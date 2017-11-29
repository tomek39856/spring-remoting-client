# Spring Remoting Client

Simple library that allows you to connect to legacy API based on Spring Remoting without adding any Spring Framework dependencies to your project.
Library has no runtime dependencies.

## Usage examples

### Call of "echo" method from Spring Remoting service

Create service proxy factory

```
HttpProxyFactory httpProxyFactory = new HttpProxyFactory<>(TestService.class, "http://localhost:" + port + "/test");
```

Add request properties provider (optional)

```
httpProxyFactory.setRequestPropertiesProvider(() -> {
            return new HashMap<String, String>() {{
                put("Authentication", "someToken");
            }};
       });
```

Build service proxy

```
TestService testInterface = (TestService) httpProxyFactory.buildProxy();
```

Call remote service and store execution result in local variable

```
String response = testInterface.echo("test");
```

## Prerequisites

```
Java 1.8 or higher
```
## Installation

```
mvn install
```

## Tests

Library containt simple junit test which starts simple http server on 9988 port and deploys spring remoting service.
Please ensure that 9988 port is free during test execution.

```
mvn test
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Tomasz Tokarczyk** (https://github.com/tomek39856)
