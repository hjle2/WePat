package com.wepat.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OpenCrypt {
    private static final Logger logger = LoggerFactory.getLogger(OpenCrypt.class);

    public static String getSHA256(String source, String salt) {
        byte byteData[] = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(source.getBytes());
            md.update(salt.getBytes());
            byteData = md.digest();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer(byteData.length * 2);
        String hexNumber;
        for (int x = 0; x < byteData.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & byteData[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }




}

