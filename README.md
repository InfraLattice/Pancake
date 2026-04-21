# Pancake

A low level-ish monitoring endpoint paper plugin for Minecraft paper servers. This plugin periodically polls a snapshot of in-game data for endpoints to pull from on request.

This plugin currently features support for both plaintext and json formats.

### Foreword
This was a bit of a pain to make, because there isn't much documentation and it's kinda tricky to map application layer concepts like HTTP onto Java with little documentation.



### Development Build

This project uses gradle to build the jar. 

**On windows:**

1. Clone the repository
2. Run gradlew.bat from mc_server_endpoints_plugin/src/gradlew.bat
3. Find the jar build in mc_server_endpoints_plugin/build/libs

**On Linux:**

1. Clone the repository
2. Run `sh gradlew` in the terminal on ~/mc_server_endpoints_plugin/src/gradlew
3. Find the build in mc_server_endpoints_plugin/build/libs