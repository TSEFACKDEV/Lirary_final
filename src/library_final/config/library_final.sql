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























































































































































