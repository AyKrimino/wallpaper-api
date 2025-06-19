package com.wallpaper.wallpaper_api.mapper.impl;

import com.wallpaper.wallpaper_api.dto.ThemeDto;
import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapperImpl implements Mapper<ThemeEntity, ThemeDto>  {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ThemeDto mapTo(ThemeEntity themeEntity) {
        return modelMapper.map(themeEntity, ThemeDto.class);
    }

    @Override
    public ThemeEntity mapFrom(ThemeDto themeDto) {
        return modelMapper.map(themeDto, ThemeEntity.class);
    }
}
