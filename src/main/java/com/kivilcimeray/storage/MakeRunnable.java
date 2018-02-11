package com.kivilcimeray.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.kivilcimeray.storage.read.ConfigurationReader;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.kivilcimeray.storage.read")
public class MakeRunnable {
	
	@Autowired
	ConfigurationReader confReader;

	@Bean
	@Scope("prototype")
	public ConfigurationReader configurationReader() {
		return new ConfigurationReader("","",-1L);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MakeRunnable.class);
//		String configurationString = "mongodb://localhost:27017/storagedb";
//		ConfigurationReader cr = new ConfigurationReader("OtherName", configurationString, 10000L);
//		System.out.println(cr.<Double>GetValue("TestFive"));
    }

}