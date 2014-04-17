CREATE TABLE `INDIVIDUAL_CANDIDACY_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE` (`OID_INDIVIDUAL_CANDIDACY` bigint(20) NOT NULL, `OID_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE` bigint(20) NOT NULL, primary key(OID_INDIVIDUAL_CANDIDACY, OID_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE), index (OID_INDIVIDUAL_CANDIDACY), index (OID_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE)) ENGINE=InnoDB, character set latin1;

INSERT INTO `INDIVIDUAL_CANDIDACY_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE` (`OID_INDIVIDUAL_CANDIDACY`, `OID_INDIVIDUAL_CANDIDACY_DOCUMENT_FILE`) SELECT `OID_INDIVIDUAL_CANDIDACY`, `OID` FROM FILE WHERE `OID_INDIVIDUAL_CANDIDACY` IS NOT NULL;