package com.example.prometheus;

import io.prometheus.client.exporter.HTTPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		try{
			HTTPServer server = new HTTPServer(8081);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
