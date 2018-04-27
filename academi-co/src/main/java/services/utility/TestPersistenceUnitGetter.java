package services.utility;

import javax.ejb.Stateless;

/**
 * Injectable class to get the test persistence unit name
 * 
 * @author kaikoveritch
 *
 */
@Stateless
public class TestPersistenceUnitGetter implements PersistenceUnitGetter {

	@Override
	public String getPersistenceUnitName() {
		return "academi-co-test";
	}

}
