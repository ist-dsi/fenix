/*
 * Created on Dec 1, 2003 by jpvl
 *  
 */
package net.sourceforge.fenixedu.applicationTier.Servico.department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.InfoExecutionPeriodWithInfoExecutionYear;
import net.sourceforge.fenixedu.dataTransferObject.credits.InfoCredits;
import net.sourceforge.fenixedu.dataTransferObject.teacher.InfoCategory;
import net.sourceforge.fenixedu.dataTransferObject.teacher.credits.TeacherCreditsDetailsDTO;
import net.sourceforge.fenixedu.domain.Department;
import net.sourceforge.fenixedu.domain.ExecutionPeriod;
import net.sourceforge.fenixedu.domain.Department;
import net.sourceforge.fenixedu.domain.ExecutionPeriod;
import net.sourceforge.fenixedu.domain.Teacher;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentDepartment;
import net.sourceforge.fenixedu.persistenceTier.IPersistentExecutionPeriod;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import net.sourceforge.fenixedu.tools.Profiler;
import pt.utl.ist.berserk.logic.serviceManager.IService;

/**
 * @author jpvl
 */
public class ReadDepartmentTeachersCreditsDetailsService implements IService {

    public List run(HashMap searchParameters) throws FenixServiceException, ExcepcaoPersistencia {
        ISuportePersistente sp;
        List teachers;

        Profiler.getInstance();
        Profiler.resetInstance();

        sp = PersistenceSupportFactory.getDefaultPersistenceSupport();

        teachers = doSearch(searchParameters, sp);

        IPersistentExecutionPeriod executionPeriodDAO = sp.getIPersistentExecutionPeriod();
        final ExecutionPeriod executionPeriod = readExecutionPeriod(searchParameters,
                executionPeriodDAO);

        InfoExecutionPeriodWithInfoExecutionYear infoExecutionPeriod = (InfoExecutionPeriodWithInfoExecutionYear) InfoExecutionPeriodWithInfoExecutionYear
                .newInfoFromDomain(executionPeriod);

        List list = new ArrayList();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = (Teacher) teachers.get(i);
            TeacherCreditsDetailsDTO details = new TeacherCreditsDetailsDTO();
            InfoCredits infoCredits = teacher.getExecutionPeriodCredits(executionPeriod);
            if (teacher.getCategory() != null) {
                details.setCategory(InfoCategory.newInfoFromDomain(teacher.getCategory()));
            }
            details.setTeacherId(teacher.getIdInternal());
            details.setTeacherName(teacher.getPerson().getNome());
            details.setTeacherNumber(teacher.getTeacherNumber());
            details.setInfoCredits(infoCredits);
            details.setInfoExecution(infoExecutionPeriod);
            list.add(details);
        }
        return list;
    }

    private ExecutionPeriod readExecutionPeriod(HashMap searchParameters,
            IPersistentExecutionPeriod executionPeriodDAO) throws ExcepcaoPersistencia {
        final ExecutionPeriod executionPeriod;
        Integer executionPeriodId = null;
        try {
            executionPeriodId = Integer.valueOf((String) searchParameters.get("executionPeriodId"));
        } catch (NumberFormatException e) {
        }

        if (executionPeriodId == null) {
            executionPeriod = executionPeriodDAO.readActualExecutionPeriod();
        } else {
            executionPeriod = (ExecutionPeriod) executionPeriodDAO.readByOID(ExecutionPeriod.class,
                    executionPeriodId);
        }
        return executionPeriod;
    }

    protected List doSearch(HashMap searchParameters, ISuportePersistente sp)
            throws ExcepcaoPersistencia {
        Integer departmentId = Integer.valueOf((String) searchParameters.get("idInternal"));
        IPersistentDepartment departmentDAO = sp.getIDepartamentoPersistente();
        Department department = (Department) departmentDAO.readByOID(Department.class, departmentId);
        
        List teachers = department.getCurrentTeachers();
        return teachers;
    }
}