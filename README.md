
📱 Modern Android App with Retrofit, Room, and MVVM

This Android application demonstrates a robust implementation of Clean Architecture using modern Android development practices

🌟 Features

    MVVM Architecture: A clear separation of concerns with ViewModel, LiveData, and lifecycle-aware components.
    Clean Architecture: Follows Clean Architecture principles to ensure modularity and testability.
    Local Data Persistence: Uses Room for efficient offline storage and SQLite database management.
    Network Operations: Leverages Retrofit to handle API calls seamlessly with Kotlin Coroutines for asynchronous operations.
    Dependency Injection: Simplifies dependency management and promotes modularity using Dagger Hilt.
    Error Handling: Gracefully handles network and data errors with user-friendly feedback.
    Kotlin Coroutines: Ensures smooth and efficient execution of background tasks.

🛠️ Tech Stack

    Kotlin: The modern programming language for Android development.
    Retrofit: For making network requests to RESTful APIs.
    Room: For managing the local database.
    Dagger Hilt: For dependency injection to reduce boilerplate code.
    Jetpack Components: Includes ViewModel, LiveData, and Navigation for robust application structure.
    Coroutines: For managing asynchronous programming.

🏛️ Architecture Overview

This app adheres to the Clean Architecture pattern:

    App Layer: Handles UI and user interaction using MVVM with ViewModels and LiveData.
    Domain Layer: Contains use cases that encapsulate business logic.
    Data Layer: Manages data sources like APIs (via Retrofit) and local database (via Room).
