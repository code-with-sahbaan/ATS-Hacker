# ATS Hacker (v0.1) 🚀

An **AI-powered Applicant Tracking System (ATS)** built with **Spring AI**, **RAG (Retrieval-Augmented Generation)**, and **Angular**.  
ATS Hacker helps recruiters streamline hiring by leveraging **LLMs** and **vector search** to intelligently match candidates to jobs.

---

## 📌 Features (v0.1)
- 🔹 Resume parsing (PDF/DOCX → structured JSON).
- 🔹 Job & candidate management.
- 🔹 AI-powered semantic search for candidates.
- 🔹 RAG-based candidate-job recommendations.
- 🔹 Secure authentication & role-based access control.
- 🔹 Modern Angular frontend with PrimeNG.

---

## 🏗️ Tech Stack
**Frontend**
- Angular 19 + PrimeNG + TailwindCSS  

**Backend**
- Spring Boot 3 + Spring AI + Java 21  
- RAG pipeline with pgVector  

**Database**
- PostgreSQL + pgVector   

---

## ⚙️ Architecture
```mermaid
flowchart TD
    A[Frontend - Angular] -->|REST/GraphQL| B[Spring Boot Backend]
    B --> C[Spring AI + RAG Engine]
    C --> D[(PostgreSQL + pgVector)]
    B --> F[Email Notification Services]
