# 🧠 Quiz Application (Java Swing + MySQL)

A desktop-based Quiz Management System developed using **Java Swing**, **MySQL (XAMPP)**, and **JDBC**.  
This project is designed to manage a complete **online exam system simulation** with separate roles for **Students and Teachers**.

---

## 🚀 Features

### 👨‍🎓 Student Panel
- Student Registration & Login system  
- Attend MCQ and Short Answer exams  
- View exam results  
- Student performance tracking  
- Leaderboard system  

### 👨‍🏫 Teacher Panel
- Teacher Registration & Login system  
- Add MCQ and Short questions  
- Create and manage question papers  
- View student performance  
- Teacher leaderboard  

---

## 🛠️ Technologies Used

- Java (Swing GUI)
- MySQL Database
- JDBC (MySQL Connector)
- XAMPP Server
- IntelliJ IDEA

---

## 📁 Project Structure


Quiz/
├── src/
│ ├── Main.java
│ ├── Login.java
│ ├── Register.java
│ ├── Student.java
│ ├── Teacher.java
│ ├── Exam.java
│ ├── DBConnection.java
│ ├── MCQ.java
│ ├── Question.java
│ └── ...
├── .idea/
├── Quiz.iml
├── .gitignore
└── README.md


---

## 🗄️ Database Setup (XAMPP)

1. Install **XAMPP**
2. Start **Apache & MySQL**
3. Open phpMyAdmin
4. Create database:

quiz_db

5. Create required tables (users, questions, results, etc.)
6. Update DB credentials inside `DBConnection.java`

---

## ▶️ How to Run

1. Clone the repository:
```bash
git clone https://github.com/alisha-akter/QuizApplication.git
Open the project in IntelliJ IDEA
Add MySQL JDBC Connector (if not added)
Configure database connection

```
## 📊 Key Highlights
Role-based authentication system
Secure login & registration
Dynamic question management
Real-time exam system
Result & leaderboard tracking

---

🔮 Future Improvements
Web-based version (Spring Boot / React)
Cloud database integration
Mobile app version
