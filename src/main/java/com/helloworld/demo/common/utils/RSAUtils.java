package com.helloworld.demo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author wangzh 2019/02/17 16:18
 */
public abstract class RSAUtils {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    /**
     * RSA/ECB/NoPadding或RSA/None/PKCS1Padding
     */
    private static final String KEY_RSA_TYPE = "RSA";
    /**
     * JDK方式RSA加密最大只有1024位
     */

    private static int KEY_SIZE = 1024;
//    private static int ENCODE_PART_SIZE = KEY_SIZE / 8;
//    public static final String PUBLIC_KEY_NAME = "public";
//    public static final String PRIVATE_KEY_NAME = "private";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * keySize=1024
     *
     * @return
     */
    public static KeyPairInfo getKeyPair() {
        return getKeyPair(KEY_SIZE);
    }

    /**
     * 生成公钥、私钥对
     *
     * @param keySize
     * @return
     */
    public static KeyPairInfo getKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_RSA_TYPE);
            //初始化密钥对
            keyPairGen.initialize(keySize, new SecureRandom());
            //生成密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
            //私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());

            KeyPairInfo pairInfo = new KeyPairInfo(keySize);
            pairInfo.setPublicKey(publicKeyStr);
            pairInfo.setPrivateKey(privateKeyStr);

            return pairInfo;
        } catch (Exception ex) {
            logger.error("生成公钥和私钥对异常:{}", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥对象
     *
     * @param publicKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publicKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA_TYPE);
        X509EncodedKeySpec publicpkcs8KeySpec =
                new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
        PublicKey publicKey = keyFactory.generatePublic(publicpkcs8KeySpec);
        return publicKey;
    }

    /**
     * 获取私钥对象
     *
     * @param privateKeyBase64
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKeyBase64)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA_TYPE);
        PKCS8EncodedKeySpec privatekcs8KeySpec =
                new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
        PrivateKey privateKey = keyFactory.generatePrivate(privatekcs8KeySpec);
        return privateKey;
    }

    /**
     * 公钥加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @return 经过 base64 编码后的字符串
     */
    public static String encrypt(String content, String publicKeyBase64) {
        return encrypt(content, publicKeyBase64, -1);
    }

    /**
     * 公钥分段加密
     *
     * @param content         待加密内容
     * @param publicKeyBase64 公钥 base64 编码
     * @param segmentSize     segmentSize分段大小,一般小于 keySize/8（段小于等于0时，将不使用分段加密）
     * @return 经过 base64 编码后的字符串
     */
    public static String encrypt(String content, String publicKeyBase64, int segmentSize) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyBase64);
            return encrypt(content, publicKey, segmentSize);
        } catch (Exception ex) {
            logger.error("公钥分段加密异常：{}", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 分段加密
     *
     * @param ciphertext  密文
     * @param key         密钥
     * @param segmentSize 分段大小，<=0 不分段
     * @return
     */
    public static String encrypt(String ciphertext, Key key, int segmentSize) {
        try {
            // 用公钥加密
            byte[] srcBytes = ciphertext.getBytes();

            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance(KEY_RSA_TYPE);
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = null;

            if (segmentSize > 0)
                //分段
                resultBytes = cipherDoFinal(cipher, srcBytes, segmentSize);
            else
                resultBytes = cipher.doFinal(srcBytes);

            String base64Str = Base64.encodeBase64String(resultBytes);
            return base64Str;
        } catch (Exception ex) {
            logger.error("分段加密异常：{}", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @return
     * @segmentSize 分段大小
     */
    public static String decrypt(String contentBase64, String privateKeyBase64) {
        return decrypt(contentBase64, privateKeyBase64, -1);
    }

    /**
     * 私钥分段解密
     *
     * @param contentBase64    待加密内容,base64 编码
     * @param privateKeyBase64 私钥 base64 编码
     * @param segmentSize      分段大小
     * @return
     */
    public static String decrypt(String contentBase64, String privateKeyBase64, int segmentSize) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBase64);
            return decrypt(contentBase64, privateKey, segmentSize);
        } catch (Exception ex) {
            logger.error("私钥分段解密异常：{}", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 分段解密
     *
     * @param contentBase64 密文
     * @param key           密钥
     * @param segmentSize   分段大小（小于等于0不分段）
     * @return
     */
    public static String decrypt(String contentBase64, Key key, int segmentSize) {
        try {
            // 用私钥解密
            byte[] srcBytes = Base64.decodeBase64(contentBase64);
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher deCipher = Cipher.getInstance(KEY_RSA_TYPE);

            // 根据密钥，对Cipher对象进行初始化
            deCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decBytes = null;
            if (segmentSize > 0)
                //分段
                decBytes = cipherDoFinal(deCipher, srcBytes, segmentSize);
            else
                decBytes = deCipher.doFinal(srcBytes);

            String decrytStr = new String(decBytes);
            return decrytStr;
        } catch (Exception ex) {
            logger.error("私钥解密异常：{}", ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥签名
     *
     * @param data       已加密的数据
     * @param privateKey 私钥（Base64编码）
     * @return
     * @throws Exception
     */
    public static String sign(String data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA_TYPE);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data.getBytes(CHARSET_UTF8));
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 验签
     *
     * @param data      已加密的数据
     * @param publicKey 公钥（Base64）编码
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA_TYPE);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data.getBytes(CHARSET_UTF8));
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 分段
     *
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        if (segmentSize <= 0)
            throw new RuntimeException("分段大小必须大于0");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }


    /**
     * 密钥对
     */
    public static class KeyPairInfo {
        private String publicKey;
        private String privateKey;
        private int keySize;

        public KeyPairInfo(int keySize) {
            setKeySize(keySize);
        }

        public KeyPairInfo(String publicKey, String privateKey) {
            setPublicKey(publicKey);
            setPrivateKey(privateKey);
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public int getKeySize() {
            return keySize;
        }

        public void setKeySize(int keySize) {
            this.keySize = keySize;
        }
    }
}
