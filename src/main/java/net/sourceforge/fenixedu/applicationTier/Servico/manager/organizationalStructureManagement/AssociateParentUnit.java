package net.sourceforge.fenixedu.applicationTier.Servico.manager.organizationalStructureManagement;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.domain.organizationalStructure.AccountabilityType;
import net.sourceforge.fenixedu.domain.organizationalStructure.Unit;
import pt.ist.fenixWebFramework.security.accessControl.Checked;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

public class AssociateParentUnit {

    @Checked("RolePredicates.MANAGER_PREDICATE")
    @Atomic
    public static void run(String unitID, String parentUnitID, AccountabilityType accountabilityType)
            throws FenixServiceException {

        Unit parentUnit = getParentUnit(parentUnitID);
        Unit unit = (Unit) FenixFramework.getDomainObject(unitID);

        if (unit == null) {
            throw new FenixServiceException("error.inexistent.unit");
        }
        unit.addParentUnit(parentUnit, accountabilityType);
    }

    private static Unit getParentUnit(String parentUnitID) {
        Unit parentUnit = null;
        if (parentUnitID != null) {
            parentUnit = (Unit) FenixFramework.getDomainObject(parentUnitID);
        }
        return parentUnit;
    }
}