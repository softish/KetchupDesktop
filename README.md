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

Alternatively a text editor and gradle can be used.
The application can be started via gradle using the gradle task `run` or `bootRun`.

### Program arguments
The following program arguments are relevant during development:

    [an integer] - timer target (ms)
    dev - uses url localhost and port 8080

### Packaging
Run gradle task `jfxJar` for minimal *jar* with external dependencies.

Run gradle task `fatJar` for *jar* with bundled dependencies.

Run gradle task `jfxNative` for package that bundles everything, 
including Java 8 for the currently used operating system.


## Dependencies
Oracle Java 8 or OpenJDK 8 and OpenJFX 8
