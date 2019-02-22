CREATE TABLE `kevin`.`user` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Birthday` DATETIME NULL,
  PRIMARY KEY (`ID`));

CREATE TABLE `kevin`.`ticket` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `DateTime` DATETIME NOT NULL,
  `Seat` INT NOT NULL,
  `Price` DOUBLE NOT NULL,
  `EventId` INT NOT NULL,
  `UserId` INT NULL,
  `IsLuckyTicket` BIT NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `kevin`.`event` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `BasePrice` DOUBLE NOT NULL,
  `Raiting` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`));


CREATE TABLE `kevin`.`auditorium` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `NumberOfSeats` INT NOT NULL,
  `VipSeats` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
 CREATE TABLE `kevin`.`airdate` (
  `airdateID` INT NOT NULL AUTO_INCREMENT,
  `EventId` INT NOT NULL,
  `Date` DATETIME NOT NULL,
  `EventId` INT NOT NULL,
  PRIMARY KEY (`ID`));



INSERT INTO auditorium (Name, NumberOfSeats, VipSeats)
VALUES ('auditorium1', 40, '4,5,6'),
VALUES ('auditorium2', 40, '14,15,16'),
VALUES ('auditorium3', 40, '24,25,26'),
VALUES ('auditorium4', 40, '34,35,36');