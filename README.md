# Project Library Spring

The objective of this project is to test the Spring Boot umbrella.

![Project Library Diagran](https://raw.githubusercontent.com/ythalorossy/project-library-spring/master/Library-Project-Spring-Diagram.png)

## Modules

### Configuration Server

### Gateway 
Acts as a application gateway that used the Discovery server to find the services.

### OAuth2 Server
Manages the authentication of clients among the modules.
Exposes an API to manage client and scopes.

### Account
Acts as a service to maipulate users. Must be exclude

### Categories
Not implemented. It will act as category service.
### Books
Not implemented. It will act as a catalog of book service.
### Rents
Not implemented. It will act a book rent service.

## Spring projects on the project
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Web MVC Framework](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/#boot-features-spring-mvc)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Authorization Server](https://docs.spring.io/spring-authorization-server/reference/getting-started.html)
- [Spring Data](https://spring.io/projects/spring-data)
- [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Spring Cloud Open Feign](https://spring.io/projects/spring-cloud-openfeign)
- [Spring Cloud Netflix](https://spring.io/projects/spring-cloud-netflix)
- [Spring Cloud Netflix Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html)

## Containers
- [Docker Compose](https://docs.docker.com/compose/)

The project are using Docker Compose to up a postgres server

Upping the containers:  
`docker-compose up -d`

## References
[Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/)
[Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/2.2.x/reference/html/)