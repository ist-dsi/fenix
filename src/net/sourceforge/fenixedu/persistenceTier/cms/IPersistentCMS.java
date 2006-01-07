/**
 * 
 */
package net.sourceforge.fenixedu.persistenceTier.cms;

import net.sourceforge.fenixedu.domain.cms.Cms;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;

/**
 * @author <a href="mailto:goncalo@ist.utl.pt">Goncalo Luiz</a>
 * <br/>
 * <br/>
 * <br/>
 * Created on 12:16:20,25/Out/2005
 * @version $Id$
 */
public interface IPersistentCMS
{
	Cms readFenixCMS() throws ExcepcaoPersistencia;
	
	Cms readCmsByName(String name) throws ExcepcaoPersistencia;
}
