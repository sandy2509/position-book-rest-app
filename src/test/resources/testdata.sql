----Author: Sandeep Das
--A sample will be automatically populated into in-memory once the services are up and running.
DROP TABLE IF EXISTS TradeOrder;

CREATE TABLE TradeOrder (TradeId LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
AccountId VARCHAR(30),
SecurityId VARCHAR(30),
Quantity DECIMAL(19,4),
TradeType VARCHAR(30)
);

INSERT INTO TradeOrder (TradeId,AccountId,SecurityId,Quantity,TradeType) VALUES (1,'ACC1','SEC1',100,'buy');

commit;