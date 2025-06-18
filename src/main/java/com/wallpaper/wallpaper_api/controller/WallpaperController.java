package com.wallpaper.wallpaper_api.controller;

import com.wallpaper.wallpaper_api.dto.WallpaperDto;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wallpapers")
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private Mapper<WallpaperEntity,  WallpaperDto> wallpaperMapper;

    @PostMapping
    public ResponseEntity<WallpaperDto> addWallpaper(@RequestBody WallpaperDto wallpaperDto) {
        WallpaperEntity wallpaperEntity = wallpaperMapper.mapFrom(wallpaperDto);
        WallpaperEntity createdWallpaperEntity = wallpaperService.saveWallpaper(wallpaperEntity);

        return new ResponseEntity<>(wallpaperMapper.mapTo(createdWallpaperEntity),
                HttpStatus.CREATED);
    }

    @GetMapping
    public List<WallpaperDto> getWallpapers() {
        List<WallpaperEntity> wallpapers = wallpaperService.getWallpapers();

        return wallpapers.stream().map(wallpaperMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WallpaperDto> getWallpaper(@PathVariable("id") Integer id) {
        Optional<WallpaperEntity> wallpaper = wallpaperService.getWallpaper(id);

        return wallpaper.map(wallpaperEntity -> {
            WallpaperDto wallpaperDto = wallpaperMapper.mapTo(wallpaperEntity);
            return new ResponseEntity<>(wallpaperDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WallpaperDto> updateWallpaper(@PathVariable("id") Integer id,
                                                        @RequestBody WallpaperDto wallpaperDto) {
        Optional<WallpaperEntity> wallpaper = wallpaperService.getWallpaper(id);

        return  wallpaper.map(existingEntity -> {
            existingEntity.setUrl(wallpaperDto.getUrl());
            existingEntity.setPath(wallpaperDto.getPath());
            existingEntity.setResolution(wallpaperDto.getResolution());
            existingEntity.setAuthor(wallpaperDto.getAuthor());
            existingEntity.setLicence(wallpaperDto.getLicence());
            existingEntity.setTags(wallpaperDto.getTags());

            WallpaperEntity  updated = wallpaperService.saveWallpaper(existingEntity);
            WallpaperDto result = wallpaperMapper.mapTo(updated);
            return new ResponseEntity<>(result, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
