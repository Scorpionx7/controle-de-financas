# 🛠️ Sistema de Controle Financeiro - API

Este é o back-end do projeto de Controle Financeiro, desenvolvido para ser uma API REST que gerencia todas as regras de negócio e a persistência dos dados.

A ideia original de abandonar blocos de notas evoluiu para uma arquitetura desacoplada, onde este serviço centraliza a lógica de finanças e o front-end (desenvolvido em Angular) consome os dados.

---

## ✨ Arquitetura

O sistema agora segue uma arquitetura Cliente-Servidor:

-   **Back-end (Este repositório):** Uma API RESTful desenvolvida com Spring Boot, responsável por todas as operações de CRUD, cálculos e gerenciamento de dados.
-   **Front-end:** Uma aplicação SPA (Single Page Application) em Angular que consome esta API para fornecer a interface ao usuário.
    -   Acesse o repositório do front-end [**aqui**](https://github.com/Scorpionx7/controle-financas-frontend).

---

## 🌟 Funcionalidades da API

-   **Endpoints para gerenciamento de contas e gastos:**
    -   API para controle de parcelas restantes.
    -   Cálculo do saldo disponível para gastos.
    -   Endpoints para registro e acompanhamento de despesas mensais.

---

## 🔧 Tecnologias Utilizadas

-   **Java 21**: Base sólida e moderna para o desenvolvimento do back-end.
-   **Spring Boot 3**: Framework principal para a criação da API REST.
-   **PostgreSQL**: Banco de dados relacional robusto para persistência dos dados.
-   **JPA/Hibernate**: Para o mapeamento objeto-relacional e persistência de dados.
-   **Maven**: Gerenciador de dependências e build do projeto.

---

## 🚀 Como Executar o Back-end

### Pré-requisitos:

-   **Java 21** ou superior instalado.
-   **Maven** configurado no ambiente.
-   **PostgreSQL** instalado e um banco de dados criado para a aplicação.

### Passos para executar:

1.  Clone o repositório:
    ```bash
    git clone https://github.com/Scorpionx7/controle-de-financas.git
    cd controle-de-financas
    ```
2.  **Configure a conexão com o Banco de Dados**:
    -   Abra o arquivo `src/main/resources/application.properties`.
    -   Adicione ou altere as seguintes propriedades com os dados do seu banco PostgreSQL:

    ```properties
    # Exemplo de configuração para o PostgreSQL
    spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_seu_banco
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    
    # Configurações do Hibernate
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```
3.  **Compile e Execute o servidor**:
   ```bash
   mvn spring-boot:run
   ```
4.  **API estará disponível**:
    Após a execução, a API estará rodando e pronta para receber requisições na porta `8080`.
    -   Exemplo de endpoint: `http://localhost:8080/compras`

    > **Nota:** Este serviço não possui interface gráfica. Ele deve ser executado em conjunto com a aplicação [**front-end**](https://github.com/Scorpionx7/controle-financas-frontend).

---

## 📧 Contato

Entre em contato para dúvidas ou sugestões!

-   🌐 [**LinkedIn**](https://www.linkedin.com/in/estherrezende/)
-   📧 **E-mail:** [rezendealvesesther@gmail.com](mailto:rezendealvesesther@gmail.com)