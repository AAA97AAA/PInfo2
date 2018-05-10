package services.demo;

import java.io.Serializable;

import javax.ejb.Local;

import dom.tags.MainTag;
import dom.tags.SecondaryTag;

@Local
public interface DemoServices extends Serializable {
	
	public void addStuffToDatabase();
	
	public MainTag storeMainTag(MainTag tag);
	
	public MainTag getTag(long id);
	
	public SecondaryTag storeSecondaryTag(SecondaryTag tag);
}
