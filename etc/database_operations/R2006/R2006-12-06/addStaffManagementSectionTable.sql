CREATE TABLE `STAFF_MANAGEMENT_SECTION` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `SECTION_MANAGERS` blob,
  `KEY_ROOT_DOMAIN_OBJECT` int(11) NOT NULL default '1',
  PRIMARY KEY  (`ID_INTERNAL`)
) ENGINE=InnoDB;

CREATE TABLE `MONTH_CLOSURE` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `KEY_ASSIDUOUSNESS` int(11) unsigned NOT NULL,
  `YEAR_MONTH` VARCHAR(24) NOT NULL,
  `KEY_ROOT_DOMAIN_OBJECT` int(11) NOT NULL default '1',
  PRIMARY KEY  (`ID_INTERNAL`),
  UNIQUE KEY `u1` (`KEY_ASSIDUOUSNESS`, `YEAR_MONTH`),
  KEY `MONTH_OF_YEAR` (`YEAR_MONTH`)
) ENGINE=InnoDB;
