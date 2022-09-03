/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.common.exception
 */
package com.nayeem.url.shortner.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomDataIntegrityViolationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomDataIntegrityViolationException(String message) {
		super(message);
	}

}

