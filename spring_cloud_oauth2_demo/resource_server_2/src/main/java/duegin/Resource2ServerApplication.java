package duegin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class Resource2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resource2ServerApplication.class, args);
    }
}