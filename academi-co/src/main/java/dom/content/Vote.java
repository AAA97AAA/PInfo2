package dom.content;

import java.io.Serializable;

/**
 * Class to represent an upvote/downvote. Not stored.
 * 
 * @author kaikoveritch
 *
 */
public class Vote implements Serializable {

	// Serial number (auto-generated)
	private static final long serialVersionUID = -866172120812379381L;

	private boolean up;
	
	private long voterId;
	
	
	/***** Constructors *****/

	public Vote() {}

	public Vote(boolean up, long voterId) {
		this.up = up;
		this.voterId = voterId;
	}
	
	
	/***** Getters/Setters *****/

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public long getVoterId() {
		return voterId;
	}

	public void setVoter(long voterId) {
		this.voterId = voterId;
	}
	
}
