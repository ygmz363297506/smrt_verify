package com.smartcold.manage.cold.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.time.LocalDateTime;

public class MD5Utils {

	private static final String SALT = "1qazxsw2";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String pswd) {
		return new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
	}

	public static String encrypt(String username, String pswd) {
		return new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),HASH_ITERATIONS).toHex();
	}

	public  static String getToken(){
		return encrypt("token"+ LocalDateTime.now().getHour());
	}


	public static void main(String[] args) {

		System.err.println(encrypt("counterman","counterman"));
	}

}
