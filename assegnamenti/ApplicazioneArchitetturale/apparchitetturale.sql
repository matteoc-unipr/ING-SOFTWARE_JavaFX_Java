-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 22, 2023 alle 11:41
-- Versione del server: 10.4.27-MariaDB
-- Versione PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apparchitetturale`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `all_appointments`
--

CREATE TABLE `all_appointments` (
  `DocName` varchar(20) NOT NULL,
  `DateTime` datetime NOT NULL,
  `Place` varchar(20) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `AppId` int(4) NOT NULL,
  `PatientId` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `all_appointments`
--

INSERT INTO `all_appointments` (`DocName`, `DateTime`, `Place`, `Type`, `AppId`, `PatientId`) VALUES
('dott.Ricci', '2024-02-07 11:00:00', 'Parma', 'Psychiatric', 1566, NULL),
('dott.Rossi', '2024-01-16 10:00:00', 'Parma', 'Oculistic', 4853, NULL),
('dott.Rossi', '2024-02-11 09:00:00', 'Parma', 'Oculistic', 5326, NULL),
('dott.Esposito', '2024-02-20 11:00:00', 'Parma', 'Neurologic', 8946, NULL),
('dott.Ricci', '2024-01-20 14:00:00', 'Parma', 'Psychiatric', 9615, NULL),
('dott.Esposito', '2024-01-07 08:00:00', 'Parma', 'Neurologic', 9812, NULL),
('dott.Rossi', '2024-03-07 08:00:00', 'Parma', 'Oculistic', 9861, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `patients`
--

CREATE TABLE `patients` (
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `PatientID` int(4) NOT NULL,
  `Password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `patients`
--

INSERT INTO `patients` (`FirstName`, `LastName`, `PatientID`, `Password`) VALUES
('admin', 'admin', 1234, 'admin');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `all_appointments`
--
ALTER TABLE `all_appointments`
  ADD PRIMARY KEY (`AppId`),
  ADD KEY `PatientId` (`PatientId`);

--
-- Indici per le tabelle `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`PatientID`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `all_appointments`
--
ALTER TABLE `all_appointments`
  ADD CONSTRAINT `all_appointments_ibfk_1` FOREIGN KEY (`PatientId`) REFERENCES `patients` (`PatientID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
