
package com.hj.rminf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tmri.tfc"})
public class HjRminfApplication {

    public static void main(String... args) {
        SpringApplication.run(HjRminfApplication.class, args);
    }

}
