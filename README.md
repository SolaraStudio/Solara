# Solara

A minimal, privacy-first Android browser. Built with Kotlin and Jetpack Compose, powered by our own rendering and JavaScript engines.

---

## What it is

Solara is a browser for Android that does not rely on Chromium, WebView, or any third-party rendering engine. It uses Optima (our Rust rendering engine) to parse HTML and CSS, Solarium (our Zig Runtime engine) to run scripts, and Solarian (our Kotlin search API) for search.

The goal is a browser that is:

- ***Small.*** No 200 MB engine dependencies.
- ***Fast.*** Native rendering, no bridge overhead.
- ***Private.*** No telemetry, no accounts, no tracking.
- ***Beautiful.*** Material 3, glass-morphism, quiet design.

Solara is the flagship browser for the SolaraStudio ecosystem. For older devices, see [Solaria](https://github.com/SolaraStudio/Solaria).

---

## Architecture

Solara is a thin Android shell around two native engines.

```
| Layer | Component | Language | Responsibility | |-------|-----------|----------|----------------|
|1. UI | Solara UI | Kotlin · Compose | Screens, tabs, toolbar, theme, gesture handling |
| 2. State | ViewModels | Kotlin · Coroutines | StateFlow, business logic, lifecycle |
| 3. Data | Repositories | Kotlin | Room, DataStore, caching, preferences |
| 4. Bridge | JNI Bridge | Kotlin ↔ C ABI Native calls, callbacks, handle table
| 5. Engine — Render | Optima Rust HTML parse · CSS cascade · DOM · layout · paint
| 6. Engine — Runtime | Solarium Zig JavaScript · event loop · DOM bindings
| 7. Service | Solarian Kotlin Search providers, suggestions
| 8. Surface | SurfaceView | Android GPU-backed render target
```

- Optima — rendering engine. Rust. [repo](https://github.com/SolaraStudio/Optima)
- Solarium — JavaScript runtime. Zig. [repo](https://github.com/SolaraStudio/Solarium)
- Solarian — search provider API. Kotlin. [repo](https://github.com/SolaraStudio/Solarian)

Solara itself does not implement HTML, CSS, or JavaScript. It provides the UI shell, state management, and native integration.

---

## Screenshots

***To be added.***

---

## Requirements

To build Solara you need:

- ***JDK*** - 17 or later
- ***Android SDK*** - with platform 36 and build-tools 36.0.0
- ***Android NDK*** - 25.1.8937393 (for building Optima and Solarium)
- ***Rust 1.85+*** with the four Android targets
- ***Zig 0.14+*** (for building Solarium)
- ***Gradle 9.5*** (included via wrapper)

---

## Building

### Clone

```bash
git clone https://github.com/SolaraStudio/Solara.git
cd Solara
```

### Configure credentials

Solara depends on prebuilt Optima artifacts hosted on GitHub Packages. Add your credentials to `~/.gradle/gradle.properties:`

```properties
GITHUB_ACTOR=your-github-username
GITHUB_TOKEN=ghp_your_personal_access_token
```

The token needs the `read:packages` scope.

### Build the app

```bash
cd android
./gradlew assembleRelease
```

### Output:

```
android/app/build/outputs/apk/release/app-release.apk
```

### For a debug build:

```bash
./gradlew assembleDebug
```

### Build with a specific ABI

```bash
./gradlew assembleRelease -Pabi=arm64-v8a
```

Supported ABIs: `arm64-v8a, armeabi-v7a, x86, x86_64.`

---

## Project structure

```
Solara/
├── android/                  Android application
│   ├── app/                  Application module
│   │   └── src/main/
│   │       ├── java/com/solara/browser/
│   │       │   ├── data/     Room, DataStore, repositories
│   │       │   ├── domain/   Models, use cases
│   │       │   ├── engine/   JNI bindings to Optima and Solarium
│   │       │   ├── ui/       Compose screens, components, theme
│   │       │   └── di/       Hilt modules
│   │       ├── res/          Resources
│   │       └── jniLibs/      Native libraries (populated at build time)
│   ├── build.gradle.kts
│   └── settings.gradle.kts
├── docs/                     Architecture and design notes
├── scripts/                  Build and maintenance scripts
└── README.md
```

---

## Configuration

Settings are exposed in the app itself. For development, the following environment variables are recognised during build:

| Variable | Purpose |
|----------|---------|
| `APP_VERSION` | Override version name |
| `OPTIMA_VERSION` | Optima artifact version to depend on |
| `VERSION_SUFFIX` | Append a suffix to the version (e.g. -SNAPSHOT) |

---

## Testing

```bash
cd android
./gradlew test
./gradlew connectedAndroidTest
```

---

## Contributing

Contributions are welcome. Before starting, please read `CONTRIBUTING.md.`

For larger changes, open an issue first so we can align on the approach before you write code.

### The process:

1. Fork the repository.
2. Create a feature branch.
3. Make your changes, with tests where reasonable.
4. Run `./gradlew check` locally.
5. Open a pull request.

---

## Related repositories

Repository | Purpose |
|----------|---------|
| [Solaria](https://github.com/SolaraStudio/Solaria) | Android browser for older devices |
| [Optima](https://github.com/SolaraStudio/Optima) | Rendering - engine (Rust) |
| [Solarium](https://github.com/SolaraStudio/Solarium) | Runtime - engine (Zig) |
| [Solarian](https://github.com/SolaraStudio/Solarian) | Search provider API (Kotlin) |
| [web](https://github.com/SolaraStudio/web) | Website |
| [docs](https://github.com/SolaraStudio/docs) | Documentation |

---

## Acknowledgements

Solara stands on the work of others:

- [Jetpack Compose](https://developer.android.com/jetpack/compose) — declarative UI toolkit
- [Material 3](https://m3.material.io) — design system
- [Hilt](https://dagger.dev/hilt) — dependency injection
- [Room](https://developer.android.com/training/data-storage/room) — persistence

And the engines that make it a browser:

- [Optima](https://github.com/SolaraStudio/Optima) — rendering
- [Solarium](https://github.com/SolaraStudio/Solarium) — runtime
- [Solarian](https://github.com/SolaraStudio/Solarian) — search

---

## License

Solara is licensed under the Mozilla Public License 2.0. See LICENSE for the full text.
