select p.* from person p inner join mwgrant_migracao_pessoa mwp on p.document_id_number=mwp.numeroDocumentoIdentificacao;
select t.* from teacher t inner join mwgrant_migracao_docente mwd on t.teacher_number=mwd.numero;