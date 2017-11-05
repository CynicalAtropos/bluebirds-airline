DROP PROCEDURE IF EXISTS searchReservID;
CREATE PROCEDURE searchReservID(resNum INT(3))
SELECT r.ResID, c.Name, r.SeatNum, r.FlightCode, r.Cost
FROM Reservation AS r, Customer AS c
WHERE r.ResID = resNum
AND r.CustID = c.CustID;

DROP PROCEDURE IF EXISTS searchCustID;
CREATE PROCEDURE searchCustID(custNum INT(3))
SELECT * FROM
FROM Customer AS c
WHERE c.CustID = custNum;

DROP PROCEDURE IF EXISTS printFirstClass;
CREATE PROCEDURE printFirstClass(flightCode VARCHAR(6))
SELECT c.Name
FROM Customer AS c, Flight AS f, Reservation AS r
WHERE r.FlightCode = flightCode
AND r.CustID = c.CustID
AND r.FirstClass = 1
ORDER BY r.SeatNum;

DROP PROCEDURE IF EXISTS printEconomyClass;
CREATE PROCEDURE printEconomyClass(flightCode VARCHAR(6))
SELECT c.Name
FROM Customer AS c, Flight AS f, Reservation AS r
WHERE r.FlightCode = flightCode
AND r.CustID = c.CustID
AND r.FirstClass = 0
ORDER BY r.SeatNum;


