/*
 * Created on 23/Jun/2004
 *
 */
package net.sourceforge.fenixedu.dataTransferObject;

import net.sourceforge.fenixedu.domain.Enrolment;

/**
 * @author T�nia Pous�o 23/Jun/2004
 */
public class InfoEnrolmentWithCourseAndDegree extends InfoEnrolment {
    public void copyFromDomain(Enrolment enrolment) {
        super.copyFromDomain(enrolment);
        if (enrolment != null) {
            setInfoCurricularCourse(InfoCurricularCourseWithInfoDegreeAndScopes.newInfoFromDomain(enrolment
                    .getCurricularCourse()));//with degree

        }
    }

    public static InfoEnrolment newInfoFromDomain(Enrolment enrolment) {
        InfoEnrolmentWithCourseAndDegree infoEnrolment = null;
        if (enrolment != null) {
            infoEnrolment = new InfoEnrolmentWithCourseAndDegree();
            infoEnrolment.copyFromDomain(enrolment);
        }

        return infoEnrolment;
    }
}