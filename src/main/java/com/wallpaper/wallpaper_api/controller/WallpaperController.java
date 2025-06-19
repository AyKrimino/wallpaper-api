package com.wallpaper.wallpaper_api.controller;

import com.wallpaper.wallpaper_api.dto.WallpaperDto;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/wallpapers")
public class WallpaperController {

    @Autowired
    private WallpaperService wallpaperService;

    @Autowired
    private Mapper<WallpaperEntity, WallpaperDto> wallpaperMapper;

    @PostMapping
    public ResponseEntity<?> addWallpaper(@Valid @RequestBody WallpaperDto wallpaperDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
            bindingResult.getGlobalErrors().forEach(globalError -> errors.add(globalError.getDefaultMessage()));

            Map<String, Object> body = Map.of(
                    "messages", errors
            );
            return ResponseEntity.badRequest().body(body);
        }

        WallpaperEntity wallpaperEntity = wallpaperMapper.mapFrom(wallpaperDto);
        WallpaperEntity createdWallpaperEntity = wallpaperService.saveWallpaper(wallpaperEntity);

        return new ResponseEntity<>(wallpaperMapper.mapTo(createdWallpaperEntity),
                HttpStatus.CREATED);
    }

    @GetMapping
    public Page<WallpaperDto> getWallpapers(Pageable pageable) {
        Page<WallpaperEntity> wallpapers = wallpaperService.getWallpapers(pageable);

        return wallpapers.map(wallpaperMapper::mapTo);
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

        return wallpaper.map(existingEntity -> {
                    existingEntity.setUrl(wallpaperDto.getUrl());
                    existingEntity.setPath(wallpaperDto.getPath());
                    existingEntity.setResolution(wallpaperDto.getResolution());
                    existingEntity.setAuthor(wallpaperDto.getAuthor());
                    existingEntity.setLicence(wallpaperDto.getLicence());
                    existingEntity.setTags(wallpaperDto.getTags());

                    WallpaperEntity updated = wallpaperService.saveWallpaper(existingEntity);
                    WallpaperDto result = wallpaperMapper.mapTo(updated);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWallpaper(@PathVariable("id") Integer id) {
        if (!wallpaperService.isWallpaperExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        wallpaperService.deleteWallpaper(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
