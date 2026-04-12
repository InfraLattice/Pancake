# Pancake

A low level-ish monitoring endpoint paper plugin for Minecraft paper servers.

### Foreword
This was a bit of a pain to make, because there isn't much documentation and it's kinda tricky to map application layer concepts like HTTP onto Java with little documentation.



### Development Build

This project uses gradle to build the jar. 

**On windows:**

1. Download Gradle from the official website: https://gradle.org/releases/
2. Clone the repository
3. Run gradlew.bat from mc_server_endpoints_plugin/src/gradlew.bat
4. Find the jar build in mc_server_endpoints_plugin/build/libs

**On Linux:**

1. Use a package manager or install zip manually from https://gradle.org/releases/
2. Clone the repository
3. Run `sh gradlew` in the terminal on ~/mc_server_endpoints_plugin/src/gradlew
4. Find the build in mc_server_endpoints_plugin/build/libs