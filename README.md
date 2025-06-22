# Wallpaper & Theme Manager API

A Spring Boot REST API to manage wallpapers and themes for automated desktop background rotation. Clients (GUI apps, shell scripts, etc.) interact over HTTP (JSON). Data is persisted via JPA to MySQL.

## Features

* **CRUD** operations for Wallpapers and Themes
* **Pagination** support on listing endpoints
* **Filtering** (tags, resolution, author, license) – planned
* **Rotate** endpoint to retrieve the next wallpaper in a theme
* **Configuration** via `application.yml`
* **Validation**

## Technology Stack

* **Language**: Java 17+
* **Framework**: Spring Boot 3.x
* **Persistence**: Spring Data JPA (MySQL for prod)
* **Build Tool**: Maven
* **Mapper**: ModelMapper

## Prerequisites

* JDK 17 or above
* Maven 3.6+
* MySQL server (for production profile)

## Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/AyKrimino/wallpaper-api.git
   cd wallpaper-api
   ```

2. **Configure environment variables**

   You can set required variables in your shell or via your IDE’s run configuration:

* **Linux/macOS (bash/zsh)**:

    ```dotenv
    export DB_URL=jdbc:mysql://localhost:3306/wallpaper_db
    export DB_USERNAME=admin
    export DB_PASSWORD=admin
    export APP_PORT=8080
    ```

* **IntelliJ IDEA**: Go to **Run > Edit Configurations...** and add the above keys under **Environment variables**.

3. **Build and run**

   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

   The API will start on `http://localhost:8080` (or the port set in `APP_PORT`).

## Configuration

All configuration keys live in `src/main/resources/application.yml`, referencing environment variables:

## API Endpoints

### Wallpapers

| Method | Path               | Description                  |
| ------ | ------------------ | ---------------------------- |
| GET    | `/wallpapers`      | List wallpapers (paginated)  |
| POST   | `/wallpapers`      | Create a new wallpaper       |
| GET    | `/wallpapers/{id}` | Retrieve wallpaper by ID     |
| PUT    | `/wallpapers/{id}` | Update an existing wallpaper |
| DELETE | `/wallpapers/{id}` | Delete wallpaper by ID       |

### Themes

| Method | Path                 | Description                         |
| ------ | -------------------- | ----------------------------------- |
| GET    | `/themes`            | List all themes (paginated)         |
| POST   | `/themes`            | Create a new theme                  |
| GET    | `/themes/{id}`       | Retrieve a theme by ID              |
| PUT    | `/themes/{id}`       | Update an existing theme            |
| DELETE | `/themes/{id}`       | Delete a theme by ID                |
| POST   | `/themes/{id}/apply` | Rotate theme and get next wallpaper |

#### Request & Response Formats

* **WallpaperDTO**:

  ```json
  {
    "url": "https://...",
    "path": "/local/path.png",
    "resolution": "1920x1080",
    "author": "photographer",
    "licence": "CC0",
    "tags": ["nature","minimal"]
  }
  ```

* **ThemeDTO**:

  ```json
  {
    "name": "Morning Flow",
    "description": "Serene morning images",
    "wallpaperIds": [1,2,3]
  }
  ```

* **ApplyResponseDTO**:

  ```json
  {
    "nextWallpaper": {
      "id": 2,
      "url": "...",
      "path": "...",
      // other WallpaperDTO fields
    }
  }
  ```

## Project Structure

```
src/
├── main/java/com/wallpaper/wallpaper_api
│   ├── config
│   │   └── MapperConfig.java
│   ├── controller
│   │   ├── WallpaperController.java
│   │   └── ThemeController.java
│   ├── dto
│   │   ├── WallpaperDto.java
│   │   └── ThemeDto.java
│   ├── entity
│   │   ├── WallpaperEntity.java
│   │   └── ThemeEntity.java
│   ├── exception
│   │   └── ResourceNotFoundException.java
│   ├── mapper
│   │   ├── Mapper.java
│   │   └── impl
│   │       ├── WallpaperMapperImpl.java
│   │       └── ThemeMapperImpl.java
│   ├── repository
│   │   ├── WallpaperRepository.java
│   │   └── ThemeRepository.java
│   ├── service
│   │   ├── WallpaperService.java
│   │   ├── ThemeService.java
│   │   └── impl
│   │       ├── WallpaperServiceImpl.java
│   │       └── ThemeServiceImpl.java
│   └── validation
│       ├── PathOrUrl.java
│       └── PathOrUrlValidator.java
└── resources
    ├── application.yml
    └── static/
```

## Future Improvements

* Add filtering query parameters (tags, resolution, author).
* Secure endpoints with JWT or OAuth2.
* Integrate Swagger/OpenAPI documentation.
* Add unit and integration tests.
* Support scheduling for automatic desktop rotation.

---

© 2025 Ayoub Krimi. Licensed under the MIT License.
