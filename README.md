# Rest API
Projeto com CRUD de usuário utilizando Docker, Kotlin e spring boot 

## O quer é o projeto?
Projeto consiste em um gerenciamento de usuário onde podemos cadastrar, atualizar, deletar e fazer consultas por id do usuário e pelo nome, na pasta raiz do projeto há um arquivo [Collection](https://github.com/jsilvamarques/restapi/blob/master/restapi.postman_collection.json) com as chamadas e seus respectivos parâmetros, além desta collection após a subida da aplicação também teremos o [Swagger](http://localhost:8080/swagger-ui.html) onde há uma documentação das apis e a possibilidade de testes direto na mesma. Abaixo as rotas disponíveis na aplicação.

## Tecnologias:
- Docker
- Docker-compose
- Java
- Spring boot
- Kotlin
- Banco de dados postgres
- Swagger 

## Como rodar:
No diretório do projeto há um arquivo docker-compose.yml com os serviços de banco e da aplicação, ele utiliza o DockerFile o qual já se encontra-se com o processo de build e subida da aplicação.

**Comando para subida:** docker-compose up -d

## Rotas disponíveis:
- Post - http://localhost:8080/v1/users - salva um novo usuário .
- GET - http://localhost:8080/v1/users - busca a lista de usuários .
- GET - http://localhost:8080/v1/users/{id} – busca usuário por id.
- GET - http://localhost:8080/v1/users?name={name} - busca usuário pelo nome (mesma api de 
- PATCH - http://localhost:8080/v1/users/{id} – faz a atualização do usuário.
- DELETE - http://localhost:8080/v1/users/{id} – faz o delete do usuário.

