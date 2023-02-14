package com.example.drinks;

import android.os.Debug;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    private static String ALGORITHM = "SHA-256";
    private static String MODE = "UTF-8";
    private static String IV = "abcdef";
    private static String Key = "oh-drink-it";

    public static String EncryptString(String value) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(Key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(value.getBytes());
        //Log the encrypted value
        Log.d("Encrypted Value", Base64.encodeToString(results, Base64.DEFAULT));
        return Base64.encodeToString(results, Base64.DEFAULT);
    }
}
