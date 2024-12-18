
# DummyJSON Client - Java 8 e Spring Boot 2.x.x

## Descrição do Projeto

Este projeto é um microsserviço Java que interage com a API pública [DummyJSON](https://dummyjson.com/docs/products) para realizar operações de busca de produtos. O projeto foi desenvolvido usando Java 8 e Spring Boot 2.6.x.

## Objetivo do Desafio

O desafio consiste em migrar este projeto para Java 17 e Spring Boot 3.2.5. Durante a migração, você enfrentará várias dificuldades, incluindo a adaptação ao novo namespace, substituição de métodos depreciados e ajustes em testes unitários.

## Funcionalidades

- **Consulta de Produtos**: Realiza chamadas para a API do DummyJSON para buscar informações sobre produtos.
- **Integração com `RestTemplate`**: Utiliza `RestTemplate` para realizar chamadas HTTP.
- **Validação de Dados**: Validação de dados de entrada usando Bean Validation (`javax.validation`).
- **Gestão de Dependências**: Configurado para utilizar @Autoweird.
- **Testes Unitários**: Inclui testes unitários desenvolvidos com JUnit 4 e Mockito.

## Estrutura do Projeto

```bash
dummyjson-client
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.dummyjsonclient
│   │   │       ├── DummyJsonClientApplication.java
│   │   │       ├── config
│   │   │       │   └── WebClientConfig.java
│   │   │       ├── controller
│   │   │       │   └── ProductController.java
│   │   │       ├── dto
│   │   │       │   └── Product.java
│   │   │       ├── service
│   │   │       │   └── ProductService.java
│   │   └── resources
│   │       └── application.yaml
│   └── test
│       ├── java
│       │   └── com.example.dummyjsonclient
│       │       ├── config
│       │       │   └── RestTemplateConfigTest.java
│       │       └── controller
│       │       │   └── ProductControllerTest.java
│       │       ├── dto
│       │       │   └── ProductTest.java
│       │       └── service
│       │           └── ProductServiceTest.java
│       └── resources
└── pom.xml
```

## Passos para Executar o Projeto

### Pré-requisitos

- **Java 8**
- **Maven 3.8.x**

### Executar a Aplicação

1. Clone o repositório:

    ```bash
    git clone https://github.ibm.com/Wendell-Santos/code-challenge-migration.git
    cd dummyjson-client
    ```

2. Compile e execute o projeto:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

3. Acesse o serviço:

    O serviço estará disponível em `http://localhost:8080`.

### Executar Testes

Para executar os testes unitários:

```bash
mvn clean test
```

## Requisitos de Entrega

1. Atualizar o `pom.xml` para usar Java 17+ e Spring Boot 3.2.5.
2. Substituir `RestTemplate` por `WebClient` ou `Openfeign`.
3. Substituir os testes unitários feitos com `JUnit 4` e `Mockito` por testes de integração utilizando `@SpringBootTest`.
4. Refatorar qualquer código depreciado ou incompatível.
5. Garantir que todos os testes ainda passam após a migração.
6. Deixar a URL da API dummyjson parametrizada por ambiente no projeto.
7. Adicionar no projeto um novo path `/health` que retorna a saude do microsserviço.

## Validação Sobre o Challenge

- O projeto deve estar funcionando em Java 17 e Spring Boot 3.2.5.
- Todos os testes unitários devem ser executados e passar sem falhas.
- O código deve estar devidamente documentado e organizado.

## Extras

- Entregar o projeto em container será um diferencial.
- Fica a critério do desenvolvedor inserir ou remover dependencias do projeto para garantir o objetivo do challenge.

## DOCUMENTAÇÃO DO DESENVOLVIMENTO:
- Os comandos de execução não foram alterados
- O desenvolvimento seguiu a ordem dos requisitos de entrega

1 - Atualização Java 17+ e Spring Boot 3.2.5
- referências antigas do Java foram removidas e adicionadas:
> <java.version>17</java.version>

> <<groupId>org.springframework.boot</groupId>>
> <<artifactId>spring-boot-starter-parent</artifactId>>
> <<version>3.2.5</version>>


2 - Substituir RestTemplate por WebClient ou Openfeign
 - Openfeign não compatível.
Escolhido WebClient, para isso foi necessário acrescentar: rg.springframework.boot
refatorado métodos de busca para utilizar WebClient
refatorado arquivo config para o novo tipo

2 - Remover Junit4 e mockito
 - removido: mockito; adicionado: SpringBootTest
 - removido: junit4; adicionado: junit5
 - Foi necessário alterar os testes unitários do Service e Controller

3 - Deixar a URL da API dummyjson parametrizada por ambiente no projeto.
 - Para cada ambiente Dev/Prod foi criado um arquivo .yml com a configuração adequada. Neste projeto, a mesma url está sendo usada para abos ambientes.

4 - path /health que retorna a saude do microsserviço
 - Criado uma nova controller com o método GET no qual responde Status: UP e dara e hora da chamada.
