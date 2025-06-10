package com.wallpaper.wallpaper_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WallpaperApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WallpaperApiApplication.class, args);
	}

}
