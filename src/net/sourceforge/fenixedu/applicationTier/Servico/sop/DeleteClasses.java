/*
 *
 * Created on 2003/08/15
 */

package net.sourceforge.fenixedu.applicationTier.Servico.sop;

/**
 * 
 * @author Luis Cruz & Sara Ribeiro
 */
import java.util.Iterator;
import java.util.List;

import net.sourceforge.fenixedu.domain.SchoolClass;
import net.sourceforge.fenixedu.domain.Shift;
import net.sourceforge.fenixedu.domain.SchoolClass;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import pt.utl.ist.berserk.logic.serviceManager.IService;

public class DeleteClasses implements IService {

    public Object run(List classOIDs) throws ExcepcaoPersistencia {

        final ISuportePersistente sp = PersistenceSupportFactory.getDefaultPersistenceSupport();

        for (int i = 0; i < classOIDs.size(); i++) {
            Integer classId = (Integer) classOIDs.get(i);
            final SchoolClass schoolClass = (SchoolClass) sp.getITurmaPersistente().readByOID(
                    SchoolClass.class, classId);

            // Shift
            Iterator iter = schoolClass.getAssociatedShiftsIterator();
            while(iter.hasNext()){
                Shift shift = (Shift) iter.next();
                iter.remove();
                shift.getAssociatedClasses().remove(schoolClass);
            }
            schoolClass.getAssociatedShifts().clear();

            // ExecutionDegree
            schoolClass.getExecutionDegree().getSchoolClasses().remove(schoolClass);
            schoolClass.setExecutionDegree(null);

            // ExecutionPeriod
            schoolClass.getExecutionPeriod().getSchoolClasses().remove(schoolClass);
            schoolClass.setExecutionPeriod(null);

            sp.getITurmaPersistente().deleteByOID(SchoolClass.class, schoolClass.getIdInternal());
        }

        return new Boolean(true);

    }

}