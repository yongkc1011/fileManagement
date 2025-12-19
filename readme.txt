fileManagement
Full Stack Pre-Task – Simple File / Notes Management System

============================================================

Project Description
-------------------
This project is a full-stack web application that allows users to manage
text-based notes (title and content). It demonstrates basic CRUD
(Create, Read, Update, Delete) functionality.

The frontend is built using Vue.js, and the backend is built using
Java Spring Boot with SQLite as the database.

============================================================

Features
--------
- Create a note with title and content
- View all notes
- Update an existing note
- Delete a note with confirmation
- RESTful API backend
- SQLite file-based database
- Deployed online using Railway

============================================================

Tech Stack
----------
Frontend:
- Vue.js 3 (Vite)
- JavaScript (ES6+)
- Fetch API

Backend:
- Java Spring Boot
- Spring Data JPA
- SQLite

Deployment:
- Railway (Frontend and Backend)

============================================================

Project Structure
-----------------
fileManagement/
├─ fileManagement-backend/
│  ├─ pom.xml
│  └─ src/main/java/com/mycompany/filemanagement
│
└─ fileManagement-frontend/
   ├─ package.json
   ├─ index.html
   └─ src/

============================================================

Live Deployment
---------------
Frontend URL:
https://filemanagement-production-0a6b.up.railway.app

Backend API Base URL:
https://filemanagement-production.up.railway.app

API Test Endpoint:
GET https://filemanagement-production.up.railway.app/api/notes

============================================================

API Endpoints
-------------
GET    /api/notes           - Get all notes
POST   /api/notes           - Create a new note
PUT    /api/notes/{id}      - Update a note
DELETE /api/notes/{id}      - Delete a note

How to Set Up and Run the Application Locally
---------------------------------------------

Prerequisites:
- Java 17 or above
- Maven
- Node.js 18 or above
- npm
- NetBeans (for backend) and VS Code (for frontend)

-------------------------------------------------------------

Step 1: Download the Project
1. Go to the GitHub repository:
   https://github.com/yongkc1011/fileManagement

2. Click the green "Code" button.
3. Select "Download ZIP".
4. Extract the ZIP file to any folder on your computer.

-------------------------------------------------------------

Step 2: Run Backend (Spring Boot)
1. Open NetBeans.
2. Click "Open Project".
3. Select the folder:
   fileManagement-backend
4. Wait for Maven dependencies to finish loading.
5. Run the project (Run ▶ or F6).

Backend will run at:
http://localhost:8080

Test endpoint:
http://localhost:8080/api/notes

-------------------------------------------------------------

Step 3: Run Frontend (Vue)
1. Open the extracted folder using VS Code.
2. Open the folder:
   fileManagement-frontend
3. Open VS Code Terminal.
4. Run the following commands:

npm install
npm run dev

Frontend will run at:
http://localhost:5173

-------------------------------------------------------------

Step 4: Frontend–Backend Configuration (Local)
1. Inside fileManagement-frontend, create a file named:
   .env.local

2. Add the following line:
VITE_API_BASE_URL=http://localhost:8080

3. Save the file and restart the frontend dev server.

============================================================

Deployment Overview
-------------------
This project is deployed using Railway with two services from the same
GitHub repository.

Backend Service:
- Root directory: fileManagement-backend
- Java / Maven auto-detected
- Uses dynamic port configuration:
  server.port=${PORT:8080}

Frontend Service:
- Root directory: fileManagement-frontend
- Built using "npm run build"
- Environment variable set in Railway:
  VITE_API_BASE_URL=https://filemanagement-production.up.railway.app

============================================================

Development Process (AI and Tools Guidance)
-------------------------------------------
AI tools (ChatGPT) were used during development for idea generation,
boilerplate code, debugging, and deployment troubleshooting.

Example 1: Backend API Design
Prompt given:
"Generate a Spring Boot REST API for a notes CRUD application using SQLite."

AI output:
- Suggested layered architecture (Controller, Service, Repository)
- Provided CRUD endpoints and entity structure

How I modified or verified it:
- Adjusted entity fields to match project requirements
- Added proper error handling
- Tested endpoints using browser and frontend integration

Why changes were made:
- To ensure clean separation of concerns and compatibility with the Vue frontend

-------------------------------------------------------------

Example 2: Deployment and Port Configuration
Prompt given:
"My Spring Boot app fails on Railway because the port is fixed. How do I fix this?"

AI output:
- Suggested using ${PORT} environment variable
- Recommended configuring Railway root directory correctly

How I modified or verified it:
- Updated application.properties
- Verified deployment by accessing /api/notes on Railway

Why changes were made:
- Railway assigns ports dynamically; hardcoding the port would break deployment

-------------------------------------------------------------

Example 3: Frontend Integration and CORS Fix
Prompt given:
"Vue fetch request fails with CORS error and 'Unexpected token <'. How to fix?"

AI output:
- Explained that incorrect API base URL causes HTML responses instead of JSON
- Suggested enabling CORS and handling preflight OPTIONS requests

How I modified or verified it:
- Fixed API base URL using environment variables
- Added CORS configuration in the backend
- Confirmed no CORS errors using browser DevTools

Why changes were made:
- Browser security requires explicit CORS handling for cross-origin API calls

============================================================

Notes
-----
- SQLite is used for simplicity; data may reset on redeploy due to
  ephemeral storage on Railway.
- The focus of this task is correctness, deployment, and clear documentation.

============================================================

Author
------
Yong Kar Chun
Full Stack Pre-Task Submission
