-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2017 at 06:00 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `searchengine`
--

-- --------------------------------------------------------

--
-- Table structure for table `basecount`
--

CREATE TABLE `basecount` (
  `BaseUrl` varchar(200) NOT NULL,
  `count` int(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `basecount`
--

INSERT INTO `basecount` (`BaseUrl`, `count`) VALUES
('BARCA$EVER', 16);

-- --------------------------------------------------------

--
-- Table structure for table `crawlerqueue`
--

CREATE TABLE `crawlerqueue` (
  `UrlName` varchar(200) NOT NULL,
  `qID` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `crawlerqueue`
--

INSERT INTO `crawlerqueue` (`UrlName`, `qID`) VALUES
('YESWECAN2', 14);

-- --------------------------------------------------------

--
-- Table structure for table `crawlerset`
--

CREATE TABLE `crawlerset` (
  `URL` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `crawlerqueue`
--
ALTER TABLE `crawlerqueue`
  ADD PRIMARY KEY (`qID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `crawlerqueue`
--
ALTER TABLE `crawlerqueue`
  MODIFY `qID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
