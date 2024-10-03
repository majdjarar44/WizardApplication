Wizards World App
This Android app fetches and displays a list of wizards from the Wizard World API, allowing users to view detailed information about each wizard, including the elixirs they've created. The app is built with Jetpack Compose and follows the MVVM architecture pattern, using Dagger-Hilt for dependency injection and Room for offline caching.

Features
1. Wizards List
Displays a list of wizards fetched from the /Wizards endpoint.
Each list item shows basic wizard information, such as the wizard's name.
Clicking on a wizard navigates to a detailed view.
2. Wizard Details
The detailed view shows the wizard's name, traits, and a list of elixirs they have created.
Each elixir item displays its name, fetched from the /Wizards/{id} endpoint.
3. Elixir Details
Users can click on an elixir in the wizard details to see full elixir details (name, effect, difficulty, ingredients) from the /Elixirs/{id} endpoint.
4. Offline Caching
Wizards and elixir data are cached locally using Room to ensure offline functionality.
The app fetches and displays cached data when there is no internet connection, handling the first two pages of wizards for offline use.
5. Favorites
Users can mark wizards as favorites, which are stored locally in the database and persist offline.
6. Error Handling & Retry
Provides error messages when data cannot be fetched or if the user is offline.
Includes retry mechanisms for API failures.
7. Architecture
Built using the MVVM architecture pattern.
Follows the repository pattern for API and data management.
Uses Dagger-Hilt for dependency injection.
8. Optional Testing (Not Included)
Unit tests for ViewModel and repository logic can be added in future iterations.
Technologies Used
UI: Jetpack Compose
Architecture: MVVM, Repository Pattern
Networking: Retrofit for API calls
Caching: Room for local storage
Dependency Injection: Dagger-Hilt
API: Wizard World API
Git Guidelines
Meaningful commit messages with a clear commit history.
