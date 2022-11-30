# mina bonyadi

## User service Model

## Description
This project is a orders crud services including an Api.

## files
```
clone https://github.com/minaBonyadi/perseus.git
```

## tools

- [ ] Java 17
- [ ] Maven
- [ ] Spring Boot
- [ ] Redis
- [ ] Git
- [ ] Swagger-ui
- [ ] Openapi
- [ ] Jupiter
- [ ] Mockito
- [ ] Docker
- [ ] Docker Compose

## Test and Deploy

- run application main method in (com.bestseller.coffeestore.ChallengeApplication) and run docker-compose.yml to
up mysql database

***

## Features

- User services within a container based environment (Docker)
- providing documentation of my User services API endpoints
- User services are covering by integration tests

## User Model

User:
- id: long
- lastName: string
- firstName: string
- drinks: List of Email
- toppings: List of PhoneNumber

Email:
- id: int
- mail: string

PhoneNumber:
- id: int
- number: string

## Installation

- After cloning this project just run this command( docker-compose up --build ) in your intellij terminal to install and up this application

## Usage

you can use it by postman service call they are services which implemented :
-[ ] Post -> /users/create   -> input (orders dto)
-[ ] Get -> /users/{id}      -> input (orders id)
-[ ] Get -> /users/spec      -> input (first name, last name)
-[ ] Put -> /{id}/phone-number -> input (orders id, phone number dto)
-[ ] Put -> /{id}/drink -> input (drink dto)
-[ ] Delete -> /users/{id} -> input (orders dto)

all outputs are orders dto except delete service

## Roadmap
- add spring security
- add swagger UI
- unit test

## Authors and acknowledgment

This is Mina. I am a java back-end developer which have more than five years experience in this career,
and I have a bachelor degree in software engineering.
