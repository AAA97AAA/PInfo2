package services.search;

import java.io.Serializable;

import javax.ejb.Local;

import dom.content.QuestionThread;

/**
 * Search services definition.
 * 
 * @author kaikoveritch
 *
 */
@Local
public interface SearchService extends Serializable {

	public void initIndex() throws InterruptedException;
	
	public SearchResult<QuestionThread> search(SearchInput input, int from, int size);
}
