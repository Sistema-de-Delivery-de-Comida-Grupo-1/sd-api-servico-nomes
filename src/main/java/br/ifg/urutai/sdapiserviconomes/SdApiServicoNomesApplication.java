package br.ifg.urutai.sdapiserviconomes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class SdApiServicoNomesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdApiServicoNomesApplication.class, args);
    }

}
