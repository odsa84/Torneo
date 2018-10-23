/**
 * 
 */
package com.odsaprojects.prod.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author PC
 *
 */
public class EncriptacionMd5 {

	/**
	 * 
	 */
	public EncriptacionMd5() {
		// TODO Auto-generated constructor stub
	}
	
	public String EncriptarMD5(String input) {
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }		
	}

}
