# Gerenciador de CEP via API REST (tests-gdantas-ns_1_2)
## Visão Geral
A aplicação web Java foi desenvolvida buscando seguir as boas práticas REST apresentadas no [REST API Tutorial](http://www.restapitutorial.com/index.html) para as operações de CRUD.

O projeto foi desenvolvido utilizando:
* Spring Framework
* Banco de dados HSQL embedded
* Jersey RESTful API (JAX-RS)
* Maven 
* Jetty - Servidor HTTP/Servlet Container

## Build
Para se efetuar o build, iniciar o servidor e implementar (deploy) a aplicação, basta executar o comando:

> mvn jetty:run

O servidor estará pronto para receber requisições quando o log exibir a informação:

> [INFO] Started Jetty Server

## Utilização

Após a inicialização do servidor, a aplicação estará acessível através da URL:
> http://localhost:8080/rest/testes/ws-ns1

As operações de CRUD do CEP estão acessíveis através das seguintes URLs associadas aos respectivos verbos HTTP:

### POST (Create)
* URL: http://localhost:8080/rest/testes/ws-ns1/ceps/
* Payload de exemplo: 
        {
          "cep": "01234999",
          "rua": "Rua Fictícia da Silva",
          "numero": "1234",
          "complemento": "Casa A"
          "bairro": "Vila Cenomatográfica",
          "cidade": "Rio de Janeiro",
          "estado": "RJ"
        }

### GET (Read)
* URL: http://localhost:8080/rest/testes/ws-ns1/ceps/{CEP}

### PUT (Update)
* URL: http://localhost:8080/rest/testes/ws-ns1/ceps/{CEP}
* Payload de exemplo: 
        {
          "cep": "01234999",
          "rua": "Rua Fictícia da Silva",
          "numero": "1234",
          "complemento": "Casa A"
          "bairro": "Vila Cenomatográfica",
          "cidade": "Rio de Janeiro",
          "estado": "RJ"
        }

### DELETE (Delete)
* URL: http://localhost:8080/rest/testes/ws-ns1/ceps/{CEP}

## Testes
### GUI (SoapUI)
Através do [SoapUI](http://www.soapui.org/downloads/soapui/open-source.html), pode-se importar o arquivo WADL a seguir para se efetuar os testes da API REST:
> http://localhost:8080/rest/application.wadl

### Unitário (JUnit)
A aplicação pode ser testada utilizando-se o comando:
> mvn test
