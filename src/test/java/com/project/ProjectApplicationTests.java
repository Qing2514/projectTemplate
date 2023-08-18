package com.project;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectApplicationTests {

    @Value("${sm4.key}")
    private String key;

    @Test
    public void sm4Encrypt() {
        String plaintext2 = "123456789";
        SymmetricCrypto sm42 = SmUtil.sm4(key.getBytes());
        String ciphertext2 = sm42.encryptHex(plaintext2);
        String ciphertext3 = sm42.encryptHex(plaintext2);
        String decryptStr = sm42.decryptStr(ciphertext2, CharsetUtil.CHARSET_UTF_8);
        System.out.println("encryptHex加密结果: " + ciphertext2);
        System.out.println("encryptHex加密结果: " + ciphertext3);
        System.out.println("decryptStr解密结果: " + decryptStr);
    }

    @Test
    public void test() {
    }

}
