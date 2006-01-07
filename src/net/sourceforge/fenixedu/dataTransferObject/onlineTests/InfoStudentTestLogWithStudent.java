/*
 * Created on 10/Set/2003
 *
 */
package net.sourceforge.fenixedu.dataTransferObject.onlineTests;

import net.sourceforge.fenixedu.dataTransferObject.InfoStudent;
import net.sourceforge.fenixedu.domain.onlineTests.StudentTestLog;

/**
 * @author Susana Fernandes
 */
public class InfoStudentTestLogWithStudent extends InfoStudentTestLog {

    public void copyFromDomain(StudentTestLog studentTestLog) {
        super.copyFromDomain(studentTestLog);
        if (studentTestLog != null) {
            setInfoStudent(InfoStudent.newInfoFromDomain(studentTestLog.getStudent()));
        }
    }

    public static InfoStudentTestLog newInfoFromDomain(StudentTestLog studentTestLog) {
        InfoStudentTestLogWithStudent infoStudentTestLog = null;
        if (studentTestLog != null) {
            infoStudentTestLog = new InfoStudentTestLogWithStudent();
            infoStudentTestLog.copyFromDomain(studentTestLog);
        }
        return infoStudentTestLog;
    }

}