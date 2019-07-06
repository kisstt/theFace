package com.face.utils;

import org.reflections.util.Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;


public class DESUtils {

    /**
     * 加密，输出16进制字符串
     *
     * @param key
     * @param source
     * @return
     */
    public static String encrypt(String key, String source) {
        try {
            if (Utils.isEmpty(source)) {
                return null;
            }
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            byte[] encryptedData = cipher.doFinal(source.getBytes());
            return HexStringUtils.bytes2strHex(encryptedData);// 16进制
        } catch (Exception e) {
            throw new RuntimeException("DESHelper Encrypt Exception", e);
        }
    }

    /**
     * 解密，输入16进制字符串
     *
     * @param key
     * @param source
     * @return
     */
    public static String decrypt(String key, String source) {
        try {
            if (Utils.isEmpty(source)) {
                return null;
            }
            // 转为大写字符
            source = source.toUpperCase();
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            byte[] decryptedData = cipher.doFinal(HexStringUtils.strHex2Bytes(source));
            return new String(decryptedData);
        } catch (Exception e) {
            return source;
        }
    }

    /**
     * 3DES加密
     *
     * @param key 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String key, String source, String iv) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(source.getBytes(Constants.ENCODING_UTF8));
        return BASE64Encoder.encode(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param key 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String key, String source, String iv) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(BASE64Decoder.decode(source));
        return new String(decryptData, Constants.ENCODING_UTF8);
    }

    /**
     * 3DESECBPK7加密 输出16进制
     *
     * @return
     * @throws Exception
     */
    public static String encode4PK7(String key, String source) throws Exception {
        if (key.length() == 16) {
            key += key.substring(0, 8);
        }
        SecretKey deskey = new SecretKeySpec(key.getBytes(), "DESede");
        Cipher c1 = Cipher.getInstance("DESede");
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        return HexStringUtils.bytes2strHex(c1.doFinal(source.getBytes()));// 16进制
    }

    /**
     * 3DESECBPK7解密 输入16进制
     *
     * @return
     * @throws Exception
     */
    public static String decrypt4PK7(String key, String source) throws Exception {
        if (key.length() == 16) {
            key += key.substring(0, 8);
        }
        SecretKey deskey = new SecretKeySpec(key.getBytes(), "DESede");
        Cipher c1 = Cipher.getInstance("DESede");
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return new String(c1.doFinal(HexStringUtils.strHex2Bytes(source)));
    }

    public static void main(String[] args) throws Exception {
        // Map map = System.getProperties();
        String key = "test12345678";
        String source = "this is example";
        String des = encrypt(key, source);
        System.out.println("encrpt:" + des);
		
		/*String des = DESUtils.decrypt("12345678", "A4BC6F788DC02D14");
		System.out.println("decrpt:" + des);*/
    }

}
