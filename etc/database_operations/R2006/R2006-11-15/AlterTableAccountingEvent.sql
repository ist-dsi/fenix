ALTER TABLE ACCOUNTING_EVENT DROP INDEX KEY_REGISTRATION_FOR_GRATUITY_EVENT;
ALTER TABLE ACCOUNTING_EVENT DROP INDEX KEY_REGISTRATION_FOR_DFA_REGISTRATION_EVENT;
ALTER TABLE ACCOUNTING_EVENT DROP COLUMN KEY_REGISTRATION_FOR_GRATUITY_EVENT;
ALTER TABLE ACCOUNTING_EVENT CHANGE COLUMN KEY_REGISTRATION_FOR_DFA_REGISTRATION_EVENT KEY_REGISTRATION int(11) NULL;
ALTER TABLE ACCOUNTING_EVENT ADD INDEX (KEY_REGISTRATION);