DROP TABLE IF EXISTS VIGILANT;
CREATE TABLE VIGILANT (
ID_INTERNAL int(11) NOT NULL auto_increment,
KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
KEY_PERSON int(11) NOT NULL,
KEY_EXECUTION_YEAR int(11) NOT NULL,
KEY_INCOMPATIBLE_PERSON int(11) DEFAULT NULL,
PRIMARY KEY(`ID_INTERNAL`)
) TYPE=InnoDB;

DROP TABLE IF EXISTS UNAVAILABLE_PERIOD;
CREATE TABLE UNAVAILABLE_PERIOD (
ID_INTERNAL int(11) NOT NULL auto_increment,
BEGIN_DATE timestamp NOT NULL,
END_DATE timestamp NOT NULL,
JUSTIFICATION varchar(255) NOT NULL,
KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
KEY_VIGILANT int(11) NOT NULL,
PRIMARY KEY(`ID_INTERNAL`)
) TYPE=InnoDB;


DROP TABLE IF EXISTS CONVOKE;
CREATE TABLE CONVOKE (
ID_INTERNAL int(11) NOT NULL auto_increment,
ATTENDED_TO_CONVOKE tinyint(1) NOT NULL DEFAULT 0,
CONFIRMED tinyint(1) NOT NULL DEFAULT 0,
ACTIVE tinyint(1) NOT NULL DEFAULT 1,
KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
KEY_VIGILANT int(11) NOT NULL,
KEY_WRITTEN_EVALUATION int(11) NOT NULL,
PRIMARY KEY(`ID_INTERNAL`)
) TYPE=InnoDB;



DROP TABLE IF EXISTS VIGILANT_GROUP;
CREATE TABLE VIGILANT_GROUP (
ID_INTERNAL int(11) NOT NULL auto_increment,
NAME varchar(255) NOT NULL,
CONVOKE_STRATEGY varchar(255) NOT NULL,
BEGIN_OF_FIRST_PERIOD_FOR_UNAVAILABLE_PERIODS timestamp,
END_OF_FIRST_PERIOD_FOR_UNAVAILABLE_PERIODS timestamp,
BEGIN_OF_SECOND_PERIOD_FOR_UNAVAILABLE_PERIODS timestamp,
END_OF_SECOND_PERIOD_FOR_UNAVAILABLE_PERIODS timestamp,
KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
KEY_UNIT int(11) NOT NULL,
KEY_EXECUTION_YEAR int(11) NOT NULL,
PRIMARY KEY(`ID_INTERNAL`)
) TYPE=InnoDB;



DROP TABLE IF EXISTS EXAM_COORDINATOR;
CREATE TABLE EXAM_COORDINATOR (
ID_INTERNAL int(11) NOT NULL auto_increment,
ALLOWED_TO_CREATE_GROUPS tinyint(1) NOT NULL default '0',
KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
KEY_UNIT int(11) NOT NULL,
KEY_PERSON int(11) NOT NULL,
KEY_EXECUTION_YEAR int(11) NOT NULL,
PRIMARY KEY(`ID_INTERNAL`)
) TYPE=InnoDB; 

DROP TABLE IF EXISTS VIGILANT_BELONGS_TO_GROUP;
CREATE table VIGILANT_BELONGS_TO_GROUP (
KEY_VIGILANT INTEGER NOT NULL, 
KEY_VIGILANT_GROUP INTEGER NOT NULL
) TYPE=InnoDB;

DROP TABLE IF EXISTS EXAM_COORDINATOR_MANAGES_VIGILANT_GROUP;
CREATE TABLE EXAM_COORDINATOR_MANAGES_VIGILANT_GROUP(
KEY_EXAM_COORDINATOR INTEGER NOT NULL,
KEY_VIGILANT_GROUP INTEGER NOT NULL
) TYPE=InnoDB;

alter table EXECUTION_COURSE add column KEY_VIGILANT_GROUP int(11);
insert into ROLE values('','EXAM_COORDINATOR','/examCoordination','index.do','portal.examCoordinator','','1');
