package com.wallpaper.wallpaper_api.service;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WallpaperService {
    WallpaperEntity saveWallpaper(WallpaperEntity wallpaperEntity);

    Page<WallpaperEntity> getWallpapers(Pageable pageable);

    Optional<WallpaperEntity> getWallpaper(Integer id);

    boolean isWallpaperExist(Integer id);

    void deleteWallpaper(Integer id);
}
