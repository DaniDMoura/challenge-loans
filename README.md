# challenge-loans
Um sistema desenvolvido em Spring Boot para avaliação e concessão de empréstimos baseado no perfil do cliente.

## Descrição

O **Desafio Loans** é uma API REST que avalia clientes e determina quais tipos de empréstimos eles podem obter baseado em critérios específicos como idade, localização, renda e CPF. O sistema oferece três tipos de empréstimos: pessoal, consignado e com garantia.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.0.3**
- **Spring Web**
- **Spring Validation**
- **Maven**

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: `LoanController` - Gerencia as requisições HTTP
- **Service**: `LoanService` - Contém a lógica de negócio para avaliação de empréstimos
- **DTO**: Objetos de transferência de dados
- **Model**: `LoanType` - Enum com os tipos de empréstimos
- **Exception Handler**: Tratamento global de exceções usando RFC 7807 (`application/problem+json`).

## Tipos de Empréstimos

| Tipo | Taxa de Juros | Descrição |
|------|---------------|-----------|
| **PERSONAL** | 4% | Empréstimo pessoal |
| **CONSIGNED** | 2% | Empréstimo consignado |
| **GUARANTEED** | 3% | Empréstimo com garantia |

## Regras de Negócio

### Empréstimo Pessoal e com Garantia
- **Renda ≤ R$ 3.000,00**: Cliente pode obter ambos os empréstimos
- **Renda entre R$ 3.000,00 e R$ 5.000,00**:
    - Idade < 30 anos
    - Localização = SP
    - Cliente pode obter ambos os empréstimos

### Empréstimo Consignado
- **Renda ≥ R$ 5.000,00**: Cliente pode obter empréstimo consignado

## Endpoints

### POST `/customer-loans`

Avalia um cliente e retorna os empréstimos disponíveis.

**Request Body:**
```json
{
  "age": 25,
  "cpf": "12345678901",
  "name": "João Silva",
  "income": 4000.0,
  "location": "SP"
}
```

**Response (Sucesso - 200):**
```json
{
  "customer": "João Silva",
  "loanList": [
    {
      "type": "PERSONAL",
      "instestRate": 4
    },
    {
      "type": "GUARANTEED", 
      "instestRate": 3
    }
  ]
}
```

**Response (Erro de Validação - 400):**
```json
{
  "message": "Validation error",
  "dateTime": "15/12/2024 -- 14:30:25",
  "details": [
    "age : deve ser maior ou igual a 18",
    "income : deve ser maior que 0"
  ]
}
```

## Validações

### CustomerRequestDto
- **age**: Obrigatório (`@NotNull`) e deve ser maior ou igual a 18 (`@Min(18)`).
- **cpf**: Obrigatório (`@NotBlank`) e deve seguir o formato válido de CPF (`@Pattern("\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}")`).
- **name**: Obrigatório (`@NotBlank`).
- **income**: Obrigatório (`@NotNull`) e deve ser maior ou igual a 0.01 (`@DecimalMin("0.01")`).
- **location**: Obrigatório (`@NotBlank`).

## Testando a API

### Exemplo com cURL

```bash
curl -X POST http://localhost:8080/customer-loans \
  -H "Content-Type: application/json" \
  -d '{
    "age": 25,
    "cpf": "123.456.789-01", 
    "name": "João Silva",
    "income": 4000.0,
    "location": "SP"
  }'
```

### Exemplos de Cenários

#### Cenário 1: Renda baixa (≤ R$ 3.000)
```json
{
  "age": 22,
  "cpf": "111.111.111-11",
  "name": "Maria Santos",
  "income": 2500.0,
  "location": "RJ"
}
```
**Resultado**: PERSONAL e GUARANTEED

#### Cenário 2: Renda alta (≥ R$ 5.000)
```json
{
  "age": 35,
  "cpf": "222.222.222-22", 
  "name": "Carlos Oliveira",
  "income": 6000.0,
  "location": "MG"
}
```
**Resultado**: CONSIGNED

#### Cenário 3: Renda média + critérios especiais
```json
{
  "age": 28,
  "cpf": "333.333.333-33",
  "name": "Ana Costa", 
  "income": 4000.0,
  "location": "SP"
}
```
**Resultado**: PERSONAL e GUARANTEED

## Testes

Os testes foram implementados utilizando:

- **Spring Boot Test**
- **JUnit 5**

A aplicação é inicializada com `@SpringBootTest` para validar o comportamento real da regra de negócio.

### Cenários Cobertos

- **Renda ≤ 3.000** → PERSONAL e GUARANTEED
- **Renda ≥ 5.000** → CONSIGNED
- **Renda entre 3.000 e 5.000 + critérios válidos (idade < 30 e SP)** → PERSONAL e GUARANTEED

### Execução

```bash
mvn test
```

Os testes garantem que as principais regras de concessão permaneçam consistentes conforme os critérios definidos.

## Desafio Original

Este projeto foi desenvolvido com base no desafio oficial disponível em:

https://github.com/backend-br/desafios/blob/master/loans/PROBLEM.md