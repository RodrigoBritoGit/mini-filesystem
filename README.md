# Mini Filesystem

## Descrição

O **Mini Filesystem** é um sistema que permite a operação de CRUD em diretórios e arquivos. O sistema possui uma interface que possibilita ao usuário selecionar um diretório e visualizar os arquivos contidos nele. Além disso, é possível editar e excluir arquivos diretamente a partir da interface. O projeto foi desenvolvido com as seguintes tecnologias:

- **Backend**: Java, Spring Framework, Java 17
- **Frontend**: Vue.js
- **Banco de Dados**: PostgreSQL
- **Testes**: JUnit e Mockito
- **Containerização**: Docker

## Tecnologias

- **Java 17**: Linguagem de programação utilizada para o desenvolvimento do backend.
- **Spring Framework**: Framework utilizado para construir a aplicação backend.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **Vue.js**: Framework JavaScript progressivo para construção da interface do usuário.
- **Docker**: Plataforma de containerização que permite empacotar a aplicação e suas dependências.
- **JUnit e Mockito**: Bibliotecas utilizadas para a criação de testes unitários.

## Estrutura do Projeto

O projeto é dividido em três serviços principais:

1. **Backend (mini-filesystem)**: Responsável pela lógica de negócios e manipulação dos dados.
2. **Banco de Dados (PostgreSQL)**: Armazena as informações dos diretórios e arquivos.
3. **Frontend (vue-frontend)**: Interface do usuário para interação com o sistema.

## Como Executar o Projeto

### Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/) instalado na sua máquina.
- [Docker Compose](https://docs.docker.com/compose/install/) instalado.

### Passos

1. Clone o repositório:

   ```bash
   git clone https://github.com/RodrigoBritoGit/mini-filesystem.git
   cd mini-filesystem
   ```

2. Inicie os containers utilizando o Docker Compose:

   ```bash
   docker-compose up --build
   ```

3. Acesse a aplicação:

   - Backend: `http://localhost:8080`
   - Frontend: `http://localhost:8081`

## Principais Endpoints da Aplicação Backend

### Endpoints de Diretórios

- **GET** `http://localhost:8080/api/directories`
  - **Descrição**: Retorna uma lista de todos os diretórios.

- **POST** `http://localhost:8080/api/directories`
  - **Descrição**: Cria um novo diretório. O corpo da requisição deve conter os detalhes do diretório em formato JSON.

- **PUT** `http://localhost:8080/api/directories/{id}`
  - **Descrição**: Atualiza um diretório existente com o ID especificado. O corpo da requisição deve conter os detalhes atualizados do diretório em formato JSON.

- **DELETE** `http://localhost:8080/api/directories/{id}`
  - **Descrição**: Exclui o diretório com o ID especificado.

### Endpoints de Arquivos

- **GET** `http://localhost:8080/api/files/{directoryId}`
  - **Descrição**: Retorna uma lista de todos os arquivos contidos no diretório especificado pelo ID.

- **POST** `http://localhost:8080/api/files`
  - **Descrição**: Cria um novo arquivo. O corpo da requisição deve conter os detalhes do arquivo em formato JSON.

- **PUT** `http://localhost:8080/api/files/{id}`
  - **Descrição**: Atualiza um arquivo existente com o ID especificado. O corpo da requisição deve conter os detalhes atualizados do arquivo em formato JSON.

- **DELETE** `http://localhost:8080/api/files/{id}`
  - **Descrição**: Exclui o arquivo com o ID especificado.

- **POST** `http://localhost:8080/api/files/upload`
  - **Descrição**: Faz o upload de um arquivo. Deve incluir o arquivo como multipart e o ID do diretório ao qual o arquivo será associado.

- **GET** `http://localhost:8080/api/files/download/{id}`
  - **Descrição**: Faz o download do arquivo com o ID especificado. Retorna o conteúdo do arquivo como um byte array.


## Como Testar

Os testes unitários podem ser executados diretamente na IDE de sua preferência, ou via terminal acessando o diretório do projeto . Para isso, você pode utilizar o Maven e executar o seguinte comando:

1. Execute os testes:

   ```bash
   mvn test
   ```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests ou abrir issues para melhorias e correções.
