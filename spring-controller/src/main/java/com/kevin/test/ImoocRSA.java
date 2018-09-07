package com.kevin.test;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by jinyugai on 2018/9/5.
 */
public class ImoocRSA {
    public static final Logger logger = LoggerFactory.getLogger(ImoocRSA.class);
    //摘要
    private static String src = "imooc security rsa";
    //公钥加密算法
    private static String RSA = "RSA";

    private static String SIGNATURE_MD5withRSA = "MD5withRSA";
    
    public static void main(String[] args){
        jdkRSA();
    }

    private static void jdkRSA() {

        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            logger.info("Public :"+rsaPublicKey.toString());
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            logger.info("private :"+rsaPrivateKey.toString());

            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_MD5withRSA);
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            logger.info("jdk rsa sign: " + Hex.encodeHexString(result));

            ////3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance(SIGNATURE_MD5withRSA);
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(result);
            logger.info("jdk rsa verify : " + bool);




        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
