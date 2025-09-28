Task Management API
This project is a backend API for managing "tasks" that can be executed as shell commands. The API allows creating, reading, updating, executing, and deleting tasks.
The data is stored in a MongoDB database, with task execution logs stored as embedded documents.

Features
Create and update tasks with fields: id, name, owner, command.

Track multiple executions per task with start time, end time, and output.

RESTful API endpoints to interact with tasks.

Validation for safe shell command execution.

MongoDB used as persistent data store.

Spring Boot based backend for easy integration and deployment.

Getting Started
Prerequisites
Java 17 or higher

MongoDB server installed and running

Maven or Gradle build tool

Git installed

VS Code or IDE of choice
