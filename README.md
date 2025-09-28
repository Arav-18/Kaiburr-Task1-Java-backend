# Task Management API

A backend API for managing and executing tasks as shell commands.
Built with **Spring Boot** and **MongoDB**, the API supports creating, updating, executing, and deleting tasks, while also tracking execution logs.

---

##  Features

* Create and update tasks with fields:

  * `id`
  * `name`
  * `owner`
  * `command`
* Track multiple executions per task with:

  * Start time
  * End time
  * Output logs
* RESTful API endpoints to interact with tasks
* Validation for safe shell command execution
* MongoDB used as a persistent datastore
* Spring Boot backend for easy integration and deployment

---

##  Getting Started

### Prerequisites

* Java **17** or higher
* MongoDB server installed and running
* Maven or Gradle build tool
* Git installed
* VS Code or preferred IDE

### Clone Repository

```bash
git clone https://github.com/your-username/task-management-api.git
cd task-management-api
```

### Run Application

Using Maven:

```bash
./mvnw spring-boot:run
```

Or using Gradle:

```bash
./gradlew bootRun
```

---

##  API Overview

Example endpoints (to be expanded):

* `POST /tasks` → Create a new task
* `GET /tasks/{id}` → Get task details
* `PUT /tasks/{id}` → Update task
* `DELETE /tasks/{id}` → Delete task
* `POST /tasks/{id}/execute` → Execute task and store logs

---

##  Database

* **Tasks** stored in MongoDB collection
* **Execution logs** stored as embedded documents within tasks

