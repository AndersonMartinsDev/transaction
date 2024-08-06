
# Projeto Transações
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)  ![Static Badge](https://img.shields.io/badge/autor-Anderson-blue)
![Static Badge](https://img.shields.io/badge/stack-java17-blue)  ![Static Badge](https://img.shields.io/badge/framework-spring-green)


        O Transações é uma api completa para gestão de transações feitas com cartão de beneficios, 
    projetado para facilitar o processo de autorizações de transacões. Construído com Java 17 e Spring Framework,o 
    Transações oferece recursos robustos e uma arquitetura robusta baseada em Onion Architecture.


## Documentation


#### Gerenciamento de Transações:

Envie os dados da transação
Feita a validação dos dados e mcc 
Retorno da transação.

#### Gerenciamento de Contas:

Cadastre novas contas e mccs.


## Tech Stack


**Linguagem de Programação:** Java 17

**Swagger**

**Framework Web:** Spring Framework

**Arquitetura:** Onion Architecture

**Banco de Dados:** MYSQL


## Installation

Pré-requisitos:
```bash
    Java 17 instalado
    Maven instalado
    Banco de dados configurado e acessível
    Etapas de Instalação:

    Clone o repositório do projeto.
    Instale as dependências do projeto usando o Maven.
    Configure o banco de dados nas propriedades do projeto.
    Execute a aplicação Spring Boot.
```


| Variables | Type     | Description                                                     |
| :-------- | :------- |:----------------------------------------------------------------|
| `DDL_SPRING_DATABASE` | `string` | create, update, none                                            |
| `DRIVERCLASS_SPRING_DATABASE` | `string` | your database driver                                            |
| `USER_SPRING_DATABASE` | `string` | your database user                                              |
| `PASSWORD_SPRING_DATABASE` | `string` | your database password                                          |
| `DATABASE_HIBERNATE_DIALECT` | `string` | your database platform Ex:org.hibernate.dialect.MySQLDialect    |
| `URL_SPRING_DATABASE` | `string` | your database host url Ex:jdbc:mysql://127.0.0.1:3306/transacao |

*Obs: O banco de dados é criado automaticamente, basta indicar o host*

## Swagger

**Essa aplicação conta com SWAGGER**

        Caso queira ter acesso via swagger basta acessar com a seguinte url 
    "http://localhost:8080/swagger-ui/index.html"
    Essa aplicação não conta com validação de usuário ou token por isso basta 
    clicar em "TRY OUT" e enviar sua requisição 

**Caso queira usar o postman**

```curl transação
curl --location '{{suaUrl}}/transactions' \
--header 'Content-Type: application/json' \
--data '{
    "account": "9876543210",
    "totalAmount": 100.32,
    "mcc": "5811",
    "merchant": "Padaria do Zé"
  }'

```
```curl contas
curl --location '{{suaUrl}}/account-banks' \
--header 'Content-Type: application/json' \
--data '[

    {
        "accountNumber": "9876543210",
        "ammount": "500.50",
        "mcc": ["5411","5412"],
        "mccDescription": "FOOD"
    },
    {
        "accountNumber": "9876543210",
        "ammount": "500.50",
        "mcc": ["5811","5812"],
        "mccDescription": "MEAL"
    },
    {
        "accountNumber": "9876543210",
        "ammount": "500.50",
        "mcc": ["----"],
        "mccDescription": "CASH"
    }
]'
```