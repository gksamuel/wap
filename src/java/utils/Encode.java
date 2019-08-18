/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author gachanja
 */
public class Encode {

    public String endodesha1(String str) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            str = hexString.toString();


        } catch (NoSuchAlgorithmException e) {
        }
        return str;
    }
}
