/**
 * @Since Apr 1, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.controller
 */
package com.nayeem.url.shortner.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nayeem.url.shortner.model.dto.input.UrlInputDto;
import com.nayeem.url.shortner.model.dto.output.UrlErrorResponseDto;
import com.nayeem.url.shortner.model.dto.output.UrlOutputDto;
import com.nayeem.url.shortner.model.entity.UrlShortner;
import com.nayeem.url.shortner.service.UrlShortnerService;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */

@RestController
public class UrlShortnerController {
	
	
    @Autowired
    private UrlShortnerService urlShortnerService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlInputDto urlDto)
    {
    	UrlOutputDto outputDto = urlShortnerService.generateShortLink(urlDto);

         if(outputDto != null)
         {
//        	 UrlOutputDto outputDto = new UrlOutputDto();
//             outputDto.setOriginalUrl(urlToRet.getOriginalUrl());
//             outputDto.setExpirationDate(urlToRet.getExpirationDate());
//             outputDto.setShortLink(urlToRet.getShortLink());
             return new ResponseEntity<UrlOutputDto>(outputDto, HttpStatus.OK);
         }
         else {
         UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
         urlErrorResponseDto.setStatus("404");
         urlErrorResponseDto.setError("There was an error processing your request. please try again.");
         return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
         }
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {

        if(StringUtils.isEmpty(shortLink))
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Invalid Url");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }
        
        UrlShortner urlToRet = urlShortnerService.getEncodedUrl(shortLink);

        if(urlToRet == null)
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }

        else if(urlToRet.getExpirationDate().isBefore(LocalDateTime.now()))
        {
        	urlShortnerService.deleteShortLink(urlToRet);
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
            urlErrorResponseDto.setStatus("200");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }
//        else if(urlToRet.getExpirationDate().equals(null)) {
//        response.sendRedirect(urlToRet.getLongUrl());
//        return null;
//        }
        else {
        response.sendRedirect(urlToRet.getLongUrl());
        return null;
        }
    }
    
	@GetMapping(value = "/get-all")
	public ResponseEntity<List<UrlOutputDto>> getAllLink() {
		List<UrlOutputDto> urlOutputDtos = urlShortnerService.getAllLink();
		return new ResponseEntity<List<UrlOutputDto>>(urlOutputDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/short/{short}")
	public ResponseEntity<UrlOutputDto> getByShortLink(@PathVariable("short") String shortUrl) {
		UrlOutputDto genericsDtos = urlShortnerService.getByShortLink(shortUrl);
		return new ResponseEntity<UrlOutputDto>(genericsDtos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/count")
	public ResponseEntity<String> getByShortLink() {
		return new ResponseEntity<String>("Total Short URL "+ urlShortnerService.totalLink(), HttpStatus.OK);
	}
}
