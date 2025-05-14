package br.com.acbueno.itapira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RailwayNetworkApplication {

  public static void main(String[] args) {
    SpringApplication.run(RailwayNetworkApplication.class, args);
  }

}
