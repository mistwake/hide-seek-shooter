-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 21, 2025 at 06:53 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_dpbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbenefit`
--

CREATE TABLE `tbenefit` (
  `username` varchar(255) NOT NULL,
  `skor` int(11) DEFAULT 0,
  `peluru_meleset` int(11) DEFAULT 0,
  `sisa_peluru` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbenefit`
--

INSERT INTO `tbenefit` (`username`, `skor`, `peluru_meleset`, `sisa_peluru`) VALUES
('DPBO<3', 350, 32, 32),
('Maverick', 200, 8, 20),
('Mifta', 2930, 172, 140),
('Mr. Hallorann', 250, 39, 48),
('Mr.Consistent', 700, 7000, 40),
('Mr.Precision', 1000, 3000, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbenefit`
--
ALTER TABLE `tbenefit`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
