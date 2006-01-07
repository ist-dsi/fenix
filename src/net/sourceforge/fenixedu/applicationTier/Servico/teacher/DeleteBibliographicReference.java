package net.sourceforge.fenixedu.applicationTier.Servico.teacher;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.InvalidArgumentsServiceException;
import net.sourceforge.fenixedu.domain.BibliographicReference;
import net.sourceforge.fenixedu.domain.BibliographicReference;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentBibliographicReference;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import pt.utl.ist.berserk.logic.serviceManager.IService;

/**
 * @author Fernanda Quit�rio
 * 
 */
public class DeleteBibliographicReference implements IService {

    public boolean run(Integer bibliographicReferenceOID) throws FenixServiceException,
            ExcepcaoPersistencia {

        ISuportePersistente persistentSupport = PersistenceSupportFactory.getDefaultPersistenceSupport();
        IPersistentBibliographicReference persistentBibliographicReference = persistentSupport
                .getIPersistentBibliographicReference();

        BibliographicReference bibliographicReference = (BibliographicReference) persistentBibliographicReference
                .readByOID(BibliographicReference.class, bibliographicReferenceOID);

        if (bibliographicReference == null) {
            throw new InvalidArgumentsServiceException();
        }
        bibliographicReference.delete();
        persistentBibliographicReference.deleteByOID(BibliographicReference.class,
                bibliographicReference.getIdInternal());

        return true;
    }
}