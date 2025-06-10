package com.wallpaper.wallpaper_api.repository;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallpaperRepository extends JpaRepository<WallpaperEntity, Integer> {
}
