package services.search;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of a search result, with a result list (might not
 * be the full result) and a full result length.
 * 
 * @author kaikoveritch
 *
 */
public class SearchResult<T> implements Serializable {

	// Serial number (auto-generated)
	private static final long serialVersionUID = 4637796262749824157L;

	private int length;
	
	private List<T> results;
	
	
	/***** Constructors *****/

	public SearchResult() {}

	public SearchResult(int length, List<T> results) {
		this.length = length;
		this.results = results;
	}
	
	
	/***** Getters/Setters *****/

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
}
