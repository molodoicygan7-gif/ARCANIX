# ARCANIX Android starter

A native Android starter app with a dark navy / violet messenger-style interface.

## What works in this starter
- Native Android app project (Java, Android Gradle Plugin)
- Dark navy / violet theme
- Chat list, contacts, settings screens
- Demo chat screen with local-only message bubbles

## Important
This is a front-end prototype. Messages are local demo messages only. There is no account system, remote delivery, encryption, push notifications, or actual multi-user chat server yet. Do not use it for private or sensitive communication.

## Build APK from a phone using GitHub Actions
1. Create a GitHub repository and upload the contents of this folder to the repository (not the ZIP file itself).
2. Open the repository's **Actions** tab and enable Actions if prompted.
3. Choose **Build ARCANIX APK** and tap **Run workflow** (or push to `main`).
4. Open the completed workflow run and download the artifact named **ARCANIX-apk**.
5. Extract the downloaded artifact ZIP; it contains `app-debug.apk`.
6. Open the APK on Android and allow installation from that source if Android asks.

The APK is a debug build. For public distribution, configure a release signing key and review Android security/privacy requirements.
