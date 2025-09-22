# Neo Hire (v0.1) 🚀

An **AI-powered Applicant Tracking System (ATS)** built with **Spring AI**, **RAG (Retrieval-Augmented Generation)**, and **Angular**.  
Neo Hire helps recruiters streamline hiring by leveraging **LLMs** and **vector search** to intelligently match candidates to jobs.

---

## 🚀 Version 01 – Core Features

1. **Unified Account (Dual Profile Switching)**
   - One account can act as **Recruiter** or **Candidate**.
   - Users can **switch profiles** via the profile page without separate logins.

2. **AI Interview (Practice Mode for Candidates)**
   - Candidates can practice with an **AI Interviewer**.
   - Realistic Q&A using **speech-to-text** and **text-to-speech**.
   - Feedback is generated to improve performance.

3. **AI-Powered Job Recommendations**
   - Candidates upload resumes (PDF/Doc).
   - NeoHire generates embeddings of resumes.
   - RAG AI recommends the **most relevant jobs**.

4. **AI-Powered Resume Recommendations**
   - Recruiters post job descriptions.
   - NeoHire generates embeddings of job descriptions.
   - RAG AI recommends **the most suitable resumes**.

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
