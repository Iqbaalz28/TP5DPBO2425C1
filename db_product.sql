-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2025 at 05:14 PM
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
-- Database: `db_product`
--

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` varchar(10) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `kondisi` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `nama`, `harga`, `kategori`, `kondisi`) VALUES
('P001', 'IPhone 15 Pro', 21000000, 'Smartphone', 'Baru'),
('P002', 'Samsung Galaxy S24', 18000000, 'Smartphone', 'Baru'),
('P003', 'Asus ROG Phone 7', 12000000, 'Smartphone', 'Baru'),
('P004', 'Xiaomi 14 Ultra', 15000000, 'Smartphone', 'Baru'),
('P005', 'OPPO Reno 11 Pro', 9000000, 'Smartphone', 'Bekas'),
('P006', 'Google Pixel 8', 13000000, 'Smartphone', 'Baru'),
('P007', 'Vivo X100', 11000000, 'Smartphone', 'Bekas'),
('P008', 'Xiaomi 17 Pro Max', 22000000, 'Smartphone', 'Baru');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
