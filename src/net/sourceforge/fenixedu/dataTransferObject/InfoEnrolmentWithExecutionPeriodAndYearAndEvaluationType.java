/*
 * Created on Dec 21, 2004
 *
 */
package net.sourceforge.fenixedu.dataTransferObject;

import net.sourceforge.fenixedu.domain.Enrolment;

/**
 * @author Andr� Fernandes / Jo�o Brito
 */
public class InfoEnrolmentWithExecutionPeriodAndYearAndEvaluationType extends
        InfoEnrolmentWithExecutionPeriodAndYear
{
    public void copyFromDomain(Enrolment enrolment) {
        super.copyFromDomain(enrolment);
    }

    public static InfoEnrolment newInfoFromDomain(Enrolment enrolment) {
        InfoEnrolmentWithExecutionPeriodAndYearAndEvaluationType infoEnrolment = null;
        if (enrolment != null) {
            infoEnrolment = new InfoEnrolmentWithExecutionPeriodAndYearAndEvaluationType();
            infoEnrolment.copyFromDomain(enrolment);
        }

        return infoEnrolment;
    }
}
