/**
 * @Since Mar 31, 2021
 * @Author Nayeemul Islam
 * @Project url-shortner
 * @Package com.nayeem.url.shortner.common.message
 */
package com.nayeem.url.shortner.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Since    sep 3, 2022
 * @Author   Afrail Hossain
 * @Project  url shortner
 * @version  1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
	
	private String messsage;
	private int code;

}
