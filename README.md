# KetchupDesktop
A pomodoro timer that generates session statistics using the
[KetchupAPI](https://github.com/softish/KetchupAPI).
The statistics are available at the
[KetchupDashboard](https://github.com/softish/KetchupDashboard).

## Getting started
Download and run a packaged version from the [Releases](https://github.com/softish/KetchupDesktop/releases). 
Make sure to meet the [Dependencies](./README.md#dependencies).

## Getting started - development
Import gradle project to favorite IDE.

### Packaging
Run gradle task `jfxJar` for minimal *jar* with external dependencies.

Run gradle task `fatJar` for *jar* with bundled dependencies.

Run gradle task `jfxNative` for package that bundles everything, 
including Java 8 for the currently used operating system.


## Dependencies
Oracle Java 8 or OpenJDK 8 and OpenJFX 8
