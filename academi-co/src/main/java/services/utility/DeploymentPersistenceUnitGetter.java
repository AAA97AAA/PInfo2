package services.utility;

import javax.ejb.Stateless;

/**
 * Injectable class to get the deployment persistence unit name
 * 
 * @author kaikoveritch
 *
 */
@Stateless
public class DeploymentPersistenceUnitGetter implements PersistenceUnitGetter {

	@Override
	public String getPersistenceUnitName() {
		return "academi-co";
	}

}
