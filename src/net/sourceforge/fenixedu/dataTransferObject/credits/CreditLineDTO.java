/**
 * Dec 5, 2005
 */
package net.sourceforge.fenixedu.dataTransferObject.credits;

import net.sourceforge.fenixedu.domain.ExecutionPeriod;
import net.sourceforge.fenixedu.domain.teacher.TeacherService;

/**
 * @author Ricardo Rodrigues
 * 
 */

public class CreditLineDTO {

    private double teachingDegreeCredits = 0;

    private double supportLessonHours = 0;

    private double masterDegreeCredits = 0;

    private double tfcAdviseCredits = 0;

    private double otherCredits = 0;

    private double institutionWorkingHours = 0;

    private double managementCredits = 0;

    private double serviceExemptionCredits = 0;

    private double pastServiceCredits = 0;

    private int mandatoryLessonHours = 0;

    private ExecutionPeriod executionPeriod;

    public CreditLineDTO(ExecutionPeriod executionPeriod, TeacherService teacherService,
            double managementCredits, double exemptionCredits, int lessonHours) {
        setExecutionPeriod(executionPeriod);
        if (teacherService != null) {
            setTeachingDegreeCredits(teacherService.getTeachingDegreeCredits());
            setSupportLessonHours(teacherService.getSupportLessonHours());
            setMasterDegreeCredits(teacherService.getMasterDegreeServiceCredits());
            setTfcAdviseCredits(teacherService.getTeacherAdviseServiceCredits());
            setOtherCredits(teacherService.getOtherServiceCredits());
            setInstitutionWorkingHours(teacherService.getInstitutionWorkingHours());
            setPastServiceCredits(teacherService.getPastServiceCredits());
        }
        setMandatoryLessonHours(lessonHours);
        setManagementCredits(managementCredits);
        setServiceExemptionCredits(exemptionCredits);
    }

    public double getTotalCredits() {
        double totalCredits = getTeachingDegreeCredits() + getMasterDegreeCredits()
                + getTfcAdviseCredits() + getOtherCredits() + getManagementCredits()
                + getServiceExemptionCredits();
        return totalCredits;
    }

    public double getManagementCredits() {
        return managementCredits;
    }

    public void setManagementCredits(double managementCredits) {
        this.managementCredits = managementCredits;
    }

    public double getOtherCredits() {
        return otherCredits;
    }

    public void setOtherCredits(double otherCredits) {
        this.otherCredits = otherCredits;
    }

    public double getServiceExemptionCredits() {
        return serviceExemptionCredits;
    }

    public void setServiceExemptionCredits(double serviceExemptionsCredits) {
        this.serviceExemptionCredits = serviceExemptionsCredits;
    }

    public double getSupportLessonHours() {
        return supportLessonHours;
    }

    public void setSupportLessonHours(double supportLessonHours) {
        this.supportLessonHours = supportLessonHours;
    }

    public double getTeachingDegreeCredits() {
        return teachingDegreeCredits;
    }

    public void setTeachingDegreeCredits(double teachingDegreeCredits) {
        this.teachingDegreeCredits = teachingDegreeCredits;
    }

    public double getTfcAdviseCredits() {
        return tfcAdviseCredits;
    }

    public void setTfcAdviseCredits(double tfcAdviseCredits) {
        this.tfcAdviseCredits = tfcAdviseCredits;
    }

    public double getInstitutionWorkingHours() {
        return institutionWorkingHours;
    }

    public void setInstitutionWorkingHours(double institutionWorkingHours) {
        this.institutionWorkingHours = institutionWorkingHours;
    }

    public double getPastServiceCredits() {
        return pastServiceCredits;
    }

    public void setPastServiceCredits(double pastServiceCredits) {
        this.pastServiceCredits = pastServiceCredits;
    }

    public ExecutionPeriod getExecutionPeriod() {
        return executionPeriod;
    }

    public void setExecutionPeriod(ExecutionPeriod executionPeriod) {
        this.executionPeriod = executionPeriod;
    }

    public int getMandatoryLessonHours() {
        return mandatoryLessonHours;
    }

    public void setMandatoryLessonHours(int mandatoryLessonHours) {
        this.mandatoryLessonHours = mandatoryLessonHours;
    }

    public double getMasterDegreeCredits() {
        return masterDegreeCredits;
    }

    public void setMasterDegreeCredits(double masterDegreeCredits) {
        this.masterDegreeCredits = masterDegreeCredits;
    }
}
