CREATE DATABASE IF NOT EXISTS tradingSystem;
USE tradingSystem;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Stocks CASCADE;
DROP TABLE IF EXISTS userStock CASCADE;
DROP TABLE IF EXISTS userStocks CASCADE;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Users (
  userid INT auto_increment not null,
  fname VARCHAR(50),
  lname VARCHAR(50),
  email VARCHAR(100),
  role VARCHAR(50),
  password VARCHAR(100),
  isactive BOOLEAN default 1,
  baseCash DECIMAL(10,2) default 0,
  deposit DECIMAL(10,2) default 0,
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
  balance DECIMAL(10,2),
  FOREIGN KEY (userid) REFERENCES Users(userid) ON DELETE NO ACTION,
  FOREIGN KEY (stockid) REFERENCES Stocks(stockid) ON DELETE NO ACTION,
  quantity INT,
  PRIMARY KEY(userid,stockid)
);

CREATE TABLE userStocks(
  purchase_order INT auto_increment not null,
  userid INT not null,
  stockid INT not null,
  quantity INT not null,
  purchase_price DECIMAL(10,2), 
  FOREIGN KEY (userid) REFERENCES Users(userid) ON DELETE NO ACTION,
  FOREIGN KEY (stockid) REFERENCES Stocks(stockid) ON DELETE NO ACTION,
  PRIMARY KEY(purchase_order, userid, stockid)
);

