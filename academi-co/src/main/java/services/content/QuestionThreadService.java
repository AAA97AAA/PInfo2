package services.content;

import java.io.Serializable;

import javax.ejb.Local;

import dom.content.QuestionThread;

@Local
public interface QuestionThreadService extends Serializable {
	
	public QuestionThread getQuestionThread(long id);
	
	public QuestionThread addQuestionThread(QuestionThread questionThread);
}
