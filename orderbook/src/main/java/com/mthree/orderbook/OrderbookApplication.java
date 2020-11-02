package com.mthree.orderbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class OrderbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderbookApplication.class, args);
        log.info("Application Listening");
    }

}
