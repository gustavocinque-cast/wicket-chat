package br.com.chat.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class SecurityUtil {
	
	private SecurityUtil (){}
	
	public static String criaMd5(String s){
		
		String result=null;
		
		try {
			MessageDigest msg = MessageDigest.getInstance("MD5");
			msg.reset();
			msg.update(s.getBytes(Charset.forName("UTF8")));
			byte[] resultByte = msg.digest();
			result = new String(HexBin.encode(resultByte));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("ERRO no getinstance criptografia");
		}
		
		return result;
	}
}

