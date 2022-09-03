/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.model.entity
 */
package com.nayeem.url.shortner.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nayeem
 *
 */
@Getter
@Setter
@ToString
public class UserInfo {
	
	private Long id;
	
	private String userName;
	
	private String date;

}
