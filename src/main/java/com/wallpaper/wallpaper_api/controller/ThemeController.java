package com.wallpaper.wallpaper_api.controller;

import com.wallpaper.wallpaper_api.dto.ThemeDto;
import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.exception.ResourceNotFoundException;
import com.wallpaper.wallpaper_api.mapper.Mapper;
import com.wallpaper.wallpaper_api.service.ThemeService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private Mapper<ThemeEntity, ThemeDto> themeMapper;

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

    @GetMapping
    public Page<ThemeDto> getThemes(Pageable pageable) {
        Page<ThemeEntity> pages = themeService.getThemes(pageable);

        return pages.map(themeMapper::mapTo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeDto> getTheme(@PathVariable("id") Integer id) {
        Optional<ThemeEntity> theme = themeService.getTheme(id);

        return theme.map(themeEntity -> {
            ThemeDto response = themeMapper.mapTo(themeEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTheme(@PathVariable("id") Integer id,
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

        Optional<ThemeEntity> theme = themeService.getTheme(id);

        return theme
                .map(existingEntity -> {
                    existingEntity.setName(themeDto.getName());
                    existingEntity.setDescription(themeDto.getDescription());
                    existingEntity.setWallpapers(
                            themeDto
                                    .getWallpaperIds()
                                    .stream()
                                    .map(wallpaperId -> wallpaperService
                                            .getWallpaper(wallpaperId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Wallpaper " +
                                            "with id " + wallpaperId + "not found"))
                                    )
                                    .collect(Collectors.toList())
                    );

                    ThemeEntity updated = themeService.saveTheme(existingEntity);
                    ThemeDto response = themeMapper.mapTo(updated);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTheme(@PathVariable("id") Integer id) {
        if (!themeService.isThemeExist(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        themeService.deleteTheme(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
