package services.content;

import java.io.Serializable;

import javax.ejb.Local;

import dom.content.Comment;

@Local
public interface CommentService extends Serializable{
	
	public Comment getComment(long id);
	
	public void addComment(Comment comment);
	
}
