--  SQL file representing changes to the functionalities model
--  Generated at Tue Dec 11 15:19:42 WET 2007
--  DO NOT EDIT THIS FILE, run the generating script instead

--  Preamble
SET AUTOCOMMIT = 0;

START TRANSACTION;

-- 
--  Updating existing functionalities
-- 

--  ID: 11 UUID: '36496cce-ebc4-4a27-b16e-a408672f0bf6'
UPDATE `ACCESSIBLE_ITEM` AS own SET own.`NAME` = 'pt15:Área de Pessoal' WHERE own.`UUID` = '36496cce-ebc4-4a27-b16e-a408672f0bf6';

COMMIT;