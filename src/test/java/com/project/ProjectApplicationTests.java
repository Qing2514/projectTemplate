package com.project;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectApplicationTests {

    @Value("${sm2.privateKey}")
    private String privateKey;

    @Value("${sm2.publicKey}")
    private String publicKey;

    @Value("${sm4.key}")
    private String key;

    String text = "123456";

    @Test
    public void sm2GenerateKey() {
        SM2 sm2 = SmUtil.sm2();
        // 生成私钥
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        String privateKeyString = HexUtil.encodeHexStr(privateKey);
        System.out.println("私钥: " + privateKeyString);
        System.out.println("私钥位数：" + privateKeyString.length());
        // 生成66位公钥
        byte[] publicKeyEc = BCUtil.encodeECPublicKey(sm2.getPublicKey());
        String publicKeyEcString = HexUtil.encodeHexStr(publicKeyEc);
        System.out.println("公钥: " + publicKeyEcString);
        System.out.println("公钥位数：" + publicKeyEcString.length());
    }

    @Test
    public void sm2Encrypt() {
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        System.out.println("私钥：" + privateKey);
        System.out.println("私钥位数：" + privateKey.length());
        System.out.println("公钥：" + publicKey);
        System.out.println("公钥位数：" + publicKey.length());
        // 公钥加密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println("公钥加密结果：" + encryptStr);
        System.out.println("加密结果位数：" + encryptStr.length());
        // 私钥解密
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("私钥解密结果：" + decryptStr);
        System.out.println("解密结果位数：" + decryptStr.length());
    }


    @Test
    public void sm3Test() {
        String digestHex = SmUtil.sm3(text);
        System.out.println("加密结果：" + digestHex);
    }

    @Test
    public void sm4Encrypt() {
        SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes());
        System.out.println("秘钥：" + key);
        System.out.println("秘钥位数：" + key.length());
        // 加密
        String ciphertext = sm4.encryptHex(text);
        System.out.println("加密结果: " + ciphertext);
        System.out.println("加密结果位数：" + ciphertext.length());
        // 解密
        String decryptStr = sm4.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密结果: " + decryptStr);
        System.out.println("解密结果位数：" + decryptStr.length());
    }

}
