# short-url

In this project,you will know what really does windward can do,And find out how to implement a short url service,
Also,You will learn a different way to Java web application without Spring.
First of all,We must do

```shell
 mvn clean compile
```

## API Glance

| relative path | method | querystring | description                       |
|---------------|--------|-------------|-----------------------------------|
| /v1/url       | post   | originUrl   | generate short url for origin url |
| /v1/url/list  | get    | -           | select all generated url          |
| /s/{url}      | get    | -           | access short url                  |

package binary file

```shell
mvn -Pnative package
```

compress binary file

```shell
upx --best ./target/shortUrl
```