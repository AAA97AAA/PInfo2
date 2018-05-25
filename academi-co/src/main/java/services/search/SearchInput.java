package services.search;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representation of a search containing the terms of the search by
 * field, or null if a specific field is not to be queried.
 * 
 * @author kaikoveritch
 *
 */
public class SearchInput implements Serializable {

	// Serial version (auto-generated)
	private static final long serialVersionUID = -6940510816836991653L;

	private String title; // Space-separated keywords to be searched in the title
	
	private String subject; // MainTag name
	
	private String language; // Tag name
	
	private String topics; // space-separated SecondaryTag names
	
	private String author; // author (User) name
	
	private LocalDateTime from; // lower bound of creation date
	
	private LocalDateTime to; // upper bound of creation date

	
	/***** Constructors *****/
	
	public SearchInput() {}

	public SearchInput(String title, String subject, String language, String topics, String author, LocalDateTime from,
			LocalDateTime to) {
		this.title = title;
		this.subject = subject;
		this.language = language;
		this.topics = topics;
		this.author = author;
		this.from = from;
		this.to = to;
	}
	
	
	/***** Getters/Setters *****/

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}
}
