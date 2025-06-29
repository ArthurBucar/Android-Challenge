# Android Tech Challenge - Aptoide App Store

A modern Android application that fetches and displays applications from the Aptoide public API, built with Jetpack Compose and following Clean Architecture principles.

## 🚀 Features

- **App List**: Displays a list of applications fetched from Aptoide API
- **App Details**: Detailed view of each application with comprehensive information
- **Download Demo**: Interactive download button with demo warning dialog
- **Offline Support**: Cached data for offline viewing using Room database
- **Periodic Notifications**: Background notifications about new apps every 30 minutes
- **Modern UI**: Beautiful Material 3 design with dark/light theme support
- **Error Handling**: Graceful error handling with retry functionality

## 🏗️ Architecture

The project follows **Clean Architecture** principles with **MVVM** pattern:

```
app/
├── data/           # Data layer (API, Database, Repository implementations)
├── domain/         # Domain layer (Entities, Use Cases, Repository interfaces)
├── presentation/   # Presentation layer (ViewModels, UI State)
├── ui/             # UI components (Screens, Theme)
├── di/             # Dependency injection modules
├── navigation/     # Navigation components
└── worker/         # Background work (Notifications)
```

### Key Components

- **Clean Architecture**: Separation of concerns with distinct layers
- **MVVM**: Model-View-ViewModel pattern for UI logic
- **Repository Pattern**: Abstraction for data sources
- **Dependency Injection**: Hilt for dependency management
- **Reactive Programming**: Kotlin Coroutines and StateFlow
- **Offline First**: Room database for local caching

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Material 3**: Latest Material Design components
- **Navigation Compose**: Type-safe navigation

### Architecture & DI
- **Hilt**: Dependency injection
- **Clean Architecture**: Layered architecture pattern
- **MVVM**: Architecture pattern for UI

### Networking & Data
- **Retrofit**: HTTP client for API calls
- **OkHttp**: HTTP client with logging
- **Room**: Local database for caching
- **Coil**: Image loading library

### Background Processing
- **WorkManager**: Background task scheduling
- **Coroutines**: Asynchronous programming

## 📱 Screenshots

### App List Screen
- Displays apps in a modern card layout
- Shows app icon, name, developer, downloads, and rating
- Pull-to-refresh functionality
- Error handling with retry option

### App Details Screen
- Comprehensive app information
- App screenshots/graphics
- Download button with demo warning
- Developer information and links

## 🔧 Setup & Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 34+
- Kotlin 1.9+
- JDK 17+

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AndroidChallenge
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder and select it

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project (Build → Make Project)

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

### Configuration

The app is pre-configured with:
- Aptoide API endpoint: `http://ws2.aptoide.com/`
- Network security config for HTTP connections
- Room database for offline caching
- WorkManager for periodic notifications

## 📊 API Integration

### Aptoide API
- **Endpoint**: `http://ws2.aptoide.com/api/6/bulkRequest/api_list/listApps`
- **Parameters**: `store_name=apps&limit=10`
- **Response**: JSON with app list data

### Data Flow
1. **API Call**: Fetch apps from Aptoide API
2. **Cache**: Store apps in local Room database
3. **Display**: Show apps in UI
4. **Offline**: Serve cached data when offline

## 🗄️ Database Schema

### AppCacheEntity
```kotlin
data class AppCacheEntity(
    val id: Long,
    val name: String,
    val packageName: String,
    val icon: String?,
    val graphic: String?,
    val description: String?,
    val developerName: String?,
    val developerWebsite: String?,
    val downloads: String?,
    val rating: Double?,
    val ratingCount: Int?,
    val versionName: String?,
    val size: Long?
)
```

## 🔄 Background Processing

### AppUpdateWorker
- **Frequency**: Every 30 minutes
- **Purpose**: Send notifications about new apps
- **Implementation**: Uses WorkManager for reliable scheduling

## 🎨 UI/UX Features

### Material 3 Design
- Dynamic color support (Android 12+)
- Dark/light theme adaptation
- Modern typography and spacing
- Responsive layout design

### User Experience
- Smooth animations and transitions
- Loading states and error handling
- Intuitive navigation
- Accessibility support

## 🧪 Testing

### Unit Tests
- Domain layer testing
- Use case testing
- Repository testing

### UI Tests
- Screen navigation testing
- User interaction testing

## 📦 Dependencies

### Core Dependencies
```kotlin
// Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.ui:ui-tooling-preview")

// Navigation
implementation("androidx.navigation:navigation-compose")

// Hilt
implementation("com.google.dagger:hilt-android")
implementation("androidx.hilt:hilt-navigation-compose")

// Networking
implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-gson")
implementation("com.squareup.okhttp3:logging-interceptor")

// Database
implementation("androidx.room:room-runtime")
implementation("androidx.room:room-ktx")

// Image Loading
implementation("io.coil-kt:coil-compose")

// Background Work
implementation("androidx.work:work-runtime-ktx")
```

## 🚀 Performance Optimizations

- **Lazy Loading**: Efficient list rendering with LazyColumn
- **Image Caching**: Coil handles image caching automatically
- **Database Indexing**: Optimized Room queries
- **Memory Management**: Proper lifecycle management
- **Network Optimization**: Efficient API calls with caching

## 🔒 Security

- **Network Security**: Configured for HTTP connections (development)
- **Data Validation**: Input validation and sanitization
- **Error Handling**: Secure error messages without sensitive data

## 📈 Future Enhancements

- [ ] Search functionality
- [ ] App categories and filtering
- [ ] User reviews and ratings
- [ ] App installation tracking
- [ ] Push notifications
- [ ] App recommendations
- [ ] Multi-language support
- [ ] Accessibility improvements

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Android Tech Challenge**
- Built with ❤️ using modern Android development practices
- Clean Architecture implementation
- Material 3 design system

## 🙏 Acknowledgments

- Aptoide for providing the public API
- Google for Jetpack Compose and Material 3
- Android developer community for best practices and libraries

---

**Note**: This is a technical challenge project demonstrating modern Android development practices, Clean Architecture, and Material 3 design implementation. 