# Stylized Timer App (Java)

A timer application written in Javan using Swing and Java2D. 
Users can input a time which the application counts down and displays animated visual effects and plays a sound when timer completes. 


https://github.com/user-attachments/assets/1a00bc9b-c13f-48f2-9afd-739e6f331937


---

## Overview

This is a self-contained timer application developed using Java Swing and Java2D. The app allows users to enter a time and displays a live countdown in a clean, stylized interface. When the timer reaches zero, a sound is played and animated stars are showcased.

The project focuses on custom rendering, animation timing, and user interaction. This application is implemented entirely with standard Java libraries and no frameworks. 

---

## Features

- Single mouse click to edit timer input
- Accepts time input from keyboard as:
  - Numeric minutes (e.g. `5`)
  - Full time format (`HH:MM:SS`)
- Double-click to cancel and stop timer
- Animated star effects upon completion
- Sound playback upon completion
- Soft blurred timer display using buffered images
- No external dependencies

---

## How to Use

1. Launch the application
2. The timer starts at `00:00:00` in the bottom-right corner
3. Click on the timer text to begin editing
4. Enter a time:
   - Numeric value (minutes), or
   - Formatted time (`HH:MM:SS`)
5. Press Enter key to start the timer
6. Double-click at any time to cancel the current run
7. When the timer reaches zero:
   - A sound plays
   - White star animations appear and twinkle on screen
8. To end time simply click anywhere on desktop screen

---

## Running the App

The project is a self-contained Java application and does not require any external dependencies.

### Option 1: Run via JAR
- Download or build the runnable JAR
- Run the JAR directly to launch the application

### Option 2: Run from Source (NetBeans or IDE)
- Open the project in NetBeans (or another Java IDE)
- Run the `Clock.java` class, which serves as the main entry point

All required resources, including audio files, are bundled with the source.

---

## Technologies & Libraries Used

- Java (Standard Library only)
- Swing ('JFrame', 'JPanel')
- Java2D ('Graphics2D', 'BufferedImage')
- 'Timer'
- Image processing ('Kernel', 'ConvolveOp')
- Audio playback ('InputStream', 'AudioInputStream')

No third-party libraries were used.

---

## Design & Rendering Focus

A central goal of this project was visual clarity and softness. The timer text is rendered in white against a warm orange background gradient to maintain readability against white or light backgrounds. A blur effect is applied to the display through the use of drawing the text to a `BufferedImage` and applying convolution filters.

The star animation applies scaling onto a StarShapes object (created using Shapes and Gradient Paints). It also applies staggered timing to simulate twinkling, creating subtle motion without overwhelming the interface. Visual decisions were made intentionally to create a calm, cohesive desktop experience.

---

## Project Structure
src/
├── Clock.java # Main application entry point
├── Sound.java # Audio playback handling
├── Star.java # Star animation logic
├── StarShapes.java # Star shape rendering utilities
├── sparkle2.wav # Sound played when timer completes
└── test.java # Experimental / testing code









