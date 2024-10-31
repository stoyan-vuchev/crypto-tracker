# Crypto Tracker

**Crypto Tracker** is a simple yet effective Android app that displays the latest cryptocurrency data, including key metrics like price change percentages and bid/ask prices. Built with Jetpack Compose, the app provides a clean UI for listing cryptocurrencies and detailed views with additional information for each coin.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Development Process](#development-process)
- [Installation](#installation)
- [License](#license)

## Overview
Crypto Tracker offers a streamlined and user-friendly interface for tracking the latest cryptocurrency prices and viewing coin details. This project is structured around modular principles and focuses on maintainability, scalability, and testability.

## Features
- View current cryptocurrency prices.
- Retrieve coin details by symbol.
- Cached data for offline access.
- Configurable data preferences using `CryptoPreferences`.

## Technologies Used
Crypto Tracker utilizes the following technologies and libraries:
- **Kotlin**: The primary language for Android development.
- **Jetpack Compose**: For building UI components declaratively.
- **Hilt**: For dependency injection.
- **DataStore**: For storing user preferences and configurations.
- **Room**: For local database management.
- **Coroutines**: For asynchronous operations.
- **Ktor**: For network communication.
- **Result Type**: To handle success and error cases consistently.
- **JUnit & AssertK**: For testing.
- **Material3**: For UI design.

## Architecture
The project follows a clean architecture pattern, separating concerns across multiple layers:
- **Domain Layer**: Contains interfaces, use cases, and utilities that define core functionality without implementation details.
- **Data Layer**: Contains the actual implementations for data sources, including `RemoteDataSource`, `LocalDatabaseDao`, and `CryptoPreferences`.
- **Presentation Layer**: Contains UI components built with Jetpack Compose.

Each feature module has separate `data` and `domain` packages for improved modularity and testability.

### Packages
- **core**: Core utilities, network utilities, and shared repositories.
- **crypto**: A feature specifically related to cryptocurrency data management, including fetching and caching coin details and prices.

### Example Classes
- `RemoteDataSource`: Interface for retrieving cryptocurrency data from a remote API.
- `LocalDatabaseDao`: Interface for interacting with the local database to cache coin data.
- `CryptoPreferences`: Interface for managing app preferences, such as last fetched timestamps.
- `CoinRepository`: Repository that serves as a single source of truth by coordinating data from `RemoteDataSource`, `LocalDatabaseDao`, and `CryptoPreferences`.

## Development Process

1. **Setting Up Data Management**:
   - The `CryptoPreferences` interface handles persistent user data. The `CryptoPreferencesImpl` class uses DataStore to store and retrieve the preferences and wraps operations in `Result` types for consistent error handling.
   
2. **Implementing the Remote Data Source**:
   - `RemoteDataSource` is responsible for fetching cryptocurrency data from the remote API using Retrofit. This source is designed to retrieve up-to-date price information and coin details, ensuring a reliable and extensible interface.
   - Network requests are implemented with error handling, which returns a `Result` type containing either the fetched data or an error object.

3. **Configuring the Local Database**:
   - `LocalDatabaseDao` provides an interface for caching cryptocurrency data locally using Room. This allows the app to serve cached data for offline access, enhancing the user experience when connectivity is limited.
   - Data retrieved from the network is cached in the local database, and the app can fetch data from this cache as a fallback when the network is unavailable.

4. **Setting Up the Repository Layer**:
   - `CoinRepository` acts as the main data coordinator by sourcing data from `RemoteDataSource`, `LocalDatabaseDao`, and `CryptoPreferences`. The repository decides when to fetch data from the network and when to use cached data based on the last fetched timestamp.
   - Timestamp-based caching logic ensures that network requests are only made when necessary, saving resources and improving app performance.

5. **Testing**:
   - Unit / UI tests are implemented across all layers, focusing on critical components like `CryptoPreferences`, `RemoteDataSource`, `LocalDatabaseDao`, and `CoinRepository`.

## Installation
To get a copy of this project up and running on your local machine:
1. Clone the repository:
```bash
git clone https://github.com/stoyan-vuchev/crypto-tracker.git
```
2. Open the project in Android Studio.
3. Sync and rebuild the project with Gradle.
4. Run the app on an emulator or connected device.

## License
```
MIT License

Copyright (c) 2024 Stoyan Vuchev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
