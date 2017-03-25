-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2017 at 03:38 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `crawlerqueue`
--

CREATE TABLE `crawlerqueue` (
  `UrlName` varchar(200) NOT NULL,
  `qID` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `crawlerset`
--

CREATE TABLE `crawlerset` (
  `URL` varchar(400) CHARACTER SET utf8 NOT NULL,
  `setID` int(11) NOT NULL,
  `CrawlerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `indexerurl`
--

CREATE TABLE `indexerurl` (
  `url_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `word_cnt`
--

CREATE TABLE `word_cnt` (
  `url_id` int(11) NOT NULL,
  `title` tinyint(1) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `word` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 MAX_ROWS=4294967295;

-- --------------------------------------------------------

--
-- Table structure for table `word_cnt_perpage`
--

CREATE TABLE `word_cnt_perpage` (
  `url_id` int(32) NOT NULL,
  `cnt` int(32) DEFAULT NULL
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
-- Indexes for table `crawlerset`
--
ALTER TABLE `crawlerset`
  ADD UNIQUE KEY `setID` (`setID`);

--
-- Indexes for table `indexerurl`
--
ALTER TABLE `indexerurl`
  ADD PRIMARY KEY (`url_id`);

--
-- Indexes for table `word_cnt`
--
ALTER TABLE `word_cnt`
  ADD PRIMARY KEY (`word`,`url_id`,`position`);

--
-- Indexes for table `word_cnt_perpage`
--
ALTER TABLE `word_cnt_perpage`
  ADD PRIMARY KEY (`url_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `crawlerqueue`
--
ALTER TABLE `crawlerqueue`
  MODIFY `qID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=226;
--
-- AUTO_INCREMENT for table `crawlerset`
--
ALTER TABLE `crawlerset`
  MODIFY `setID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5001;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
