# 🔗 Shortzy - Cloud-Based URL Shortener

Shortzy is a cloud-based URL shortener application built using Spring Boot and MySQL. It allows users to convert long URLs into short, easily shareable links. Fully deployed with a working frontend and backend.

## Live Links

- 🔧 Backend API: [https://your-render-backend-url.com](https://your-render-backend-url.com)  
- 🌐 Frontend App: [https://jkmalra.github.io/Shortzy](https://jkmalra.github.io/Shortzy)

## Tech Stack

- **Backend:** Spring Boot, Java, MySQL, Maven  
- **Frontend:** HTML/CSS/JS (GitHub Pages)  
- **Deployment:** Render (Backend), GitHub Pages (Frontend)  
- **Tools:** Postman, Swagger (coming soon)

## Features

- ✅ Create short links for any valid URL  
- ✅ Redirect from short link to original URL  
- ✅ Persistent storage using MySQL  
- ✅ Clean REST APIs (GET/POST)  
- ✅ Environment variable support for DB configs  
- 🔜 Swagger API docs  
- 🔜 Click tracking (planned)

## API Endpoints

| Method | Endpoint       | Description              |
| ------ | -------------- | ------------------------ |
| POST   | `/shorten`     | Create a shortened URL   |
| GET    | `/{shortCode}` | Redirect to original URL |

J.K. Malra
