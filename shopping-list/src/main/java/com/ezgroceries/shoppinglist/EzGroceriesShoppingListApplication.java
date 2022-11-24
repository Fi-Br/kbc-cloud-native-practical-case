package com.ezgroceries.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EntityScan
public class EzGroceriesShoppingListApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzGroceriesShoppingListApplication.class, args);
    }

}
