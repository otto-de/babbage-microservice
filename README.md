# Babbage Microservice
[Babbage](https://de.wikipedia.org/wiki/Charles_Babbage) originated the concept of a digital programmable computer.

## About

The babbage microservice library provides basic implementation based on a reactive spring boot application to speed up the setup of a microservice. 
It's the little brother from [edison-microservice](https://github.com/otto-de/edison-microservice), which is the original non reactive implementation.

## Modules
| Module                                     | Description                                           |
|--------------------------------------------|-------------------------------------------------------|
| [core](core/README.md)                     | Basic management page (Status, Loggers, Metrics etc.) |
| [aws-auth](aws-auth/README.md)             | AWS Credentials Provider Chain                        |
| [aws-paramstore](aws-paramstore/README.md) | Property resolver from AWS Parameter Store            |


## How to release

1. Update [Changelog](CHANGELOG.md).
2. Check that all dependencies are update to date
   1. `buildSrc/build.gradle.kts`.
   2. `buildSrc/src/main/kotlin/babbage.conventions.gradle.kts`.
3. Update version in `buildSrc/src/main/kotlin/babbage.conventions.gradle.kts`.
4. Run `go publish` in root folder
5. Update version to next snapshot