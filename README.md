# Mina Bonyadi

## Coffee-Store Services

## Description
This project is a coffee-store, and it's based on Api for both side admin and customer.

## files
```
https://github.com/minaBonyadi/coffee-store
```

## tools

- [ ] Java 17
- [ ] Maven
- [ ] Spring Boot
- [ ] Mysql
- [ ] Git
- [ ] Docker
- [ ] Docker Compose

## Test and Deploy

- run application main method in (com.bestseller.coffeestore.CoffeeStoreApplication) and run docker-compose.yml to
up mysql database

***

## Features

- Order services within a container based environment (Docker)
- providing documentation of Order services API endpoints
- Order services are covering by integration tests

## Coffee_Store Model

Drink:
- id: long
- name: string
- price: double

Topping:
- id: long
- name: string
- price: double

Orders:
- id: long
- drink: drink model
- toppings: topping list

## Installation

- After cloning this project just run this command( docker-compose up --build ) in your intellij terminal to install and up this application

## Usage

you can use it by postman service call they are services which implemented :
- [ ] Post -> /orders/register   -> input (RegisterOrdersRequest)
- [ ] Post -> /admin/menu     -> input (MenuItemRequest)
- [ ] Put -> /admin/menu     -> input (MenuItemRequest)
- [ ] Delete -> /admin/menu -> input (MenuItemRequest)
- [ ] Post -> /admin/menu/order-report -> without input

## Roadmap
- add User Model
- unit test

## Authors and acknowledgment

This is Mina. I am a java back-end developer which have more than six years experience in this career,
and I have a bachelor degree in software engineering.
