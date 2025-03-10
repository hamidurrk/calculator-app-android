# Calculator App

## Overview

This is a simple, user-friendly Android calculator app designed to perform basic arithmetic operations. It provides a clean and intuitive interface for adding, subtracting, multiplying, and dividing numbers. The app also includes several quality-of-life features to enhance the user experience.

## Demonstration Video

https://drive.google.com/file/d/1ef5FBn7xqBKp078hUdZoVlhhjhThzKXZ/view?usp=sharing

## Features

*   **Basic Arithmetic Operations:**
    *   Addition (+)
    *   Subtraction (-)
    *   Multiplication (*)
    *   Division (/)
*   **Dual Input Fields:** Two input fields for entering numbers, and a dedicated result field to display the outcome.
*   **Input Validation:** Prevents invalid number inputs and displays "Invalid" when an error is detected.
*   **Clear Operations:**
    *   **All Clear (AC):** Clears both input fields and the result.
    *   **Delete (DEL):** Deletes the last entered digit in the active input field. Long press the delete button to delete all.
*   **Negative Numbers:** Support for entering and performing operations with negative numbers.
*   **Decimal Input:** Support for floating point numbers.
*   **Swap Inputs:** A "swap" button to quickly exchange the values between the two input fields.
*   **Focus Control:** A dedicated button to quickly switch the active input field.
*   **Visual Feedback:**
    *   Highlighted operation buttons to clearly indicate the currently selected operation.
    *   Error indicators for invalid inputs (the background becomes red).
*   **Onboarding Tutorial:** A set of instructions displayed the first time a user opens the app.

## Getting Started

### Prerequisites

*   Android Studio installed on your machine.
*   An Android emulator or a physical Android device for testing.

### Installation

1.  Clone this repository to your local machine:
   ``` bash
git clone https://github.com/hamidurrk/calculator-app-android.git
   ```
2.  Open the project in Android Studio.
3.  Connect an Android emulator or a physical device.
4.  Click the "Run" button in Android Studio to build and run the app.

### Usage

1.  Enter the first number in the `top input field`.
2.  Enter the second number in the `bottom input field`.
3.  Select the desired operation (`+`, `-`, `*`, `/`) by tapping on the corresponding button.
4.  The result will be displayed in the `result field`.
5. Use `AC` to clear all. Use `DEL` to delete digits. Long press `DEL` to delete all.
6. Tap `flip` to change the input.
7. Tap the `down arrow` to change the input.

## Project Structure

*   **`app/src/main/java/com/main/calculator/`**:
    *   `MainActivity.java`: Contains the main application logic, including:
        *   UI element management.
        *   Button click listeners.
        *   Input validation.
        *   Calculation functions.
        *   Onboarding tutorial logic.
*   **`app/src/main/res/layout/`**:
    *   `activity_main.xml`: Defines the user interface layout, including the input fields, result field, and buttons.
*   **`app/src/main/res/values/`**:
    *   `colors.xml`: Defines the colors used in the app.
    *   `strings.xml`: Defines string resources for the app.
    *   `themes.xml`: Defines the styles and themes used in the app.
* **`app/src/main/AndroidManifest.xml`**:
    * This file contains the app details and permissions.

## Development Details

*   **Language:** Java
*   **UI Framework:** XML layouts with Material Components.
*   **Key Concepts:**
    *   Event handling with listeners (e.g., `OnClickListener`, `OnTouchListener`, `TextWatcher`).
    *   UI updates on the main thread.
    *   Input validation and error handling.
    *   User onboarding.
    *   Accessibility.
*   **Best Practices:** The project attempts to follow Android best practices for UI design, user experience, and code organization.
