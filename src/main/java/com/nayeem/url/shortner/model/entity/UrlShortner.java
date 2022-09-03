/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner
 */
package com.nayeem.url.shortner.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.sun.istack.NotNull;
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
@Entity
@Table(name = "url_shorts")
public class UrlShortner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "long_url")
	private String longUrl;
	
	@NotNull
	@Column(name = "short_url", unique = true)
	private String shortUrl;
	
//	@Column(name = "short_url_domain", nullable = true)
//	private String shortUrlDomain;
//	
	
	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "expiration_date", nullable = true)
	private LocalDateTime expirationDate;

}
