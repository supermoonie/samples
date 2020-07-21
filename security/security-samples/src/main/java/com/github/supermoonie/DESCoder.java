package com.github.supermoonie;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 * @author supermoonie
 * @since 2020/7/15
 */
public abstract class DESCoder {

    /**
     * 算法密钥
     */
    public static final String KEY_ALGORITHM = "DES";

    /**
     * 加密解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception e
     */
    private static Key toKey(byte[] key) throws Exception {
        // 初始化DES密钥材料
        DESKeySpec dks = new DESKeySpec(key);
        // 实例化密文密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成密文密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    public static void main(String[] args) {

    }

}
