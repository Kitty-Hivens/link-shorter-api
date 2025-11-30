# ✂️ LinkShorter API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-10.x-003545?style=for-the-badge&logo=mariadb&logoColor=white)

Scalable and collision-free URL shortening service built with **Spring Boot 3** and **MariaDB**.
The service uses a **Base62 encoding algorithm** based on database IDs to guarantee mathematically unique short codes without the overhead of collision checks.

---

## 🚀 Key Features

* **Collision-Free Generation:** Uses Database ID to Base62 conversion strategy ($O(1)$ complexity).
* **Containerized Environment:** Fully dockerized setup for Application and Database.
* **Data Persistence:** Uses MariaDB (InnoDB engine) for reliable storage.
* **Validation:** Strict input validation using Jakarta Validation API.
* **Modern Stack:** Java 21 Records, Gradle Kotlin DSL, Docker Compose V2.

---

## 🛠 Technology Stack

* **Core:** Java 21, Spring Boot 3.x
* **Database:** MariaDB (Dockerized)
* **Build Tool:** Gradle (Kotlin DSL)
* **Containerization:** Docker & Docker Compose
* **Utilities:** Lombok, Jakarta Validation
* **Architecture:** RESTful API, N-Tier Architecture

---

## 🐳 Quick Start (Docker)

You don't need Java or MariaDB installed locally. Just **Docker**.

### 1. Clone the repository
```bash
git clone https://github.com/Kitty-Hivens/link-shorter-api.git
cd link-shorter-api
````

### 2\. Configure & Run

The project includes a ready-to-use `docker-compose.yaml`.
It sets up the application on port **8080** and MariaDB on external port **3307** (to avoid conflicts with local MySQL/MariaDB instances).

```bash
docker-compose up --build
```

*Note: The first launch might take a minute to download Gradle and compile the project.*

### 3\. Verify

Access the application:

* **API:** `http://localhost:8080`
* **Database (External access):** `localhost:3307` (User: `haru`, Password: `password`)

-----

## 🔌 API Documentation

### 1\. Shorten a URL

Create a new short link from a long URL.

* **Endpoint:** `POST /api/v1/shorten`
* **Content-Type:** `application/json`

**Request:**

```json
{
  "originalUrl": "https://www.google.com/search?q=spring+boot+docker"
}
```

**Response (200 OK):**

```json
{
  "shortUrl": "http://localhost:8080/1bX",
  "originalUrl": "https://www.google.com/search?q=spring+boot+docker"
}
```

**Error (400 Bad Request):**
If the URL is invalid or empty:

```json
{
  "originalUrl": "Invalid URL format"
}
```

### 2\. Redirect

Navigate to the original URL using the short code.

* **Endpoint:** `GET /{shortCode}`
* **Response:** `302 Found` (Redirects to original URL)

-----

## ⚙️ Configuration (Environment Variables)

The application is configured via `docker-compose.yaml`.

| Variable                     | Description             | Default (Docker)                           |
|:-----------------------------|:------------------------|:-------------------------------------------|
| `SPRING_DATASOURCE_URL`      | Database connection URL | `jdbc:mariadb://mariadb:3306/shortener_db` |
| `SPRING_DATASOURCE_USERNAME` | Database User           | `haru`                                     |
| `SPRING_DATASOURCE_PASSWORD` | Database Password       | `password`                                 |
| `MYSQL_ROOT_PASSWORD`        | Root DB Password        | `secret`                                   |

-----

## 🏗 Architecture Logic

Instead of generating random strings and checking the database for duplicates (which is slow at scale), this service uses **Base62 Encoding**.

1.  **Save:** The long URL is saved to the database. The DB assigns an auto-incrementing ID (e.g., `100005`).
2.  **Encode:** The ID is converted to Base62 (e.g., `100005` -\> `q83`).
3.  **Update:** The record is updated with this unique `shortCode`.

This guarantees **uniqueness** and simplifies lookup logic.

-----

## 🔧 Local Development (Optional)

If you want to run the app **without Docker** (e.g., via IntelliJ IDEA), you must have a local MariaDB instance running.

1.  Ensure MariaDB is running on `localhost:3306`.
2.  Create database `shortener_db`.
3.  Update `src/main/resources/application.properties` with your local credentials.
4.  Run via Gradle:
    ```bash
    ./gradlew bootRun
    ```

-----

## 📝 License

This project is licensed under the MIT License.
