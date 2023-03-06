# Core Module

The core module contains basic components that are needed (in our opinion) for every microservice. A basic admin page, actuator configuration and basic metrics. 

## Usage

### Admin area
Based on the configured path for `management.endpoints.web.base-path`, there are some useful endpoints.

_/status_: TODO
_/logger_: TODO

### Status Detail Indicators
TODO

## InfoContributor
TODO



## configurations
| property                               | default | description                                        |
|----------------------------------------|---------|----------------------------------------------------|
| babbage.metrics.startup-metric.enabled | false   | If enabled, a metric for startup duration is added |
| babbage.status.useCommitAsVersion      | false   | _tbd_                                              |
