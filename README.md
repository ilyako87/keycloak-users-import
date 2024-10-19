# Keycloak user migration (from Spring Boot)
This is a demo project that demonstrates how to migrate existing user accounts (including their passwords) to a 
keycloak instance.

Get the project up and running in just a few simple steps: 
- Execute the build (`mvn package`) command for the _[keycloak-bcrypt-provider](./keycloak)_ module
- Run the keycloak container using [the docker compose configuration](./keycloak/keycloak-docker.yaml)
- Build and run the [backend-user-server](./backend-user-sever) Spring Boot application *

Note: you may need to set an environment variable (CLIENT_SECRET) for authorization in keycloak admin API. 
To find out the value, sign in with default admin credentials and check the `user-import` OAuth client.

There are two endpoints available in the [backend-user-server](./backend-user-sever) Spring Boot application.  
In the default configuration, these are available under: 
 - [users](http://localhost:8080/users)
 - [migration](http://localhost:8080/migrate)