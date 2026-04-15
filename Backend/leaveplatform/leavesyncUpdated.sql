-- =========================
-- CREATE DATABASE
-- =========================
CREATE DATABASE IF NOT EXISTS leavesync;
USE leavesync;

-- =========================
-- USERS TABLE
-- =========================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role varchar(50) NOT NULL,
    department VARCHAR(100)
);

-- =========================
-- LEAVE REQUESTS TABLE
-- =========================
CREATE TABLE leave_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_id INT,
    leave_type ENUM('normal','urgent') DEFAULT 'normal',  -- NEW
    start_date DATE,
    end_date DATE,
    time_slot VARCHAR(50),
    reason TEXT,
    status ENUM('pending','approved','rejected') DEFAULT 'pending',
    remarks TEXT,
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (faculty_id) REFERENCES users(id)
);

-- =========================
-- CLASS SCHEDULE (SUBSTITUTE SYSTEM)
-- =========================
CREATE TABLE class_schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    leave_id INT,
    original_faculty INT,
    replacement_faculty INT,
    date DATE,
    subject VARCHAR(100),
    time_slot VARCHAR(50),

    FOREIGN KEY (leave_id) REFERENCES leave_requests(id),
    FOREIGN KEY (original_faculty) REFERENCES users(id),
    FOREIGN KEY (replacement_faculty) REFERENCES users(id)
);

-- =========================
-- NOTIFICATIONS TABLE
-- =========================
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    leave_id INT,
    sender_id INT,
    receiver_id INT,
    message TEXT,
    type ENUM('leave_request','approval','rejection','assignment'),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (leave_id) REFERENCES leave_requests(id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);

