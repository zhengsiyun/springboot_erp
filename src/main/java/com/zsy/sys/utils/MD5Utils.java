package com.zsy.sys.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Utils {
	
	/**
	 * 进行md5加密 二次加盐
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String md5(String source,Object salt) {
		return new Md5Hash(source, salt, 2).toString();
	}
}
