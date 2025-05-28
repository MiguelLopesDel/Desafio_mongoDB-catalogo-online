# Desafio Catálogo de Produtos

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar um catálogo de produtos e categorias. Ele utiliza MongoDB como banco de dados e se integra com o AWS Simple Notification Service (SNS) para publicar eventos de criação e atualização de entidades. A aplicação também inclui suporte para containerização com Docker.

## Sumário

*   [Funcionalidades Principais](#funcionalidades-principais)
*   [Tecnologias Utilizadas](#tecnologias-utilizadas)
*   [Arquitetura](#arquitetura)
*   [Pré-requisitos](#pré-requisitos)
*   [Configuração do Ambiente](#configuração-do-ambiente)
    *   [Clonando o Repositório](#clonando-o-repositório)
    *   [Variáveis de Ambiente](#variáveis-de-ambiente)
    *   [Configuração do MongoDB](#configuração-do-mongodb)
    *   [Configuração do AWS SNS](#configuração-do-aws-sns)
*   [Como Executar](#como-executar)
    *   [Opção 1: Com Docker Compose (Recomendado)](#opção-1-com-docker-compose-recomendado)
    *   [Opção 2: Localmente com Maven](#opção-2-localmente-com-maven)
*   [Endpoints da API](#endpoints-da-api)
    *   [Categorias (`/api/category`)](#categorias-apicategory)
    *   [Produtos (`/api/product`)](#produtos-apiproduct)
*   [Integração AWS SNS](#integração-aws-sns)
*   [Estrutura do Projeto](#estrutura-do-projeto)
*   [Licença](#licença)

## Funcionalidades Principais

*   **Gerenciamento de Categorias:**
    *   `POST /api/category`: Criar nova categoria.
    *   `GET /api/category`: Listar todas as categorias.
    *   `PUT /api/category/{id}`: Atualizar categoria existente.
    *   `DELETE /api/category/{id}`: Deletar categoria.
*   **Gerenciamento de Produtos:**
    *   `POST /api/product`: Criar novo produto (associado a uma categoria).
    *   `GET /api/product`: Listar todos os produtos.
    *   `PUT /api/product/{id}`: Atualizar produto existente.
    *   `DELETE /api/product/{id}`: Deletar produto.
*   **Integração com AWS SNS:**
    *   Publica automaticamente uma mensagem em um tópico SNS quando um produto ou categoria é criado ou atualizado.
*   **Suporte a Docker:**
    *   `Dockerfile` para construir a imagem da aplicação.
    *   `docker-compose.yml` para orquestrar a aplicação e o serviço MongoDB em ambiente de desenvolvimento.

## Tecnologias Utilizadas

*   **Linguagem:** Java 17+
*   **Framework Principal:** Spring Boot 3.2.3
    *   **Spring Web:** Para desenvolvimento de APIs RESTful.
    *   **Spring Data MongoDB:** Para abstração e facilitação da interação com o MongoDB.
*   **Banco de Dados:** MongoDB (NoSQL)
*   **Mensageria:** AWS Simple Notification Service (SNS)
*   **SDKs:** AWS SDK para Java v1 (1.12.783)
*   **Build e Gerenciamento de Dependências:** Maven
*   **Utilitários:** Lombok (para redução de código boilerplate)
*   **Containerização:** Docker, Docker Compose
*   **Variáveis de Ambiente:** spring-dotenv (para carregar variáveis de arquivos `.env`)

## Arquitetura

O projeto segue uma arquitetura em camadas típica de aplicações Spring Boot:

*   **Controller Layer (`controller`):** Responsável por expor os endpoints da API REST, receber requisições HTTP, validar entradas básicas e delegar o processamento para a camada de serviço.
*   **Service Layer (`service`):** Contém a lógica de negócio principal da aplicação. Coordena as interações entre os repositórios e outros serviços (como o `AwsSnsService`).
*   **Repository Layer (`repository`):** Interfaces que estendem `MongoRepository` (Spring Data MongoDB) para fornecer abstração sobre as operações de acesso a dados com o MongoDB.
*   **Domain Layer (`domain`):** Contém as entidades (`Category`, `Product`), Data Transfer Objects (DTOs - `CategoryDTO`, `ProductDTO`, `MessageDTO`) e exceções customizadas.
*   **Configuration Layer (`config`):** Classes de configuração para beans do Spring, como a configuração do cliente AWS SNS e a configuração do MongoDB.

## Pré-requisitos

Antes de iniciar, garanta que você possui os seguintes softwares instalados e configurados:

*   **JDK (Java Development Kit):** Versão 17 ou superior.
*   **Maven:** Versão 3.6 ou superior (para compilação e gerenciamento de dependências).
*   **Docker e Docker Compose:** (Recomendado para facilitar a execução e gerenciamento do ambiente).
*   **Git:** Para clonar o repositório.
*   **MongoDB:**
    *   Se não for usar Docker, uma instância do MongoDB deve estar rodando e acessível.
    *   Se usar Docker Compose, ele cuidará da instância do MongoDB.
*   **Conta AWS:**
    *   Credenciais de acesso (Access Key ID e Secret Access Key) com permissões para `sns:Publish`.
    *   Um tópico SNS previamente criado na região desejada.

## Configuração do Ambiente

### Clonando o Repositório

Para obter o código do projeto, clone o repositório usando o seguinte comando:

```bash
git clone git@github.com:MiguelLopesDel/Desafio_mongoDB-catalogo-online.git

cd desafio_catalogo

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```
# MongoDB
MONGODB_URI=mongodb://localhost:27017/product-catalog
MONGODB_DATABASE=product-catalog

# AWS SNS
AWS_REGION=sua-region
AWS_ACCESS_KEY=sua-access-key
AWS_SECRET_KEY=sua-secret-key
AWS_SNS_TOPIC_ARN=seu-arn

### Configuração do AWS SNS

1. Acesse o console da AWS e navegue até o serviço SNS
2. Crie um novo tópico SNS chamado `catalog-topic`
3. Copie o ARN do tópico para usar na variável de ambiente `AWS_SNS_TOPIC_ARN`
4. Crie um usuário IAM com permissões para `sns:Publish`
5. Gere as credenciais (Access Key e Secret Key) para esse usuário

## Como Executar

### Opção 1: Com Docker Compose (Recomendado)

1. Certifique-se de que Docker e Docker Compose estão instalados
2. Configure as variáveis de ambiente no arquivo `.env`
3. Execute o comando:

```bash
docker-compose up -d
```

A aplicação estará disponível em: http://localhost:8080

### Opção 2: Localmente com Maven

1. Certifique-se de que possui JDK 17+ e Maven instalados
2. Configure as variáveis de ambiente no arquivo `.env`
3. Certifique-se de que o MongoDB está em execução
4. Execute os comandos:

```bash
mvn clean package
java -jar target/desafio_catalogo-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: http://localhost:8080

## Endpoints da API

### Categorias (`/api/category`)

#### Criar categoria
- **Endpoint:** `POST /api/category`
- **Corpo da requisição:**
  ```json
  {
    "title": "Eletrônicos",
    "description": "Produtos eletrônicos diversos",
    "ownerId": "2903290"
  }
  ```
- **Resposta:** Categoria criada (201 Created)

#### Listar categorias
- **Endpoint:** `GET /api/category`
- **Resposta:** Lista de todas as categorias (200 OK)

#### Atualizar categoria
- **Endpoint:** `PUT /api/category/{id}`
- **Corpo da requisição:**
  ```json
  {
    "title": "Eletrônicos Atualizados",
    "description": "Nova descrição"
  }
  ```
- **Resposta:** Categoria atualizada (200 OK)

#### Deletar categoria
- **Endpoint:** `DELETE /api/category/{id}`
- **Resposta:** Sem conteúdo (204 No Content)

### Produtos (`/api/product`)

#### Criar produto
- **Endpoint:** `POST /api/product`
- **Corpo da requisição:**
  ```json
  {
          "title": "Smartphone",
          "description": "Smartphone avançado",
          "ownerId": "id-do-proprietario",
          "price": 1999.99,
          "categoryId": "id-da-categoria"
  }
        
  ```
- **Resposta:** Produto criado (201 Created)
    ```json
    {
          "id": "id-do-produto",
          "title": "Smartphone",
          "description": "Smartphone avançado",
          "ownerId": "id-do-proprietario",
          "price": 1999.99,
          "category": "id-da-categoria"
    }
    ```

#### Listar produtos
- **Endpoint:** `GET /api/product`
- **Resposta:** Lista de todos os produtos (200 OK)

#### Atualizar produto
- **Endpoint:** `PUT /api/product/{id}`
- **Corpo da requisição:**
  ```json
  {
    "title": "Smartphone Atualizado",
    "price": 1899.99
  }
  ```
- **Resposta:** Produto atualizado (200 OK)

#### Deletar produto
- **Endpoint:** `DELETE /api/product/{id}`
- **Resposta:** Sem conteúdo (204 No Content)

## Integração AWS SNS

A aplicação publica automaticamente mensagens no tópico SNS configurado quando:

1. Uma categoria é criada ou atualizada
2. Um produto é criado ou atualizado

As mensagens contêm uma representação em string da entidade modificada. Para configurar inscrições para receber estas notificações:

1. Acesse o console da AWS
2. Navegue até o serviço SNS
3. Selecione o tópico `catalog-topic`
4. Clique em "Create subscription"
5. Escolha o protocolo (Email, SMS, SQS, Lambda, etc.)
6. Configure o endpoint (endereço de email, ARN da função Lambda, etc.)

## Estrutura do Projeto

```
desafio_catalogo/
├── src/main/java/com/miguel/catalogchallenge/
│   ├── config/             # Configurações de beans Spring
│   ├── controller/         # Endpoints da API REST 
│   ├── domain/             # Entidades, DTOs e exceções
│   ├── repository/         # Interfaces para acesso ao MongoDB
│   ├── service/            # Lógica de negócio e integrações
│   └── CatalogChallengeApplication.java
├── src/main/resources/
│   └── application.properties  # Configurações da aplicação
├── .env                    # Variáveis de ambiente (não versionado)
├── docker-compose.yml      # Configuração do ambiente Docker
├── Dockerfile              # Instruções para build da imagem
├── mvnw                    # Script para execução do Maven
├── pom.xml                 # Configuração do Maven e dependências
└── README.md               # Documentação do projeto
```

## Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).