package com.wepat.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class SecurityUtil {
    public static String getSHA256(String source, String salt) {
        byte byteData[] = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(source.getBytes());
            md.update(salt.getBytes());
            byteData = md.digest();
            log.info("원문: "+ source + "   SHA-256: "+
                    byteData.length+"," + byteArrayToHex(byteData));
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

