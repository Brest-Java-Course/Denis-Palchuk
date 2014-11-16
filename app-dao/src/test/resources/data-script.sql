INSERT INTO USER (userId,userLogin,userName,userAge) VALUES (1,'userLogin1','userName1',1);
INSERT INTO USER (userId,userLogin,userName,userAge) VALUES (2,'userLogin2','userName2',2);
INSERT INTO USER (userId,userLogin,userName,userAge) VALUES (3,'userLogin3','userName3',3);
INSERT INTO MESSAGE (messageId,messageFromId,messageToId,messageText,messageDateTime) VALUES (1,1,2,'Hello', TIMESTAMP ( '2014-11-23', '21:32:40' ));
INSERT INTO MESSAGE (messageId,messageFromId,messageToId,messageText,messageDateTime) VALUES (2,2,1,'Hi', TIMESTAMP ( '2014-11-23', '21:32:53' ));
INSERT INTO MESSAGE (messageId,messageFromId,messageToId,messageText,messageDateTime) VALUES (3,3,1,'HiMen', TIMESTAMP ( '2014-11-23', '21:42:53' ));