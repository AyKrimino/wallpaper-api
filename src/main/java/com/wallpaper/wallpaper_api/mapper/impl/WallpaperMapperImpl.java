package com.wallpaper.wallpaper_api.mapper.impl;

import com.wallpaper.wallpaper_api.dto.WallpaperDto;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WallpaperMapperImpl implements Mapper<WallpaperEntity, WallpaperDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WallpaperDto mapTo(WallpaperEntity wallpaperEntity) {
        return modelMapper.map(wallpaperEntity, WallpaperDto.class);
    }

    @Override
    public WallpaperEntity mapFrom(WallpaperDto wallpaperDto) {
        return modelMapper.map(wallpaperDto, WallpaperEntity.class);
    }
}
