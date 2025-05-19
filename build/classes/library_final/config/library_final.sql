/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  fredi
 * Created: 24 mars 2025
 */

CREATE DATABASE IF NOT EXISTS library
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

USE library;

-- Table principale pour toutes les personnes
CREATE TABLE IF NOT EXISTS Person (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    nic VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(100)
) ENGINE=InnoDB;

-- Table pour les utilisateurs (hérite de Person)
CREATE TABLE IF NOT EXISTS `User` (
    id INT UNSIGNED PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('Librarian', 'Administrator') NOT NULL,
    status ENUM('Active', 'Inactive') DEFAULT 'Active',
    reset_code VARCHAR(255),
    FOREIGN KEY (id) REFERENCES Person(id)
) ENGINE=InnoDB;

-- Table pour les emprunteurs (hérite de Person)
CREATE TABLE IF NOT EXISTS Borrower (
    id INT UNSIGNED PRIMARY KEY,
    max_loan INT DEFAULT 3,
    FOREIGN KEY (id) REFERENCES Person(id)
) ENGINE=InnoDB;

-- Table des livres avec soft delete
CREATE TABLE IF NOT EXISTS Book (
    id_book INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) UNIQUE NOT NULL,
    year_publication INT,
    image VARCHAR(255),
    description TEXT,
    position VARCHAR(255),
    status ENUM('Active', 'Inactive') DEFAULT 'Active'
) ENGINE=InnoDB;

-- Table des exemplaires d'un livre
CREATE TABLE IF NOT EXISTS Copy (
    id_copy INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    book_id INT UNSIGNED NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    status ENUM('Available', 'Loaned', 'Lost', 'Inactive') NOT NULL DEFAULT 'Available',
    FOREIGN KEY (book_id) REFERENCES Book(id_book)
) ENGINE=InnoDB;

-- Table des emprunts
CREATE TABLE IF NOT EXISTS Loan (
    id_loan INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    borrower_id INT UNSIGNED NOT NULL,
    copy_id INT UNSIGNED NOT NULL,
    librarian_id INT UNSIGNED NOT NULL,
    date_loan DATE NOT NULL,
    exp_return_date DATE NOT NULL,
    act_return_date DATE DEFAULT NULL,
    FOREIGN KEY (borrower_id) REFERENCES Borrower(id),
    FOREIGN KEY (copy_id) REFERENCES Copy(id_copy),
    FOREIGN KEY (librarian_id) REFERENCES `User`(id)
) ENGINE=InnoDB;

-- Insertion de personnes
INSERT INTO Person (name, nic, phone, email) VALUES
('Tsefack klein', 'NIC12345', '0600000001', 'klein@example.com'),
('ngansop fredi', 'NIC12346', '0600000002', 'fredi@example.com');

-- Insertion d’un utilisateur (librarian/admin)
INSERT INTO `User` (id, password, role, status) VALUES
(1, '23721', 'Librarian', 'Active');

-- Insertion d’un emprunteur
INSERT INTO Borrower (id, max_loan) VALUES
(2, 3);

-- Insertion d’un livre
INSERT INTO Book (title, author, isbn, year_publication, image, description, position, status) VALUES
(' Petit Joe enfant des rues ', 'eveline poundi', '9782070612758', 1943, NULL, 'histoire de vie', 'A1', 'Active');

-- Insertion d’un exemplaire du livre
INSERT INTO Copy (book_id, code, status) VALUES
(1, 'EX001', 'Available');

-- Insertion d’un emprunt
INSERT INTO Loan (borrower_id, copy_id, librarian_id, date_loan, exp_return_date, act_return_date) VALUES
(2, 1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL);























































































































































