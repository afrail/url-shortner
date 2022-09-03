/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.service.impl
 */
package com.nayeem.url.shortner.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.hash.Hashing;
import com.nayeem.url.shortner.common.message.BaseResponse;
import com.nayeem.url.shortner.model.dto.input.UrlInputDto;
import com.nayeem.url.shortner.model.dto.output.AnotherInfo;
import com.nayeem.url.shortner.model.dto.output.UrlOutputDto;
import com.nayeem.url.shortner.model.entity.UrlShortner;
import com.nayeem.url.shortner.repository.UrlShortnerRepository;
import com.nayeem.url.shortner.service.UrlShortnerService;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */

@Component
public class UrlShortnerServiceImpl implements UrlShortnerService {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortnerServiceImpl.class);

	@Autowired
	private UrlShortnerRepository repo;

	
	@Override
	public UrlOutputDto generateShortLink(UrlInputDto inputDto) {
		if(inputDto.getLongUrl() != null)
        {
            UrlShortner urlToPersist = new UrlShortner();
            urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setLongUrl(inputDto.getLongUrl());
            urlToPersist.setShortUrl(randomUrl());
            
            if(inputDto.getExpirationTime() == 0)
            {
            	urlToPersist.setExpirationDate(null);
            }else {
            urlToPersist.setExpirationDate(getExpirationDate(inputDto.getExpirationTime(),urlToPersist.getCreationDate()));
            }
            UrlOutputDto outputDto = persistShortLink(urlToPersist);
            
                return outputDto;
        }
		else {
        return null;}
	}
	
    @Bean
    public static String randomUrl() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+ "0-9" + "a-z";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while(salt.length() < 6) {
            int index = (int)(rnd.nextFloat()*SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
    @Override
    public void deleteShortLink(UrlShortner url) {

        repo.delete(url);
    }
    
    public UrlOutputDto persistShortLink(UrlShortner url) {
    	UrlShortner urlToRet = repo.save(url);
    	UrlOutputDto outputDto = new UrlOutputDto();
    	outputDto.setShortUrlDomain(ServletUriComponentsBuilder.fromCurrentContextPath().path("/")
					.toUriString());
    	BeanUtils.copyProperties(urlToRet, outputDto);
    	outputDto.setInfo(new AnotherInfo(urlToRet.getCreationDate(), urlToRet.getExpirationDate()));
    	
        return outputDto;
    }

	@Override
	public List<UrlOutputDto> getAllLink() {

		return repo.findAll().stream().map(shortner -> copyEntityToDto(shortner)).collect(Collectors.toList());
	}
	
    @Override
    public UrlShortner getEncodedUrl(String url) {
    	UrlShortner urlToRet = repo.findByShortUrl(url);
        return urlToRet;
    }

	@Override
	public UrlOutputDto getByShortLink(String shortUrl) {
		return copyEntityToDto(repo.findByShortUrl(shortUrl));
	}

	@Override
	public Long totalLink() {
		return repo.count();
	}

	private UrlOutputDto copyEntityToDto(UrlShortner shortner) {
		UrlOutputDto outputDto = new UrlOutputDto();

		BeanUtils.copyProperties(shortner, outputDto);

		if (shortner.getExpirationDate() != null) {
			outputDto.setInfo(new AnotherInfo(shortner.getCreationDate(), shortner.getExpirationDate()));
		}
		else {
			outputDto.setInfo(new AnotherInfo(shortner.getCreationDate(), null));
		}

		return outputDto;
	}

	private String encodeUrl(String url) {
		String encodedUrl = "";
		LocalDateTime time = LocalDateTime.now();
		encodedUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		return encodedUrl;
	}

	private LocalDateTime getExpirationDate(int expirationTime, LocalDateTime creationDate) {
		LocalDateTime expirationDateToRet = creationDate.plusSeconds(expirationTime);
		return expirationDateToRet;
	}
}
