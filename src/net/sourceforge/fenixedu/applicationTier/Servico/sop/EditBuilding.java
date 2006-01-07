package net.sourceforge.fenixedu.applicationTier.Servico.sop;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.ExistingServiceException;
import net.sourceforge.fenixedu.domain.Campus;
import net.sourceforge.fenixedu.domain.Campus;
import net.sourceforge.fenixedu.domain.space.Building;
import net.sourceforge.fenixedu.domain.space.Building;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentBuilding;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import net.sourceforge.fenixedu.persistenceTier.places.campus.IPersistentCampus;
import pt.utl.ist.berserk.logic.serviceManager.IService;

public class EditBuilding implements IService {

    public void run(final Integer buildingID, final Integer campusID) throws ExcepcaoPersistencia, ExistingServiceException {
        final ISuportePersistente persistentSupport = PersistenceSupportFactory.getDefaultPersistenceSupport();

        final IPersistentBuilding persistentBuilding = persistentSupport.getIPersistentBuilding();
        final Building building = (Building) persistentBuilding.readByOID(Building.class, buildingID);

        final IPersistentCampus persistentCampus = persistentSupport.getIPersistentCampus();
        final Campus campus = (Campus) persistentCampus.readByOID(Campus.class, campusID);

        building.setCampus(campus);
    }

}