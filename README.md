# frc-2025

**Team 2204 Rambots: FRC REEFSCAPE 2025**

This repository contains the robot code for Team 2204's 2025 FRC season. The project is built using WPILib and integrates tools like AdvantageKit and AdvantageScope for enhanced telemetry and debugging.

---
```markdown

Project Overview

This robot code controls a custom swerve-drive robot designed for the 2025 FRC game. It includes:

- Subsystems for drivetrain, autonomous behavior, and sensors
- Command-based architecture for modularity
- Telemetry logging via AdvantageKit
- Simulation support and deployment-ready build configuration

---

Project Structure

- `src/main/java`: Main robot source code
- `.run/`: IntelliJ run configurations for deploying and simulating
- `.github/workflows/`: GitHub Actions CI setup
- `build.gradle` / `settings.gradle`: Gradle build files
- `AdvantageScope Swerve Calibration.json`: Calibration data for AdvantageScope
- `src/main/deploy/pathplanner`: PathPlanner autonomous routines and auto align positions
- `src/main/java/config`: Constants files
- `src/main/java/frc/robot`: Subsystems and Commands
- `src/main/java/frc/robot/robotcontainer`: Initializations and button mappings

---
```

## Getting Started

### Prerequisites

- Java 11+
- WPILib (2025 version)
- Gradle
- FRC Driver Station
- [AdvantageScope](https://github.com/Mechanical-Advantage/AdvantageScope) (optional for telemetry visualization)

### Installation

```bash
git clone https://github.com/frc2204/frc-2025.git
cd frc-2025
./gradlew build
````

### Deploy to RoboRIO

```bash
./gradlew deploy
```

---

## 🛠️ Development

To simulate:

```bash
./gradlew simulateJava
```

To run unit tests:

```bash
./gradlew test
```

---

## 📋 License

* WPILib: See `WPILib-License.md`
* AdvantageKit: See `AdvantageKit-License.md`