package com.example.ItE03.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class HMACGeneratorTest {

    @Test
    void hmacTest() {
        HMACGenerator hmac = new HMACGenerator();
        String key = "mySecretKey";
        String hmacHex = hmac.hmac(key, "Rock");
        assertThat(hmacHex, is("4fc88bf177fa9731c136aafe6ade8b184c45697f0c2e9a5c5444e1182b98e85d"));
    }
}