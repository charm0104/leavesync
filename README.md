LeaveSync – Online Platform for Leave Approval and Intimation to Stakeholders

--> Project Overview

LeaveSync is a role-based web platform designed to automate the faculty leave management process in educational institutions. The system allows faculty members to apply for leave digitally, enables the approving authority to review and approve or reject leave requests, and ensures that relevant stakeholders are informed so that academic scheduling can be managed effectively.

--> Problem Statement

In our institution, the process of managing faculty leave is still handled manually through informal communication, emails, or paperwork. This manual approach often leads to delays in approval, lack of transparency in leave status, and difficulty in maintaining proper records of faculty leave history. Additionally, when a faculty member goes on leave, there is often no structured system to inform the concerned stakeholders, such as the academic coordinator or administration, which makes it difficult to arrange substitute classes and manage the academic schedule efficiently.

Due to the absence of a centralized digital system, authorities find it difficult to monitor leave patterns, analyze leave records, and ensure smooth academic operations. Therefore, there is a need for an automated and role-based system that enables faculty members to apply for leave online, allows the approving authority to review and approve or reject leave requests, and ensures timely intimation to relevant stakeholders such as the coordinator or administrative office for proper scheduling and management of classes.

The proposed system aims to streamline the faculty leave approval process, improve transparency, maintain accurate records, and ensure effective communication among stakeholders through an online leave approval and stakeholder intimation platform.

--> Objectives

The main objectives of the Online Platform for Leave Approval and Intimation to Stakeholders are:

1. To develop a role-based system that allows faculty members to apply for leave through an online platform.

2. To enable the approving authority (HOD/Manager) to review, approve, or reject leave requests and provide remarks when necessary.

3. To maintain a centralized database of leave records for tracking faculty leave history and monitoring leave patterns.

4. To provide real-time updates of leave status to faculty members regarding approval or rejection of their leave applications.

5. To ensure automatic intimation to stakeholders, such as the coordinator or administrative office, so that substitute classes can be arranged and academic schedules can be managed effectively.

6. To allow authorities to analyze leave data and generate reports for better decision-making and management of faculty availability.

7. To improve transparency, efficiency, and communication in the leave management process within educational institutions.

--> System Architecture

The system follows a three-tier architecture:

1. Frontend 
   
Provides role-based dashboards for different users.

- Faculty Dashboard
- Authority Dashboard
- Admin/Coordinator Dashboard

2. Backend

Developed using Spring Boot, handling:

- Business logic
- Authentication & authorization
- Leave approval workflow
- Stakeholder notifications

3. Database

A MySQL database stores:

- User information
- Leave requests
- Class scheduling details

--> Key Features

- Role-based authentication and authorization
- Online leave application system
- Leave approval and rejection workflow
- Leave status tracking
- Faculty leave history management
- Stakeholder notification system
- Leave analytics dashboard
- Centralized database for records

--> Stakeholders and Roles

1. Faculty
- Apply for leave
- View leave status
- View leave history
- Access personal dashboard

2. Authority (HOD/Manager)
   
- Review leave requests
- Approve or reject requests
- Add remarks
- View faculty leave history
- Analyze leave statistics

4. Admin/Coordinator
- Receive notification when leave is approved
- Manage substitute class scheduling
- Monitor system records

5. System
- Maintains records
- Sends notifications
- Updates leave status automatically

--> Workflow

1. Faculty logs into the system.
2. Faculty submits a leave application.
3. Leave request is stored with Pending status.
4. Authority reviews the request.
5. Authority approves or rejects the request with remarks.
6. System updates the leave status.
7. Faculty receives the updated status.
8. Admin/Coordinator receives intimation to manage substitute classes.

--> Tech Stack

Frontend

- HTML
- CSS
- React.js

Backend

- Spring Boot
- Spring Security
- JWT Authentication

Database

- MySQL

Version Control

- Git
- Github



