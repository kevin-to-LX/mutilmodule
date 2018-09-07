package com.kevin.test;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by jinyugai on 2018/9/5.
 */
public class ImoocDSA {
    /**
     * DSA是基于整数有限域离散对数难题的，
     * 其安全性与RSA相比差不多。
     * DSA的一个重要特点是两个素数公开，
     * 这样，当使用别人的p和q时，即使不知道私钥，
     * 你也能确认它们是否是随机产生的，还是作了手脚。RSA却做不到。
     * DSA仅包含数字签名
     * */
    static final Logger logger = LoggerFactory.getLogger(ImoocRSA.class);
    private static final String SRC = "imooc security dsa";
    private static final String DSA = "DSA";

    private static final Integer KEY_SIZE = 512;
    private static final String SIGNATURE_SHA_DSA = "SHA1withDSA";
    public static void main(String[] args) {
        jdkDSA();
    }

    private static void jdkDSA() {
        try {
            //1.初始化密钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DSA);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();

            //执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(DSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_SHA_DSA);
            signature.initSign(privateKey);
            signature.update(SRC.getBytes());
            byte[] signResult = signature.sign();
            logger.info("jdk dsa sign : " + Hex.encodeHexString(signResult));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance(DSA);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance(SIGNATURE_SHA_DSA);
            signature.initVerify(publicKey);
            signature.update(SRC.getBytes());
            logger.info("jdk dsa versify :" + signature.verify(signResult) );


        } catch (Exception e) {
            logger.info("you");
        }
    }

}
