package com.craigwoodcock.fishingapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FishingAppApplicationTests {

    private static final Log log = LogFactory.getLog(FishingAppApplicationTests.class);

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
        log.info("Tests Passed!");
    }

}
