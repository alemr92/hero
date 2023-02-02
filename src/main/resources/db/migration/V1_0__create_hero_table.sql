CREATE TABLE hero (
  id int unsigned not null AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  gender varchar(1) NOT NULL,
  telephone varchar(9) NOT NULL,
  description varchar(255),
  category varchar(1) NOT NULL,
  PRIMARY KEY (id)
 );
 