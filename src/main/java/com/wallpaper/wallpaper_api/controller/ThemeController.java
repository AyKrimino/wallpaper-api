package com.wallpaper.wallpaper_api.controller;

import com.wallpaper.wallpaper_api.dto.ThemeDto;
import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.exception.ResourceNotFoundException;
import com.wallpaper.wallpaper_api.service.ThemeService;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private WallpaperService wallpaperService;

    @PostMapping
    public ResponseEntity<?> addTheme(
            @Valid @RequestBody ThemeDto themeDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            bindingResult.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage())
            );

            bindingResult.getGlobalErrors().forEach(globalError -> errors.add(globalError.getDefaultMessage())
            );

            Map<String, Object> body = Map.of("messages", errors);
            return ResponseEntity.badRequest().body(body);
        }

        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setName(themeDto.getName());
        themeEntity.setDescription(themeDto.getDescription());
        themeEntity.setCurrentIndex(0);

        List<WallpaperEntity> list = themeDto
                .getWallpaperIds()
                .stream()
                .map(wallpaperId -> wallpaperService
                        .getWallpaper(wallpaperId)
                        .orElseThrow(() -> new ResourceNotFoundException("Wallpaper " +
                                "with id " + wallpaperId + "not found"))
                )
                .collect(Collectors.toList());
        themeEntity.setWallpapers(list);

        ThemeEntity createdEntity = themeService.saveTheme(themeEntity);

        ThemeDto response = new ThemeDto();
        response.setId(createdEntity.getId());
        response.setName(createdEntity.getName());
        response.setDescription(createdEntity.getDescription());
        response.setWallpaperIds(
                createdEntity
                        .getWallpapers()
                        .stream()
                        .map(WallpaperEntity::getId)
                        .collect(Collectors.toList())
        );
        response.setCurrentIndex(createdEntity.getCurrentIndex());
        response.setCreatedAt(createdEntity.getCreatedAt());
        response.setUpdatedAt(createdEntity.getUpdatedAt());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
