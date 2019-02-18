package com.helloworld.demo.controller;

import com.helloworld.demo.common.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: created by wangzh 2018/05/05 12:53
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @GetMapping("/say")
    public String sayHello(@RequestParam(name = "name") String name) {
        logger.info("[say]...");
        int enSegmentSize = 117;
        int deSegmentSize = 128;
        RSAUtils.KeyPairInfo pairInfo = RSAUtils.getKeyPair();
        String publicKey = pairInfo.getPublicKey();
        String privateKey = pairInfo.getPrivateKey();
        logger.info(publicKey);
        logger.info(privateKey);
        String str = "RSA非对称加密";


        String encryptStr = RSAUtils.encrypt(str, publicKey, enSegmentSize);
        logger.info("RSA 加密：{}", encryptStr);
        String decryptStr = RSAUtils.decrypt(encryptStr, privateKey, deSegmentSize);

        logger.info("RSA 解密：{}", decryptStr);
        try {
            String signStr = RSAUtils.sign(encryptStr, privateKey);
            logger.info("RSA 签名：{}", signStr);
            boolean result = RSAUtils.verify(encryptStr, publicKey, signStr);
            logger.info("RSA verify result：{}", result);
        } catch (Exception ex) {

        }

        return "hello：" + name;
    }
}
