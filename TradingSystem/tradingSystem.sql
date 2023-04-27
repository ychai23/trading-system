CREATE DATABASE IF NOT EXISTS tradingSystem;
USE tradingSystem;

DROP TABLE IF EXISTS userStock CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Stocks CASCADE;


CREATE TABLE Users (
  userid INT auto_increment not null,
  fname VARCHAR(50),
  lname VARCHAR(50),
  email VARCHAR(100),
  role VARCHAR(50),
  password VARCHAR(100),
  isactive BOOLEAN default 1,
	PRIMARY KEY(userid)
);

CREATE TABLE Stocks (
  stockid INT auto_increment not null,
  name VARCHAR(50),
  symbol VARCHAR(50),
  price DECIMAL(10,2),
  isactive BOOLEAN default 1,
  PRIMARY KEY(stockid)
);


CREATE TABLE userStock(
userid INT not null,
stockid INT not null,
buyPrice DECIMAL(10,2),
FOREIGN KEY (userid) REFERENCES Users(userid) ON DELETE NO ACTION,
FOREIGN KEY (stockid) REFERENCES Stocks(stockid) ON DELETE NO ACTION,
quantity INT,
PRIMARY KEY(userid,stockid)
);
