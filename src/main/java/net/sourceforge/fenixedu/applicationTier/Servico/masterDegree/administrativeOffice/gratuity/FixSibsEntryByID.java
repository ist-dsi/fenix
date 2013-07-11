package net.sourceforge.fenixedu.applicationTier.Servico.masterDegree.administrativeOffice.gratuity;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.domain.gratuity.SibsPaymentStatus;
import net.sourceforge.fenixedu.domain.gratuity.masterDegree.SibsPaymentFileEntry;
import pt.ist.fenixWebFramework.security.accessControl.Checked;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

/**
 * @author <a href="mailto:sana@ist.utl.pt">Shezad Anavarali </a>
 * @author <a href="mailto:naat@ist.utl.pt">Nadir Tarmahomed </a>
 * 
 */
public class FixSibsEntryByID {

    @Checked("RolePredicates.MASTER_DEGREE_ADMINISTRATIVE_OFFICE_PREDICATE")
    @Atomic
    public static void run(String sibsEntryId) throws FenixServiceException {
        SibsPaymentFileEntry sibsPaymentFileEntry = FenixFramework.getDomainObject(sibsEntryId);
        if (sibsPaymentFileEntry == null) {
            throw new FenixServiceException();
        }

        sibsPaymentFileEntry.setPaymentStatus(SibsPaymentStatus.PROCESSED_PAYMENT);
    }

}