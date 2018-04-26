package services.content;

import java.io.Serializable;

import javax.ejb.Local;
import javax.persistence.EntityManagerFactory;

import dom.content.QuestionThread;

@Local
public interface QuestionThreadService extends Serializable {
	
	public QuestionThread getQuestionThread(long id);
	
	public void addQuestionThread(QuestionThread questionThread);

	EntityManagerFactory getEmf();

	
}
