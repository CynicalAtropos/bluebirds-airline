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
  `custID` int(3) NOT NULL AUTO_INCREMENT,
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
  `resID` int(3) NOT NULL AUTO_INCREMENT,
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
SELECT c.customerName AS 'Customer Name', 
c.custID AS 'Customer ID',
r.resID AS 'Reservation ID',
r.flightCode AS 'Flight Code', 
r.seatNumber AS 'Seat Number', 
r.firstClass AS 'Seat Type',
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

DROP PROCEDURE IF EXISTS getSchedule;
CREATE PROCEDURE  getSchedule ( IN  `enteredID` INT(3) )  
SELECT flightCode, route, flightTime, flightDay
FROM flights, pilots
WHERE flights.pilotID = pilots.pilotID
AND pilots.pilotID = enteredID;

DROP PROCEDURE IF EXISTS cancelRes;
CREATE PROCEDURE  cancelRes ( IN  `enteredID` INT(3) ) 
insert into canceledreservations
(select * 
from reservations
where resID = enteredID);

drop procedure if exists deleteRes;
create procedure deleteRes(in enteredID int(3) )
delete from reservations
where resID = enteredID;

drop procedure if exists getCanceledByName;
CREATE PROCEDURE  getCanceledByName ( IN  `enteredName` VARCHAR( 32 ) ) 
SELECT customerName AS 'Customer Name', 
canceledreservations.custID AS 'Customer ID', 
resID AS 'Reservation ID',
flightCode AS 'Flight Code',
seatNumber AS 'Seat Number',
firstClass AS 'Seat Type', 
cost AS 'Cost'
FROM customers, canceledreservations
WHERE customers.custID = canceledreservations.custID
AND customers.customerName = enteredName;

drop procedure if exists getCanceledByID;
CREATE PROCEDURE  `getCanceledByID` ( IN  `enteredID` INT ) 
SELECT customerName AS 'Customer Name', 
canceledreservations.custID 'Customer ID', 
resID AS 'Reservation ID', 
flightCode AS 'Flight Code',
seatNumber AS 'Seat Number',
firstClass AS 'Seat Type',
cost AS 'Cost'
FROM customers, canceledreservations
WHERE customers.custID = canceledreservations.custID
AND resID = enteredID;

drop procedure if exists getCustomerRes;
create procedure getCustomerRes(in enteredID int)
select customers.customerName AS 'Customer Name',
customers.custID AS 'Customer ID',
resID AS 'Reservation ID',
flightCode AS 'Flight Code',
seatNumber AS 'Seat Number',
firstClass AS 'Seat Type',
cost AS 'Cost'
from reservations, customers
where reservations.custID = enteredID
AND reservations.custID = customers.custID;

DROP PROCEDURE IF EXISTS grossIncomeEach;
CREATE PROCEDURE grossIncomeEach()
SELECT f.flightCode, IFNULL(SUM(cost),0) AS GrossIncome
FROM flights AS f LEFT OUTER JOIN reservations AS r
ON f.flightCode = r.flightCode
GROUP BY f.flightCode;

DROP PROCEDURE IF EXISTS grossIncomeSpec;
CREATE PROCEDURE grossIncomeSpec(flightCodeIn Varchar(6))
SELECT f.flightCode, IFNULL(SUM(cost),0) AS GrossIncome
FROM flights AS f LEFT OUTER JOIN reservations AS r
ON f.flightCode = r.flightCode
WHERE f.flightCode = flightCodeIn
GROUP BY f.flightCode;

DROP PROCEDURE IF EXISTS findSeatAvailability;
CREATE PROCEDURE findSeatAvailability(flightCodeIn Varchar(6))
SELECT ((CASE WHEN FCA1 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN FCA2 IS NULL THEN 1 ELSE 0 END) + 
        (CASE WHEN FCB1 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN FCB2 IS NULL THEN 1 ELSE 0 END)) AS fcAvail,

       ((CASE WHEN ECA1 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN ECA2 IS NULL THEN 1 ELSE 0 END) + 
        (CASE WHEN ECA3 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN ECA4 IS NULL THEN 1 ELSE 0 END) +
        (CASE WHEN ECB1 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN ECB2 IS NULL THEN 1 ELSE 0 END) + 
        (CASE WHEN ECB3 IS NULL THEN 1 ELSE 0 END) + (CASE WHEN ECB4 IS NULL THEN 1 ELSE 0 END)) AS ecAvail
FROM seatmap
WHERE seatmap.flightCode = flightCodeIn;

DROP PROCEDURE IF EXISTS getFirstClassSeats;
CREATE PROCEDURE getFirstClassSeats(flightCodeIn Varchar(6))
SELECT CASE WHEN FCA1 IS NULL THEN 0 ELSE FCA1 END AS 'FCA1', 
       CASE WHEN FCA2 IS NULL THEN 0 ELSE FCA2 END AS 'FCA2', 
       CASE WHEN FCB1 IS NULL THEN 0 ELSE FCB1 END AS 'FCB1', 
       CASE WHEN FCB2 IS NULL THEN 0 ELSE FCB2 END AS 'FCB2'
FROM seatmap
WHERE seatmap.flightCode = flightCodeIn;

DROP PROCEDURE IF EXISTS getEconomySeats;
CREATE PROCEDURE getEconomySeats(flightCodeIn Varchar(6))
SELECT CASE WHEN ECA1 IS NULL THEN 0 ELSE ECA1 END AS 'ECA1', 
       CASE WHEN ECA2 IS NULL THEN 0 ELSE ECA2 END AS 'ECA2', 
       CASE WHEN ECA3 IS NULL THEN 0 ELSE ECA3 END AS 'ECA3',
       CASE WHEN ECA4 IS NULL THEN 0 ELSE ECA4 END AS 'ECA4', 
       CASE WHEN ECB1 IS NULL THEN 0 ELSE ECB1 END AS 'ECB1', 
       CASE WHEN ECB2 IS NULL THEN 0 ELSE ECB2 END AS 'ECB2', 
       CASE WHEN ECB3 IS NULL THEN 0 ELSE ECB3 END AS 'ECB3',
       CASE WHEN ECB4 IS NULL THEN 0 ELSE ECB4 END AS 'ECB4'  
FROM seatmap
WHERE seatmap.flightCode = flightCodeIn;
