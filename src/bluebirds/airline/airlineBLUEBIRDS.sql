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
