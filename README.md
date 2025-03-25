# Dishu - Recipe Sharing Application

Dishu is an innovative application that lets users create, search, and share cooking recipes. It combines a robust backend built with Java Spring Boot, a MySQL database for data persistence, Elasticsearch for powerful fuzzy search capabilities, and a modern Angular frontend for a seamless user experience.

---

## Features

- **User Management:** Secure user registration and authentication using JWT.
- **Recipe Management:** Create, update, delete, and view recipes.
- **Fuzzy Search:** Advanced fuzzy search functionality powered by Elasticsearch.
- **Responsive UI:** A sleek, user-friendly interface built with Angular.
- **RESTful API:** Clean and well-documented API endpoints for integration.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot, Spring Security, JPA/Hibernate
- **Database:** MySQL 8
- **Search Engine:** Elasticsearch
- **Frontend:** Angular
- **Containerization:** Docker & Docker Compose
- **Documentation:** Swagger for API documentation

---

## Architecture

- **Backend Service:** Handles business logic, user authentication, and recipe CRUD operations.
- **MySQL Database:** Stores user and recipe data.
- **Elasticsearch:** Provides fuzzy search capabilities for recipes.
- **Frontend Application:** Offers a responsive UI to interact with the backend API.
- **Docker Compose:** Orchestrates all services for local development and testing.

---

## Installation

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) installed on your machine.

### Steps

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/dishu.git
    ```
2. **Configure Environment Variables:**
   - **Backend environment variables are located in the backend/.env file.**
   - **Update MySQL credentials and other settings as needed.**

3. **Start the services**
   Use Docker Compose to start all services:
    ```bash
    docker-compose up -d
    ```
    This command will start the backend, frontend, MySQL, Elasticsearch, and Kibana services.
4. **Access the Applications:**
   - **Backend API: http://localhost:8080**
   - **Frontend: http://localhost:4200**
   - **Kibana (Elasticsearch UI): http://localhost:5601**
   - **phpMyAdmin (MySQL UI): http://localhost:8081**
  
