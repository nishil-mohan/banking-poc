
CREATE TABLE tbl_user (
                          id integer primary key AUTO_INCREMENT,
                          username varchar(25) NOT NULL,
                          password varchar(75) NOT NULL
);

INSERT INTO tbl_user (username, password) VALUES ( 'client1', '$2a$10$d456zexuyNn5ULoLS9Y7weQ5x1ZXcM38NKOuzdaRuFt1tZoXSNnsS');
INSERT INTO tbl_user (username, password) VALUES ('alpha', '$2a$10$uRPGXTwtlqIiPK5j8Du11Od/9vpICBjsOHvCqeTwZrbrCDisxAsqC');
INSERT INTO tbl_user ( username, password) VALUES ( 'beta', '$2a$10$uRPGXTwtlqIiPK5j8Du11Od/9vpICBjsOHvCqeTwZrbrCDisxAsqC');
