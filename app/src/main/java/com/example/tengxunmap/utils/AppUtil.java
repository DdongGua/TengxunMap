package com.example.tengxunmap.utils;

import android.os.Build;

import com.example.tengxunmap.constant.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 亮亮 on 2017/8/25.
 */

public class AppUtil {
    public static String encryptSign(String t, String s, String p, String i) {
        return MD5(t + p + Constant.SALT + i);
    }

    public static String MD5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }



    public static boolean isFlyme() {
        if ("Flyme".equals(Build.MANUFACTURER)) {
            return true;
        } else {
            return false;
        }
    }
}
