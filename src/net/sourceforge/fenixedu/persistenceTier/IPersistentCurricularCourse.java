package net.sourceforge.fenixedu.persistenceTier;

import java.util.List;

import net.sourceforge.fenixedu.domain.CurricularCourse;
import net.sourceforge.fenixedu.domain.degreeStructure.CurricularStage;

/**
 * @author dcs-rjao 25/Mar/2003
 */

public interface IPersistentCurricularCourse extends IPersistentObject {

    public List<CurricularCourse> readByCurricularStage(CurricularStage curricularStage) throws ExcepcaoPersistencia;    
    
    public List readCurricularCoursesByDegreeCurricularPlan(String name, String degreeName, String degreeSigla)
            throws ExcepcaoPersistencia;
	
    public CurricularCourse readCurricularCourseByDegreeCurricularPlanAndNameAndCode(
            Integer degreeCurricularPlanId, String name, String code) throws ExcepcaoPersistencia;

    public List readCurricularCoursesByDegreeCurricularPlanAndBasicAttribute(
			Integer degreeCurricularPlanKey, Boolean basic) throws ExcepcaoPersistencia;

    public List readbyCourseCodeAndDegreeCurricularPlan(String curricularCourseCode,
            Integer degreeCurricularPlanID) throws ExcepcaoPersistencia;
	
    public List readbyCourseNameAndDegreeCurricularPlan(String curricularCourseName,
			Integer degreeCurricularPlanKey) throws ExcepcaoPersistencia;

    public List readExecutedCurricularCoursesByDegreeAndExecutionYear(Integer degreeKey, 
			Integer executionYearKey) throws ExcepcaoPersistencia;

    public List readExecutedCurricularCoursesByDegreeAndYearAndExecutionYear(Integer degreeKey, 
			Integer year, Integer executionYearKey) throws ExcepcaoPersistencia;

}