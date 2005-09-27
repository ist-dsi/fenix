package net.sourceforge.fenixedu.applicationTier.Servico;

import java.io.Serializable;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.IPerson;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPessoaPersistente;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import net.sourceforge.fenixedu.util.kerberos.UpdateKerberos;
import pt.utl.ist.berserk.logic.serviceManager.IService;

public class AuthenticateKerberos extends Authenticate implements IService, Serializable {

    public IUserView run(final String username, final String password, final String application,
            final String requestURL) throws ExcepcaoPersistencia, ExcepcaoAutenticacao {

    	final Authenticate authenticate = new Authenticate();
    	final IUserView userView = authenticate.run(username, password, application, requestURL);

    	if (userView != null) {
    		updateKerberos(userView);
    	}

    	return userView;
    }

	private void updateKerberos(final IUserView userView) throws ExcepcaoPersistencia {
    	final ISuportePersistente persistenceSupport = PersistenceSupportFactory.getDefaultPersistenceSupport();
    	final IPessoaPersistente persistentPerson = persistenceSupport.getIPessoaPersistente();

    	final IPerson person = (IPerson) persistentPerson.readByOID(Person.class, userView.getPersonOID());

    	if (person == null) {
    		throw new Error("No person found for provided UserView. This should not be possible!");
    	}

        if(person.getIstUsername() != null && !person.getIsPassInKerberos()) {
        	try {
	        	UpdateKerberos.createUser(person.getIstUsername(), person.getPassword());
	        	person.setIsPassInKerberos(true);
        	} catch(Exception e) {
        		//for now, do nothing
        	}
        }
	}

}