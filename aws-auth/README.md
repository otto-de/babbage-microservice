# AWS Auth Module

This module provides a the `AwsCredentialsProvider` Bean, which is resolving the AWS Credentails from a chain of
providers. The configured providers and its order could be found
here: [AwsCredentialsConfiguration](src/main/kotlin/de/babbage/aws/auth/AwsCredentialsConfiguration.kt).

## Usage

Just create your required AWS client and inject the `AwsCredentialsProvider`. Example:
```
@Bean
fun ssmAsyncClient(awsCredentialsProvider: AwsCredentialsProvider): SsmAsyncClient {
    return SsmAsyncClient.builder()
        .credentialsProvider(awsCredentialsProvider)
        .build()
}
```

## Configurations:

| property                 | default value | description                                                                                                                             |
|--------------------------|---------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| babbage.aws.auth.profile | default       | Profile name for resolving aws credentials with `ProfileCredentialsProvider`, which is configured in provided `AwsCredentialsProvider`. |
