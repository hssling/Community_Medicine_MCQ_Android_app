# Community Medicine MCQ App

An educational Android application designed for MBBS students preparing for NEET examination. This app provides chapter-wise MCQs with progress tracking, performance analytics, and an engaging learning experience.

## Features

### 📚 Chapter-wise MCQs
- Comprehensive question bank covering all Community Medicine topics
- Questions organized by chapters for systematic learning
- Multiple difficulty levels (Easy, Medium, Hard)
- NEET-focused content

### 📊 Progress Tracking
- Real-time progress monitoring
- Chapter-wise completion tracking
- Performance analytics and statistics
- Accuracy and speed metrics

### 🎯 Quiz System
- Timed quizzes with customizable duration
- Instant feedback and explanations
- Bookmark important questions
- Review mode for answered questions

### 🏆 Gamification
- Achievement system
- Study streaks
- Performance badges
- Leaderboard (planned)

### 🎨 User Experience
- Clean, modern Material Design 3 interface
- Dark mode support
- Responsive layout for all screen sizes
- Accessibility features

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database
- **UI**: Jetpack Compose (planned) / Views
- **Dependency Injection**: Hilt (planned)
- **Networking**: Retrofit (for future updates)
- **Animations**: Lottie

## Project Structure

```
app/
├── src/main/
│   ├── java/com/supernova/communitymedicine/
│   │   ├── data/
│   │   │   ├── dao/           # Database access objects
│   │   │   ├── database/      # Room database configuration
│   │   │   ├── model/         # Data classes and entities
│   │   │   └── converters/    # Type converters for Room
│   │   ├── repository/        # Data repositories
│   │   ├── ui/               # UI components and activities
│   │   ├── utils/            # Utility classes
│   │   └── viewmodel/        # ViewModels
│   └── res/
│       ├── drawable/         # Icons and images
│       ├── layout/           # XML layouts
│       ├── values/           # Strings, colors, themes
│       └── xml/              # Other XML resources
```

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Minimum SDK 21 (Android 5.0)
- Target SDK 34 (Android 14)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/your-username/community-medicine-mcq.git
cd community-medicine-mcq
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the app on an emulator or physical device

### Building from Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install debug APK (requires connected device)
./gradlew installDebug
```

## Database Schema

### Questions Table
- Question text and options (A, B, C, D)
- Correct answer and explanation
- Chapter and difficulty classification
- Progress tracking (attempts, correct answers)
- Bookmark status

### Quiz Results Table
- Quiz metadata (date, time, chapter)
- Score and performance metrics
- Question-wise results
- Time tracking

### Chapter Progress Table
- Chapter-wise completion status
- Best scores and averages
- Study streaks and statistics
- Difficulty-wise progress

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Roadmap

### Phase 1 (Current) ✅
- [x] Basic project structure and configuration
- [x] Database design and implementation
- [x] Core data models and repositories
- [x] Main activity and basic UI
- [x] Sample MCQ data

### Phase 2 (Next)
- [ ] Quiz activity implementation
- [ ] Result screen and review mode
- [ ] Chapter selection screen
- [ ] Progress visualization
- [ ] Basic animations and transitions

### Phase 3 (Future)
- [ ] Advanced statistics and analytics
- [ ] Bookmark system
- [ ] Search functionality
- [ ] Offline sync capabilities
- [ ] Push notifications for reminders

### Phase 4 (Advanced)
- [ ] Multiplayer quiz mode
- [ ] Leaderboards and achievements
- [ ] Social features
- [ ] Advanced customization options
- [ ] Dark mode implementation

## Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests
./gradlew connectedDebugAndroidTest

# Generate test coverage report
./gradlew createDebugCoverageReport
```

## Publishing

### Play Store
1. Generate signed APK/AAB
2. Create Play Store listing
3. Upload APK/AAB to Play Console
4. Configure store presence
5. Publish to production

### GitHub
1. Create repository
2. Push code to GitHub
3. Add README and documentation
4. Enable GitHub Pages (optional)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, email support@supernovacorp.com or create an issue in the repository.

## Acknowledgments

- NEET preparation content and structure
- Material Design 3 guidelines
- Android Jetpack components
- Room Database documentation
- Kotlin coroutines and Flow

---

**Note**: This app is designed for educational purposes and should be used as a supplement to official NEET preparation materials.
