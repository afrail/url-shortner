/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.model.dto.output
 */
package com.nayeem.url.shortner.model.dto.output;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlOutputDto {

	private Long id;
	
	private String longUrl;
	
	private String shortUrl;

	private String shortUrlDomain;
	
	private AnotherInfo info;

//    private String originalUrl;
//    private String shortLink;
//    private LocalDateTime expirationDate;


}
