package com.wallpaper.wallpaper_api.repository;

import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<ThemeEntity, Integer> {
}
