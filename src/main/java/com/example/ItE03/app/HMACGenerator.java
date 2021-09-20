package com.example.ItE03.app;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.security.SecureRandom;

public class HMACGenerator {

    public String hmac(String key, String value) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(value);
    }

    public String key(int bits) {
        byte[] key = new byte[bits / 8];
        new SecureRandom().nextBytes(key);

        return Hex.encodeHexString(key);
    }
}
