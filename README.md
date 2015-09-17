# Busca de Endereços através de CEP e Gerenciamento de Endereços via API REST

### Licença

<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.

## Visão Geral
A aplicação web Java foi desenvolvida buscando seguir as boas práticas REST apresentadas no [REST API Tutorial](http://www.restapitutorial.com/index.html) para as operações de CRUD.

O projeto foi desenvolvido utilizando:
* Spring Framework
* Banco de dados HSQL embedded
* JAX-RS (Jersey RESTful API & Jackson)
* Maven 
* JUnit & Mockito
* Jetty - Servidor HTTP/Servlet Container
* Slf4j & Log4J 2 

## Funcionalidades
A aplicação possui 2 principais funcionalidades:
* Um serviço para efetuar a busca de um endereço através do CEP fornecido.
* Serviços que efetuam o gerenciamento da informação de endereço.

## Build
Para se efetuar o build, iniciar o servidor e implementar (deploy) a aplicação, basta executar o comando:

> mvn jetty:run

O servidor estará pronto para receber requisições quando o log exibir a informação:

> [INFO] Started Jetty Server

## Utilização

Após a inicialização do servidor, a aplicação estará acessível através da URL:
> http://localhost:8080/rest

### Busca de Endereço por CEP

O serviço busca um endereço cadastrado através do parâmetro CEP fornecido com 8 dígitos. Exemplo:
> 01234999

Caso o CEP informado seja válido e não seja encontrado nenhum endereço correspondente, o serviço substitui os números do sufixo do CEP (3 últimos algarismos) por zero, da direita para a esquerda, até formar um CEP genérico (sufixo = "000").

* Método HTTP: GET
* URL: http://localhost:8080/rest/busca/ceps/{CEP}

### Gerenciamento de Endereços (CRUD)

Para as operações de CRUD, os seguintes serviços estão disponíveis:

### Create
Cria um novo registro de endereço, utilizando o payload informado.

* Método HTTP: POST
* URL: http://localhost:8080/rest/crud/enderecos

Payload de exemplo:

        {
          "cep": "01234999",
          "rua": "Rua Ficticia da Silva",
          "numero": "1234",
          "complemento": "Casa A",
          "bairro": "Vila Cinematografica",
          "cidade": "Rio de Janeiro",
          "estado": "RJ"
        }

### Read
Obtém o registro de endereço referenciado por {ID}.

* Método HTTP: GET
* URL: http://localhost:8080/rest/crud/enderecos/{ID}

### Update
Atualiza o registro de endereço referenciado por {ID}, utilizando o payload informado.

* Método HTTP: PUT
* URL: http://localhost:8080/rest/crud/enderecos/{ID}

Payload de exemplo:

        {
          "cep": "01234999",
          "rua": "Rua Ficticia da Silva",
          "numero": "1234",
          "complemento": null,
          "bairro": null,
          "cidade": "Rio de Janeiro",
          "estado": "RJ"
        }

### Delete
Apaga o registro de endereço referenciado por {ID}.

* Método HTTP: DELETE
* URL http://localhost:8080/rest/crud/enderecos/{ID}

## Testes
### GUI (SoapUI)
Através do [SoapUI](http://www.soapui.org/downloads/soapui/open-source.html), pode-se importar o arquivo WADL a seguir para se efetuar os testes da API REST:
> http://localhost:8080/rest/application.wadl

### Unitário (JUnit)
A aplicação pode ser testada utilizando-se o comando:
> mvn test
