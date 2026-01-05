# 🎬 I-Cinema - Movie Booking Platform

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-green)
![Angular](https://img.shields.io/badge/Angular-15.2.0-red)
![License](https://img.shields.io/badge/license-MIT-blue)

**I-Cinema** is a comprehensive full-stack movie booking application designed to provide a seamless ticket reservation experience. Built with a robust **Spring Boot** backend and a dynamic **Angular** frontend, it features real-time seat selection, movie management, and secure booking flows.

---

## 🚀 Features

| Feature                      | Description                                                |
| :--------------------------- | :--------------------------------------------------------- |
| **User Authentication**      | Secure login and registration for users.                   |
| **Movie Browsing**           | Browse currently showing movies with detailed information. |
| **Real-time Seat Selection** | Interactive seat map to choose preferred seats.            |
| **Booking Management**       | Book tickets and view booking history.                     |
| **Admin Dashboard**          | Manage movies, theaters, and showtimes.                    |
| **Responsive Design**        | Optimized for desktop and mobile devices using Bootstrap.  |

---

## 🛠️ Tech Stack

| Category     | Technology            | Description                              |
| :----------- | :-------------------- | :--------------------------------------- |
| **Backend**  | **Java 21**           | Core programming language.               |
|              | **Spring Boot 3.5.9** | Framework for microservices & REST APIs. |
|              | **Spring Data JPA**   | ORM and database interaction.            |
|              | **MySQL**             | Relational database management system.   |
|              | **Lombok**            | Boilerplate code reduction.              |
|              | **Maven**             | Build automation tool.                   |
| **Frontend** | **Angular 15.2.0**    | Component-based web framework.           |
|              | **Bootstrap 5.3.8**   | Responsive CSS framework.                |
|              | **TypeScript**        | Typed superset of JavaScript.            |

## 📂 Project Structure

```bash
I-Cinema
├── backend
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com.icinema
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   └── pom.xml
└── frontend
    ├── src
    │   ├── app
    │   ├── assets
    │   └── environments
    ├── angular.json
    └── package.json
```

---

## 🏁 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

Ensure you have the following installed:

- **Node.js** (v14.20+ recommended) & **npm**
- **Java Development Kit (JDK) 21**
- **Maven**
- **MySQL Server**

### 🔧 Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/codedevjk/i-cinema.git
cd i-cinema
```

#### 2. Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Configure the database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/icinema_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
   The backend API will start at `http://localhost:8080`.

#### 3. Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```
   Navigate to `http://localhost:4200/` in your browser.

---

## 📖 API Documentation

The backend API is documented using Swagger/OpenAPI. Once the backend is running, you can access the interactive API docs at:

`http://localhost:8080/swagger-ui/index.html`

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## 📧 Contact

Email - [jagmohan.jk11@gmail.com](mailto:jagmohan.jk11@gmail.com)

Project Link: [https://github.com/codedevjk/i-cinema](https://github.com/codedevjk/i-cinema)
