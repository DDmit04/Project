package com.web.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ServiceUtils {
	
	 public static String generateRandomKey(int byteLength) {
		    SecureRandom secureRandom = new SecureRandom();
		    byte[] token = new byte[byteLength];
		    secureRandom.nextBytes(token);
		    return new BigInteger(1, token).toString(1);
		}


}
