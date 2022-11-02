# sumador
Aplicación Spring Boot para sumar dos números

## Instrucciones de uso

Para levantar la aplicación, la forma más sencilla es hacerlo utilizando la imagen publicada en [DockerHub](https://hub.docker.com/repository/docker/dcid/sumador). Para ello, existe el archivo *src/main/docker/docker-compose.yml*, basta con ejecutar el comando ``` docker compose up -d ```, esto iniciará la applicación en el puerto 8080.

Para conocer la definición de la API, se incorpora documentación OpenAPI v3, cuya interfaz es accesible desde la url: http://localhost:8080/swagger-ui/index.html. 

Una vez aqui, se debe crear un usuario en el sistema, consumiendo el endpoint */auth/signup*. 

Luego se debe generar las credenciales para consumir el resto de los endpoint, consumiendo el endpoint */auth/signin*.

Las llamadas a los demás endpoints deben incluir el header *Authorization* con el contenido: *Bearer < accessToken >*.
