# Giphy Search Android Application

A modern Android application built with Jetpack Compose, MVVM architecture, Clean Architecture, Hilt dependency injection, and Kotlin for searching and browsing GIFs from the Giphy API.

## Features

✅ **Primary Requirements**
- ✅ Kotlin language
- ✅ Auto-search: Search requests performed after a 500ms delay after user stops typing
- ✅ Pagination: Automatic loading more results when scrolling near the end
- ✅ Vertical & horizontal orientation support (2 columns portrait, 4 columns landscape)
- ✅ Error handling with custom ApiResult type

✅ **UI Features**
- ✅ Grid layout for displaying GIF results
- ✅ Detailed GIF view with full-size image and metadata
- ✅ Loading indicators for initial load and pagination
- ✅ Error display with retry functionality
- ✅ Trending GIFs on app start

✅ **Bonus Features**
- ✅ **Modern Android Development**
    - Jetpack Compose for UI
    - Coroutines and Flows for asynchronous operations
    - ViewModel with StateFlow for state management

- ✅ **Clean Architecture**
    - Data Layer: API services, repositories, data sources
    - Domain Layer: Use cases, models
    - Presentation Layer: ViewModels, Compose UI

- ✅ **Single Activity Architecture**
    - Navigation handled via Jetpack Navigation Compose

- ✅ **Network Availability Handling**
    - NetworkManager for monitoring network connectivity
    - Flow-based network state updates

- ✅ **Dependency Injection**
    - Hilt for DI
    - Modular structure with separate DI modules

## Architecture

The application follows Clean Architecture principles with clear separation of concerns:

```
app/
├── data/              # Data layer
│   ├── model/         # API response models
│   ├── mapper/        # Data to domain mappers
│   ├── remote/        # API service and data sources
│   └── repository/    # Repository implementations
├── domain/            # Domain layer
│   ├── entity/         # Domain models
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Business logic use cases
├── presentation/      # Presentation layer
│   ├── ui/            # Jetpack Compose screens, components, ViewModels and Themes 
│   └── navigation/    # Navigation configuration
├── util/              # Utilities (RichResult, NetworkManager)
│   ├── di/            # Dependency injection modules 
│   └── network/       # Network Manager 
├── GifSearchApplication # Main Applicaiton
└── MainActivity       # Single Activity Entry Point
```

## Libraries Used

- **Jetpack Compose**: Modern declarative UI
- **Hilt**: Dependency injection
- **Retrofit + OkHttp**: Network requests
- **Coroutines + Flow**: Asynchronous programming
- **Coil**: GIF loading
- **Jetpack Navigation Compose**: Navigation
- **Material 3**: UI components
- **Mockito + Kotlin Coroutines Test**: Unit testing

## Setup Instructions

1. **Get a Giphy API Key**
    - Visit [developers.giphy.com](https://developers.giphy.com/docs/api/)
    - Create an account and get your API key

2. **Configure API Key**
    - Open `app/build.gradle.kts`
    - Replace `"your-api-key-here"` in the `buildConfigField` with your actual API key
   ```kotlin
   buildConfigField("String", "GIPHY_API_KEY", "\"YOUR_API_KEY_HERE\"")
   ```

3. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

## How It Works

### Auto-Search
- User types in the search bar
- After 500ms of inactivity, search is triggered
- Previous search job is cancelled if user continues typing
- Trending GIFs shown when search is empty

### Pagination
- Grid detects when user scrolls near the bottom (last 5 items)
- Automatically loads more results
- Shows loading indicator at bottom
- Appends new items to existing list

### Error Handling
Custom `ApiResult` sealed class provides:
- `Success(data)` - successful operations
- `Error(exception, message)` - error cases


## Testing

Run unit tests:
```bash
./gradlew test
```

Test coverage includes:
- Data mapping transformations

## Configuration

### Permissions
The app requires:
- `INTERNET`: Fetch GIFs from Giphy API
- `ACCESS_NETWORK_STATE`: Monitor network availability
- `ACCESS_WIFI_STATE`: Enhanced network monitoring





