package com.exam.service;

import java.util.Set;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;

public interface QuestionService {
	public Question addQuestion(Question question);

	public Question updateQuestion(Question question);

	public Set<Question> getQuestions();
	
	public Set<Question> getQuestionsOfQuiz(Quiz quiz);
	
	public Question getQuestion(Long questionId);
	
	public void deleteQuestion(Long questionId);
}
