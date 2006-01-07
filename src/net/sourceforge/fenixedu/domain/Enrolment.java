package net.sourceforge.fenixedu.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.fenixedu.commons.CollectionUtils;
import net.sourceforge.fenixedu.domain.curriculum.EnrollmentCondition;
import net.sourceforge.fenixedu.domain.curriculum.EnrollmentState;
import net.sourceforge.fenixedu.domain.curriculum.EnrolmentEvaluationType;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.util.EnrolmentEvaluationState;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * @author dcs-rjao
 * 
 * 24/Mar/2003
 */

public class Enrolment extends Enrolment_Base {

    private Integer accumulatedWeight;

    public Enrolment() {
        this.setOjbConcreteClass(this.getClass().getName());
    }

    public Integer getAccumulatedWeight() {
        return accumulatedWeight;
    }

    public void setAccumulatedWeight(Integer accumulatedWeight) {
        this.accumulatedWeight = accumulatedWeight;
    }

    public void initializeAsNew(StudentCurricularPlan studentCurricularPlan,
            CurricularCourse curricularCourse, ExecutionPeriod executionPeriod,
            EnrollmentCondition enrolmentCondition, String createdBy) {
        initializeAsNewWithoutEnrolmentEvaluation(studentCurricularPlan, curricularCourse,
                executionPeriod, enrolmentCondition, createdBy);
        createEnrolmentEvaluationWithoutGrade();
    }

    public void initializeAsNewWithoutEnrolmentEvaluation(StudentCurricularPlan studentCurricularPlan,
            CurricularCourse curricularCourse, ExecutionPeriod executionPeriod,
            EnrollmentCondition enrolmentCondition, String createdBy) {
        setCurricularCourse(curricularCourse);
        setEnrollmentState(EnrollmentState.ENROLLED);
        setExecutionPeriod(executionPeriod);
        setStudentCurricularPlan(studentCurricularPlan);
        setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);
        setCreationDate(new Date());
        setCondition(enrolmentCondition);
        setCreatedBy(createdBy);

        createAttend(studentCurricularPlan.getStudent(), curricularCourse, executionPeriod);
    }

    public String toString() {
        String result = "[" + this.getClass().getName() + "; ";
        result += "idInternal = " + super.getIdInternal() + "; ";
        result += "studentCurricularPlan = " + this.getStudentCurricularPlan() + "; ";
        result += "enrollmentState = " + this.getEnrollmentState() + "; ";
        result += "execution OccupationPeriod = " + this.getExecutionPeriod() + "; ";
        result += "curricularCourse = " + this.getCurricularCourse() + "]\n";
        return result;
    }

    public void unEnroll() throws DomainException {

        for (EnrolmentEvaluation eval : getEvaluations()) {

            if (eval.getEnrolmentEvaluationType().equals(EnrolmentEvaluationType.NORMAL)
                    && eval.getEnrolmentEvaluationState().equals(EnrolmentEvaluationState.TEMPORARY_OBJ)
                    && (eval.getGrade() == null || eval.getGrade().equals("")))
                ;
            else
                throw new DomainException("error.enrolment.cant.unenroll");
        }

        delete();
    }

    public void delete() {
        removeExecutionPeriod();
        removeStudentCurricularPlan();
        removeCurricularCourse();

        Iterator<Attends> attendsIter = getAttendsIterator();
        while (attendsIter.hasNext()) {
            Attends attends = attendsIter.next();

            try {
                attendsIter.remove();
                attends.removeEnrolment();
                attends.delete();
            } catch (DomainException e) {
            }
        }

        Iterator<EnrolmentEvaluation> evalsIter = getEvaluationsIterator();
        while (evalsIter.hasNext()) {
            EnrolmentEvaluation eval = evalsIter.next();
            evalsIter.remove();
            eval.delete();
        }

        Iterator<CreditsInAnySecundaryArea> creditsInAnysecundaryAreaIterator = getCreditsInAnySecundaryAreasIterator();

        while (creditsInAnysecundaryAreaIterator.hasNext()) {
            CreditsInAnySecundaryArea credits = creditsInAnysecundaryAreaIterator.next();
            creditsInAnysecundaryAreaIterator.remove();
            credits.delete();
        }

        Iterator<CreditsInScientificArea> creditsInScientificAreaIterator = getCreditsInScientificAreasIterator();

        while (creditsInScientificAreaIterator.hasNext()) {
            CreditsInScientificArea credits = creditsInScientificAreaIterator.next();
            creditsInScientificAreaIterator.remove();
            credits.delete();
        }

        Iterator<EquivalentEnrolmentForEnrolmentEquivalence> equivalentEnrolmentIterator = getEquivalentEnrolmentForEnrolmentEquivalencesIterator();

        while (equivalentEnrolmentIterator.hasNext()) {
            EquivalentEnrolmentForEnrolmentEquivalence equivalentEnrolment = equivalentEnrolmentIterator
                    .next();
            equivalentEnrolmentIterator.remove();
            equivalentEnrolment.removeEquivalentEnrolment();

            EnrolmentEquivalence equivalence = equivalentEnrolment.getEnrolmentEquivalence();
            Enrolment enrolment = equivalence.getEnrolment();

            equivalence.removeEnrolment();
            enrolment.delete();
            equivalentEnrolment.removeEnrolmentEquivalence();

            equivalentEnrolment.delete();
            equivalence.delete();
        }

        Iterator<EnrolmentEquivalence> equivalenceIterator = getEnrolmentEquivalencesIterator();

        while (equivalenceIterator.hasNext()) {
            EnrolmentEquivalence equivalence = equivalenceIterator.next();
            equivalenceIterator.remove();
            equivalence.removeEnrolment();

            Iterator<EquivalentEnrolmentForEnrolmentEquivalence> equivalentRestrictionIterator = equivalence
                    .getEquivalenceRestrictionsIterator();

            while (equivalentRestrictionIterator.hasNext()) {
                EquivalentEnrolmentForEnrolmentEquivalence equivalentRestriction = equivalentRestrictionIterator
                        .next();
                equivalentRestriction.removeEquivalentEnrolment();
                equivalentRestrictionIterator.remove();
                equivalentRestriction.removeEnrolmentEquivalence();

                equivalentRestriction.delete();
            }
            equivalence.delete();
        }

        super.deleteDomainObject();

    }

    public EnrolmentEvaluation getImprovementEvaluation() {

        for (EnrolmentEvaluation evaluation : getEvaluations()) {
            if (evaluation.getEnrolmentEvaluationType().equals(EnrolmentEvaluationType.IMPROVEMENT)
                    && evaluation.getEnrolmentEvaluationState().equals(
                            EnrolmentEvaluationState.TEMPORARY_OBJ))

                return evaluation;
        }

        return null;
    }

    public EnrolmentEvaluation getEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade(
            final EnrolmentEvaluationType evaluationType, final String grade) {

        return (EnrolmentEvaluation) CollectionUtils.find(getEvaluations(), new Predicate() {

            public boolean evaluate(Object o) {
                EnrolmentEvaluation enrolmentEvaluation = (EnrolmentEvaluation) o;
                String evaluationGrade = enrolmentEvaluation.getGrade();

                return enrolmentEvaluation.getEnrolmentEvaluationType().equals(evaluationType)
                        && ((grade == null && evaluationGrade == null) || (evaluationGrade != null && evaluationGrade
                                .equals(grade)));
            }

        });
    }

    private EnrolmentEvaluation getEnrolmentEvaluationByEnrolmentEvaluationStateAndType(
            final EnrolmentEvaluationState state, final EnrolmentEvaluationType type) {
        return (EnrolmentEvaluation) CollectionUtils.find(getEvaluations(), new Predicate() {

            public boolean evaluate(Object o) {
                EnrolmentEvaluation enrolmentEvaluation = (EnrolmentEvaluation) o;
                return (enrolmentEvaluation.getEnrolmentEvaluationState().equals(state) && enrolmentEvaluation
                        .getEnrolmentEvaluationType().equals(type));
            }

        });
    }

    public EnrolmentEvaluation submitEnrolmentEvaluation(
            EnrolmentEvaluationType enrolmentEvaluationType, Mark publishedMark, Employee employee,
            Person personResponsibleForGrade, Date evaluationDate, String observation) {

        EnrolmentEvaluation enrolmentEvaluation = getEnrolmentEvaluationByEnrolmentEvaluationStateAndType(
                EnrolmentEvaluationState.TEMPORARY_OBJ, enrolmentEvaluationType);

        // There can be only one enrolmentEvaluation with Temporary State
        if (enrolmentEvaluation == null) {
            enrolmentEvaluation = new EnrolmentEvaluation();
            enrolmentEvaluation.setEnrolment(this);
        }

        // teacher responsible for execution course
        String grade = null;
        if ((publishedMark == null) || (publishedMark.getMark().length() == 0))
            grade = "NA";
        else
            grade = publishedMark.getMark().toUpperCase();

        enrolmentEvaluation.setGrade(grade);

        enrolmentEvaluation.setEnrolmentEvaluationType(enrolmentEvaluationType);
        enrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
        enrolmentEvaluation.setObservation(observation);
        enrolmentEvaluation.setPersonResponsibleForGrade(personResponsibleForGrade);

        enrolmentEvaluation.setEmployee(employee);

        Calendar calendar = Calendar.getInstance();
        enrolmentEvaluation.setWhen(new Timestamp(calendar.getTimeInMillis()));
        enrolmentEvaluation.setGradeAvailableDate(calendar.getTime());
        if (evaluationDate != null) {
            enrolmentEvaluation.setExamDate(evaluationDate);
        } else {
            enrolmentEvaluation.setExamDate(calendar.getTime());
        }

        enrolmentEvaluation.setCheckSum("");

        return enrolmentEvaluation;
    }

    private void createEnrolmentEvaluationWithoutGrade() {

        EnrolmentEvaluation enrolmentEvaluation = getEnrolmentEvaluationByEnrolmentEvaluationTypeAndGrade(
                EnrolmentEvaluationType.NORMAL, null);

        if (enrolmentEvaluation == null) {
            enrolmentEvaluation = new EnrolmentEvaluation();
            enrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
            enrolmentEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.NORMAL);

            addEvaluations(enrolmentEvaluation);
        }
    }

    private void createAttend(Student student, CurricularCourse curricularCourse,
            ExecutionPeriod executionPeriod) {

        List executionCourses = curricularCourse.getExecutionCoursesByExecutionPeriod(executionPeriod);

        ExecutionCourse executionCourse = null;
        if (executionCourses.size() > 1) {
            Iterator iterator = executionCourses.iterator();
            while (iterator.hasNext()) {
                ExecutionCourse executionCourse2 = (ExecutionCourse) iterator.next();
                if (executionCourse2.getExecutionCourseProperties() == null
                        || executionCourse2.getExecutionCourseProperties().isEmpty()) {
                    executionCourse = executionCourse2;
                }
            }
        } else if (executionCourses.size() == 1) {
            executionCourse = (ExecutionCourse) executionCourses.get(0);
        }

        if (executionCourse != null) {
            Attends attend = executionCourse.getAttendsByStudent(student);

            if (attend != null) {
                addAttends(attend);
            } else {
                Attends attendToWrite = new Attends(student, executionCourse);
                addAttends(attendToWrite);
            }
        }
    }

    public void createEnrolmentEvaluationForImprovement(Employee employee,
            ExecutionPeriod currentExecutionPeriod, Student student) {

        EnrolmentEvaluation enrolmentEvaluation = new EnrolmentEvaluation();

        enrolmentEvaluation.setEmployee(employee);
        enrolmentEvaluation.setWhen(new Date());
        enrolmentEvaluation.setEnrolment(this);
        enrolmentEvaluation.setEnrolmentEvaluationState(EnrolmentEvaluationState.TEMPORARY_OBJ);
        enrolmentEvaluation.setEnrolmentEvaluationType(EnrolmentEvaluationType.IMPROVEMENT);

        createAttendForImprovment(currentExecutionPeriod, student);
    }

    private void createAttendForImprovment(final ExecutionPeriod currentExecutionPeriod,
            final Student student) {

        List executionCourses = getCurricularCourse().getAssociatedExecutionCourses();
        ExecutionCourse currentExecutionCourse = (ExecutionCourse) CollectionUtils.find(
                executionCourses, new Predicate() {

                    public boolean evaluate(Object arg0) {
                        ExecutionCourse executionCourse = (ExecutionCourse) arg0;
                        if (executionCourse.getExecutionPeriod().equals(currentExecutionPeriod))
                            return true;
                        return false;
                    }

                });

        if (currentExecutionCourse != null) {
            List attends = currentExecutionCourse.getAttends();
            Attends attend = (Attends) CollectionUtils.find(attends, new Predicate() {

                public boolean evaluate(Object arg0) {
                    Attends frequenta = (Attends) arg0;
                    if (frequenta.getAluno().equals(student))
                        return true;
                    return false;
                }

            });

            if (attend != null) {
                attend.setEnrolment(this);
            } else {
                attend = new Attends(student, currentExecutionCourse);
                attend.setEnrolment(this);
            }
        }
    }

    public boolean isImprovementForExecutionCourse(ExecutionCourse executionCourse) {
        return !getExecutionPeriod().equals(executionCourse.getExecutionPeriod());
    }

    public void unEnrollImprovement(final ExecutionPeriod executionPeriod) throws DomainException {
        EnrolmentEvaluation improvmentEnrolmentEvaluation = getImprovementEvaluation();
        if (improvmentEnrolmentEvaluation != null) {

            improvmentEnrolmentEvaluation.delete();

            final Student student = getStudentCurricularPlan().getStudent();
            List<ExecutionCourse> executionCourses = getCurricularCourse()
                    .getAssociatedExecutionCourses();

            ExecutionCourse currentExecutionCourse = (ExecutionCourse) CollectionUtils.find(
                    executionCourses, new Predicate() {

                        public boolean evaluate(Object arg0) {
                            ExecutionCourse executionCourse = (ExecutionCourse) arg0;
                            if (executionCourse.getExecutionPeriod().equals(executionPeriod))
                                return true;
                            return false;
                        }
                    });

            if (currentExecutionCourse != null) {
                List attends = currentExecutionCourse.getAttends();
                Attends attend = (Attends) CollectionUtils.find(attends, new Predicate() {

                    public boolean evaluate(Object arg0) {
                        Attends frequenta = (Attends) arg0;
                        if (frequenta.getAluno().equals(student))
                            return true;
                        return false;
                    }
                });

                if (attend != null) {
                    try {
                        attend.delete();
                    } catch (DomainException e) {
                        // nothing to be done
                    }
                }
            }
        } else {
            throw new DomainException("error.enrolment.cant.unenroll.improvement");
        }
    }

    public List<EnrolmentEvaluation> getAllFinalEnrolmentEvaluations() {
        return (List<EnrolmentEvaluation>) CollectionUtils.select(getEvaluations(), new Predicate() {

            public boolean evaluate(Object arg0) {
                EnrolmentEvaluation enrolmentEvaluation = (EnrolmentEvaluation) arg0;
                return enrolmentEvaluation.getEnrolmentEvaluationState().equals(
                        EnrolmentEvaluationState.FINAL_OBJ);
            }

        });
    }

    public boolean hasSpecialSeason() {
        for (EnrolmentEvaluation enrolmentEvaluation : getEvaluations()) {
            if (enrolmentEvaluation.getEnrolmentEvaluationType().equals(
                    EnrolmentEvaluationType.SPECIAL_SEASON)) {
                return true;
            }
        }
        return false;
    }

    public Integer getFinalGrade() {
        
        EnrolmentEvaluation enrolmentEvaluation = null;
        
        for (EnrolmentEvaluation evaluation : getEvaluations()) {
            if(evaluation.getEnrolmentEvaluationState().equals(EnrolmentEvaluationState.FINAL_OBJ)){
                if(enrolmentEvaluation == null || evaluation.compareTo(enrolmentEvaluation) > 0) {
                    enrolmentEvaluation = evaluation;
                }
            }
        }
        
        return (enrolmentEvaluation == null || !StringUtils.isNumeric(enrolmentEvaluation.getGrade())) ? null : Integer
                .valueOf(enrolmentEvaluation.getGrade());

    }

    public Boolean isFirstTime() {
        return this.getStudentCurricularPlan().getEnrolments(this.getCurricularCourse()).size() == 1;
    }
}