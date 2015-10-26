# find me 58

  这是我们参加 segmentfault hackathon 比赛的项目源码，主要功能是实现现场需求的发布。

## Install

    brew install gradle

## Build

    gradle build

## Run

    gradle bootRun

## Check out

    $ curl localhost:8080
    Greetings from Spring Boot!

## h2

   view data in [web](http://localhost:8080/console)

   * set profile to development
   * modify `JDBC URL`

        jdbc:h2:mem:testdb
