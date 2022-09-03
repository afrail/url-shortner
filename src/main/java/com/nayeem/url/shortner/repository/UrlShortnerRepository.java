/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.repository
 */
package com.nayeem.url.shortner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayeem.url.shortner.model.entity.UrlShortner;

/**
 * @author Nayeem
 *
 */
public interface UrlShortnerRepository extends JpaRepository<UrlShortner, Long> {
	
	public UrlShortner findByShortUrl(String shortUrl);
	
//	public UrlShortner findByShortLink(String shortUrl);

}
