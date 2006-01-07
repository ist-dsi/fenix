package net.sourceforge.fenixedu.persistenceTier.OJB;

import java.util.List;

import net.sourceforge.fenixedu.domain.CurricularCourse;
import net.sourceforge.fenixedu.domain.CurricularCourse;
import net.sourceforge.fenixedu.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import net.sourceforge.fenixedu.domain.degreeStructure.CurricularStage;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentCurricularCourse;

import org.apache.ojb.broker.query.Criteria;

/**
 * @author dcs-rjao
 * 
 * 25/Mar/2003
 */

public class CurricularCourseOJB extends PersistentObjectOJB implements IPersistentCurricularCourse {

    public List<CurricularCourse> readByCurricularStage(CurricularStage curricularStage) throws ExcepcaoPersistencia {
        Criteria crit = new Criteria();
        crit.addEqualTo("curricularStage", curricularStage);
        return queryList(CurricularCourse.class, crit);
    }
    
    public List readCurricularCoursesByDegreeCurricularPlan(String name, String degreeName, String degreeSigla)
            throws ExcepcaoPersistencia {
        Criteria crit = new Criteria();
        crit.addEqualTo("degreeCurricularPlan.name", name);
        crit.addEqualTo("degreeCurricularPlan.degree.nome", degreeName);
        crit.addEqualTo("degreeCurricularPlan.degree.sigla", degreeSigla);
        crit.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, crit);
    }
	
	
    public CurricularCourse readCurricularCourseByDegreeCurricularPlanAndNameAndCode(
            Integer degreeCurricularPlanId, String name, String code) throws ExcepcaoPersistencia {
        Criteria crit = new Criteria();
        crit.addEqualTo("name", name);
        crit.addEqualTo("code", code);
        crit.addEqualTo("degreeCurricularPlan.idInternal", degreeCurricularPlanId);
        crit.addEqualTo("curricularStage", CurricularStage.OLD);
        return (CurricularCourse) queryObject(CurricularCourse.class, crit);

    }

    public List readCurricularCoursesByDegreeCurricularPlanAndBasicAttribute(
			Integer degreeCurricularPlanKey, Boolean basic) throws ExcepcaoPersistencia {

        Criteria criteria = new Criteria();
        criteria.addEqualTo("degreeCurricularPlanKey", degreeCurricularPlanKey);
        criteria.addEqualTo("basic", basic);
        criteria.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, criteria);
    }


    public List readbyCourseCodeAndDegreeCurricularPlan(String curricularCourseCode,
            Integer degreeCurricularPlanID) throws ExcepcaoPersistencia {

        Criteria criteria = new Criteria();
        criteria.addEqualTo("code", curricularCourseCode);
        criteria.addEqualTo("degreeCurricularPlan.idInternal", degreeCurricularPlanID);
        criteria.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, criteria);
    }
	
    public List readbyCourseNameAndDegreeCurricularPlan(String curricularCourseName,
            Integer degreeCurricularPlanKey) throws ExcepcaoPersistencia {

        Criteria criteria = new Criteria();
        criteria.addEqualTo("name", curricularCourseName);
        criteria.addEqualTo("degreeCurricularPlan.idInternal", degreeCurricularPlanKey);
        criteria.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, criteria);
    }


    public List readExecutedCurricularCoursesByDegreeAndExecutionYear(Integer degreeKey, 
			Integer executionYearKey) throws ExcepcaoPersistencia {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("degreeCurricularPlan.degree.idInternal", degreeKey);
        criteria.addEqualTo("degreeCurricularPlan.state", DegreeCurricularPlanState.ACTIVE);
        criteria.addEqualTo("associatedExecutionCourses.executionPeriod.executionYear.idInternal", executionYearKey);
        criteria.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, criteria, true);
    }


    public List readExecutedCurricularCoursesByDegreeAndYearAndExecutionYear(Integer degreeKey, 
			Integer year, Integer executionYearKey) throws ExcepcaoPersistencia {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("degreeCurricularPlan.degree.idInternal", degreeKey);
        criteria.addEqualTo("degreeCurricularPlan.state", DegreeCurricularPlanState.ACTIVE);
        criteria.addEqualTo("associatedExecutionCourses.executionPeriod.executionYear.idInternal",
                executionYearKey);
        criteria.addEqualTo("scopes.curricularSemester.curricularYear.year", year);
        criteria.addEqualTo("curricularStage", CurricularStage.OLD);
        return queryList(CurricularCourse.class, criteria, true);
    }


}
