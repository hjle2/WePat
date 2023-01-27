package com.wepat.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OpenCrypt {

    public static byte[] getSHA256(String source, String salt) {
        byte byteData[] = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(source.getBytes());
            md.update(salt.getBytes());
            byteData = md.digest();
            System.out.println("원문: "+source+ "   SHA-256: "+
                    byteData.length+","+byteArrayToHex(byteData));
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return byteData;
    }


    // byte[] to hex
    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

}

