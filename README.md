# Memstagram: Instagram for Memes
An Instagram-like app where users can post memes, follow other users, and view memes in their feed. No likes or comments, just follows. This project implements a backend API using Spring Boot 3.4.1 and Java 17.

Features
- User registration and login.
- Profile management (username, bio, profile picture).
- Upload and manage memes (title, description, image URL).
- Follow and unfollow other users.
- View memes from users you follow in the feed.
- Soft delete functionality with "deleted by user" and "deleted by admin" flags.

Technologies Used
- Backend: Spring Boot 3.4.1 (Java 17)
- Database: MySQL (Dockerized)
- Authentication: JWT (JSON Web Token)
- ORM: Spring Data JPA
- Security: Spring Security
- Dependency Management: Maven

Database Setup
The project uses a MySQL database running in Docker. Here is the basic setup for the database:
```
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-for-jee-project
    environment:
      MYSQL_ROOT_PASSWORD: qwe123
      MYSQL_DATABASE: project
      MYSQL_PASSWORD: password
    ports:
      - "3306:3306"
    volumes:
      - ./database_data:./var
    networks:
      - mysql-network

networks:
  mysql-network:
    driver: bridge

```

Database schema:
The database schema includes three main tables: `users`, `memes`, and `follows`. Memes and users can be soft-deleted with flags: `deleted_by_user` and `deleted_by_admin`.

```
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(48) NOT NULL UNIQUE, -- Max length 48
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255),
    bio VARCHAR(128), -- Max length 128
    deleted_by_user BOOLEAN DEFAULT FALSE, -- Flag for user deletion
    deleted_by_admin BOOLEAN DEFAULT FALSE, -- Flag for admin deletion
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE memes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255) NOT NULL,
    deleted_by_user BOOLEAN DEFAULT FALSE, -- Flag for user deletion
    deleted_by_admin BOOLEAN DEFAULT FALSE, -- Flag for admin deletion
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE follows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    follower_id INT NOT NULL,
    followed_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (follower_id, followed_id)
);

```

## API Endpoints

User Endpoints:
- POST /users/register: Register a new user.
- POST /users/login: Log in and get a JWT token.
- GET /users/{id}: Get user profile and posted memes.

Meme Endpoints:
- POST /memes/upload: Upload a new meme.
- GET /memes/{id}: Get meme by ID.
- GET /memes/feed: Get memes from followed users.

Follow Endpoints:
- POST /users/{id}/follow: Follow a user.
- DELETE /users/{id}/unfollow: Unfollow a user.
- GET /users/{id}/followers: Get list of followers.
- GET /users/{id}/following: Get list of followed users.
