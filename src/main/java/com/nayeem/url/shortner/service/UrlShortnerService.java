/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.service
 */
package com.nayeem.url.shortner.service;

import java.util.List;

import com.nayeem.url.shortner.common.message.BaseResponse;
import com.nayeem.url.shortner.model.dto.input.UrlInputDto;
import com.nayeem.url.shortner.model.dto.output.UrlOutputDto;
import com.nayeem.url.shortner.model.entity.UrlShortner;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */

public interface UrlShortnerService {
	
//	public UrlShortner generateShortLink(UrlInputDto inputDto);
	
	public UrlOutputDto generateShortLink(UrlInputDto inputDto);
	
	public List<UrlOutputDto> getAllLink();
	
	public UrlOutputDto getByShortLink(String shortUrl);
	
	public Long totalLink();
	
	public UrlShortner getEncodedUrl(String url);
	
	public void deleteShortLink(UrlShortner url);

}
