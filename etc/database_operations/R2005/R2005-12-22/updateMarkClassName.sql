SELECT CONCAT("UPDATE MARK SET MARK.CLASS_NAME = 'net.sourceforge.fenixedu.domain.Mark' WHERE MARK.ID_INTERNAL =", MARK.ID_INTERNAL,";") AS '' FROM MARK INNER JOIN EVALUATION ON MARK.KEY_EVALUATION = EVALUATION.ID_INTERNAL WHERE EVALUATION.CLASS_NAME != "net.sourceforge.fenixedu.domain.FinalEvaluation";