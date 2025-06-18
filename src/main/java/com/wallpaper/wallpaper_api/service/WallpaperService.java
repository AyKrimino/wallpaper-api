package com.wallpaper.wallpaper_api.service;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;

import java.util.List;
import java.util.Optional;

public interface WallpaperService {
    WallpaperEntity saveWallpaper(WallpaperEntity wallpaperEntity);

    List<WallpaperEntity> getWallpapers();

    Optional<WallpaperEntity> getWallpaper(Integer id);

    boolean isWallpaperExist(Integer id);

    void deleteWallpaper(Integer id);
}
