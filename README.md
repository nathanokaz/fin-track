# 💰 Fin Track - API Financeira

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

Fin Track é uma API REST inspirada em sistemas bancários, desenvolvida com Java e Spring Boot, que simula operações financeiras entre contas, autenticação segura com JWT e gerenciamento de transações financeiras com controle de acesso por permissões.

## 🚧 Status do Projeto

Projeto em desenvolvimento.

## ✨ Funcionalidades Principais

- Autenticação e autorização com JWT e Spring Security.
- Cadastro e gerenciamento de usuários e contas bancárias.
- Operações financeiras entre contas (transferência, saque e depósito).
- Registro e consulta de transações financeiras.
- Organização de transações por categorias.
- Validação de dados utilizando Bean Validation.
- Tratamento global de exceções para respostas padronizadas da API.
- Relacionamento entre entidades utilizando Spring Data JPA.
- Criptografia de senhas com BCrypt.

## 🔐 Controle de Acesso

- User: pode gerenciar a própria conta, criar categorias e realizar transações financeiras.
- Admin (a implementar): pode gerenciar usuários, contas bancárias, categorias e visualizar transações do sistema.

## 📌 Endpoints Principais

| Método | Rota | Descrição |
|--------|------|------------|
| POST | /auth/cadastrar | Cadastra um novo usuário |
| POST | /auth/login | Realiza autenticação do usuário |
| POST | /conta/criar | Cria uma nova conta bancária |
| GET | /conta/listar | Lista as contas do usuário |
| GET | /conta/saldo/{id} | Consulta o saldo disponível da conta |
| POST | /transacao/criar | Realiza uma transação financeira |
| GET | /transacao/listar | Lista as transações realizadas |
| POST | /categoria/criar | Cria uma nova categoria |
| GET | /categoria/listar | Lista as categorias cadastradas |

> Observação: As rotas protegidas requerem autenticação via Bearer Token JWT no cabeçalho da requisição.

## 🛠️ Tecnologias Utilizadas

### Backend

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Web MVC
- Bean Validation

### Banco de Dados

- MySQL

### Segurança

- JWT (JSON Web Token)
- BCrypt

### Ferramentas

- Maven
- Lombok
