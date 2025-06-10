package com.wallpaper.wallpaper_api.controller;

import com.wallpaper.wallpaper_api.dto.WallpaperDto;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private Mapper<WallpaperEntity,  WallpaperDto> wallpaperMapper;

    @PostMapping("/wallpapers")
    public ResponseEntity<WallpaperDto> addWallpaper(@RequestBody WallpaperDto wallpaperDto) {
        WallpaperEntity wallpaperEntity = wallpaperMapper.mapFrom(wallpaperDto);
        WallpaperEntity createdWallpaperEntity = wallpaperService.addWallpaper(wallpaperEntity);

        return new ResponseEntity<>(wallpaperMapper.mapTo(createdWallpaperEntity),
                HttpStatus.CREATED);
    }
}
