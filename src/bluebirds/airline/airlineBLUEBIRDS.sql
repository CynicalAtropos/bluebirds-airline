drop database if exists BlueBirdsAirline;
create database BlueBirdsAirline;
USE BlueBirdsAirline;

drop table if exists canceledreservations;
drop table if exists customers;
drop table if exists flights;
drop table if exists pilots;
drop table if exists reservations;
drop table if exists seatmap;

CREATE TABLE IF NOT EXISTS `canceledreservations` (
  `resID` int(3) NOT NULL,
  `custID` int(3) NOT NULL,
  `seatNumber` varchar(4) NOT NULL,
  `firstClass` int(1) NOT NULL,
  `flightCode` varchar(6) NOT NULL,
  `cost` int(10) NOT NULL,
  PRIMARY KEY (`resID`),
  KEY `custID` (`custID`),
  KEY `flightCode` (`flightCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `customers` (
  `custID` int(3) NOT NULL,
  `customerName` varchar(32) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`custID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `flights` (
  `flightCode` varchar(6) NOT NULL,
  `flightDay` date NOT NULL,
  `flightTime` varchar(10) NOT NULL,
  `route` varchar(50) NOT NULL,
  `pilotID` int(3) NOT NULL,
  PRIMARY KEY (`flightCode`),
  KEY `pilotID` (`pilotID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `pilots` (
  `pilotID` int(3) NOT NULL,
  `pilot` varchar(32) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`pilotID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `reservations` (
  `resID` int(3) NOT NULL,
  `custID` int(3) NOT NULL,
  `seatNumber` varchar(4) NOT NULL,
  `firstClass` int(1) NOT NULL,
  `flightCode` varchar(6) NOT NULL,
  `cost` int(10) NOT NULL,
  PRIMARY KEY (`resID`),
  KEY `custID` (`custID`),
  KEY `flightCode` (`flightCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `seatmap` (
  `flightCode` varchar(6) NOT NULL,
  `FCA1` int(3) DEFAULT NULL,
  `FCA2` int(3) DEFAULT NULL,
  `FCB1` int(3) DEFAULT NULL,
  `FCB2` int(3) DEFAULT NULL,
  `ECA1` int(3) DEFAULT NULL,
  `ECA2` int(3) DEFAULT NULL,
  `ECA3` int(3) DEFAULT NULL,
  `ECA4` int(3) DEFAULT NULL,
  `ECB1` int(3) DEFAULT NULL,
  `ECB2` int(3) DEFAULT NULL,
  `ECB3` int(3) DEFAULT NULL,
  `ECB4` int(3) DEFAULT NULL,
  KEY `flightCode` (`flightCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `canceledreservations`
  ADD CONSTRAINT `canceledreservations_ibfk_1` FOREIGN KEY (`custID`) REFERENCES `customers` (`custID`),
  ADD CONSTRAINT `canceledreservations_ibfk_2` FOREIGN KEY (`flightCode`) REFERENCES `flights` (`flightCode`);

ALTER TABLE `flights`
  ADD CONSTRAINT `flights_ibfk_1` FOREIGN KEY (`pilotID`) REFERENCES `pilots` (`pilotID`);

ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`custID`) REFERENCES `customers` (`custID`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`flightCode`) REFERENCES `flights` (`flightCode`);

ALTER TABLE `seatmap`
  ADD CONSTRAINT `seatmap_ibfk_1` FOREIGN KEY (`flightCode`) REFERENCES `flights` (`flightCode`);

DROP PROCEDURE IF EXISTS searchReservID;
CREATE PROCEDURE searchReservID(resNum INT(3))
SELECT r.resID AS 'Reservation ID', 
c.customerName AS 'Customer Name', 
r.seatNumber AS 'Seat Number', 
r.flightCode AS 'Flight Number', 
r.cost AS 'Cost'
FROM reservations AS r, customers AS c
WHERE r.resID = resNum
AND r.custID = c.custID;

DROP PROCEDURE IF EXISTS searchCustID;
CREATE PROCEDURE searchCustID(custNum INT(3))
SELECT c.custID AS 'Customer ID',
c.customerName AS 'Name',
c.address AS 'Address',
c.phone AS 'Phone'
FROM
customers AS c
WHERE c.custID = custNum;

DROP PROCEDURE IF EXISTS getSeatName;
CREATE PROCEDURE getSeatName(resNum INT(3))
SELECT c.customerName
FROM customers AS c, reservations AS r
WHERE r.resID = resNum
AND r.custID = c.custID;

DROP PROCEDURE IF EXISTS printFirstClass;
CREATE PROCEDURE printFirstClass(flightNum VARCHAR(6))
SELECT FCA1, FCA2, FCB1, FCB2
FROM seatmap AS sm
WHERE sm.flightCode = flightNum;

DROP PROCEDURE IF EXISTS printEconomyClass;
CREATE PROCEDURE printEconomyClass(flightNum VARCHAR(6))
SELECT ECA1, ECA2,ECA3,ECA4,ECB1,ECB2,ECB3,ECB4
FROM seatmap AS sm
WHERE sm.flightCode = flightNum;




