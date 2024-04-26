# Changelog

## 0.3.1-SNAPSHOT
* _core_:
  * Fix: Just pass commit time from `git.properties` to status page, because the formats in the generated files are different.

## 0.3.0
* _core_:
  * Upgrade to bootstrap 5.3.3
  * Add status detail indicator infos to status page
  * Enhance git infos on status page

## 0.2.0
* Upgrade all modules to spring boot 3.2.2

## 0.1.0
* _core_:
  * Fix: Load bootstrap js in footer of logger console
* _validation_:
  * Feature: Expose validation message in http response body

## 0.0.3
* _core_: 
  * Fix: Logger overview now uses the correct links for changing log levels.
  * Fix: Long loggers are now scrollable and are not hidden behind the log-level buttons.
  * Feature: Add option to add custom management pages by extending your Controller with `ManagementController`. Documentation about is added [here](core/README.md)
* _validation_: 
  * add `SafeId` annotation to validation String input parameters 

## 0.0.2
* _aws-paramstore:_ Fix: Add missing `@ConfigurationProperties` for AWS ParamStore Properties.

## 0.0.1
* Initial version