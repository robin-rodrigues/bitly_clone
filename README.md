# Bitly Clone

Bitly Clone is a URL shortening service that allows users to convert long URLs into shorter, more manageable links. This project comprises a Spring Boot backend, a React frontend (using Vite), and utilizes Docker for containerization. The backend is deployed on Render, the database is hosted on Neon, and the frontend is deployed on Netlify.

## Features

- **URL Shortening**: Convert long URLs into short links for easy sharing.
- **Redirection**: Short links redirect users to the original long URLs.
- **Analytics**: Track the number of times a short link has been accessed.


## Demo Video

Watch the demo video to see this project in action:
[Demo Video](https://drive.google.com/file/d/1_jw9Kd2XgzNdUSgGc-3wTclOmXhuokJ_/view?usp=share_link)

[Bitly Clone](https://mini-urls.netlify.app/)


## Architecture

The application follows a microservices architecture:

- **Backend**: Developed using Spring Boot, providing RESTful APIs for URL shortening and redirection.
- **Frontend**: Built with React (Vite), offering a user-friendly interface for interacting with the service.
- **Database**: Hosted on Neon, storing mappings between short links and original URLs.
- **Containerization**: Docker is used to containerize the backend application for consistent deployment.
- **Deployment**:
  - Backend: Deployed on Render.
  - Frontend: Deployed on Netlify.

## Prerequisites

- **Docker**: Ensure Docker is installed to build and run the backend container.
- **Node.js & npm**: Required for running the React (Vite) frontend locally.

## Getting Started

### Backend

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/robin-rodrigues/bitly_clone.git
   cd bitly_clone/backend
   ```

2. **Build the Docker Image**:
   ```bash
   docker build -t bitly_clone_backend .
   ```

3. **Run the Docker Container**:
   ```bash
   docker run -p 8080:8080 bitly_clone_backend
   ```

   The backend API will be accessible at `http://localhost:8080`.

### Frontend

1. **Navigate to the Frontend Directory**:
   ```bash
   cd ../frontend
   ```

2. **Install Dependencies**:
   ```bash
   npm install
   ```

3. **Start the Development Server**:
   ```bash
   npm run dev
   ```

   The frontend application will be accessible at `http://localhost:5173`.

## Deployment

### Backend

The backend is containerized using Docker and deployed on Render. The Docker image is available on Docker Hub:

- [bitly_clone_backend Docker Image](https://hub.docker.com/r/rodrob/bitly_clone_backend)

To deploy the backend:

1. **Pull the Docker Image**:
   ```bash
   docker pull rodrob/bitly_clone_backend:latest
   ```

2. **Run the Docker Container**:
   ```bash
   docker run -p 8080:8080 rodrob/bitly_clone_backend:latest
   ```

### Frontend

The frontend is deployed on Netlify and can be accessed at:

- [Bitly Clone Frontend](https://mini-urls.netlify.app/)

## Database

The application uses a PostgreSQL database hosted on Neon. Ensure that the database connection details are correctly configured in the backend application properties.



## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

