package services.utility;

/**
 * Interface to be injected for fetching a persistence unit name
 * dynamically
 * 
 * @author kaikoveritch
 *
 */
public interface PersistenceUnitGetter {

	public String getPersistenceUnitName();
}
