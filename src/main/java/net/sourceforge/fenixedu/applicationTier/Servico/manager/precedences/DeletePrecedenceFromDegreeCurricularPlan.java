package net.sourceforge.fenixedu.applicationTier.Servico.manager.precedences;


import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.precedences.Precedence;
import pt.ist.fenixWebFramework.security.accessControl.Checked;
import pt.ist.fenixWebFramework.services.Service;

public class DeletePrecedenceFromDegreeCurricularPlan {

    @Checked("RolePredicates.MANAGER_OR_OPERATOR_PREDICATE")
    @Service
    public static void run(Integer precedenceID) throws FenixServiceException {
        Precedence precedence = RootDomainObject.getInstance().readPrecedenceByOID(precedenceID);

        if (precedence != null) {
            precedence.delete();
        }
    }
}