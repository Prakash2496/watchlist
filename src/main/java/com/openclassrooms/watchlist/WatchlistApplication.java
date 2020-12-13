package com.openclassrooms.watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WatchlistApplication {

	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}

	public static void main(String[] args) {
		SpringApplication.run(WatchlistApplication.class, args);
	}

}
