package com.exam.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	// add new question
	@PostMapping("/{qId}")
	public ResponseEntity<Question> add(@PathVariable("qId") Long qId, @RequestBody Question question){
		System.out.println("-----------------------------**********888");
		System.out.println(qId);
		Quiz quiz = new Quiz();
		quiz.setqId(qId);
		question.setQuiz(quiz);
		return ResponseEntity.ok(this.questionService.addQuestion(question));
	}
	
	// update question
	@PutMapping("/")
	public ResponseEntity<Question> update(@RequestBody Question question){
		return ResponseEntity.ok(this.questionService.updateQuestion(question));
	}
	
	// get all questions of a particular quiz
	@GetMapping("/quiz/{qId}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qId") Long qId){
		Quiz quiz = this.quizService.getQuiz(qId);
		Set<Question> questions =  quiz.getQuestion();
		
		for (Question question : questions) {
			question.setAnswer(null);
		}
		
		List list = new ArrayList(questions);
		
		
		
		
		if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
		}
		
		Collections.shuffle(list);
		return ResponseEntity.ok(list);	
	}
	
	// get all questions of a particular quiz
	@GetMapping("/quiz/all/{qId}")
	public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qId") Long qId){
		Quiz quiz = new Quiz();
		quiz.setqId(qId);
		Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionsOfQuiz);
	}
	
	
	
	//get single question
	@GetMapping("/{quesId}")
	public Question getQuestion(@PathVariable("quesId") Long quesId) {
		return this.questionService.getQuestion(quesId);
	}
	
	// delete 
	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		this.questionService.deleteQuestion(quesId);
	}
	
	// evaluate quiz
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
		 Integer marksGot=0;
		 Integer correctAnswers=0;
		 Integer attempted=0;
		 
		 
		for (Question q: questions){
			Question question = this.questionService.getQuestion(q.getQuesId());
			if(q.getGivenAnswer() != null) {
				attempted++;
			}
			if(question.getAnswer().equals(q.getGivenAnswer())) {
				correctAnswers++;
			}
		}
		marksGot = correctAnswers;
		
		System.out.println("Correct: " + correctAnswers);
		System.out.println("Attempted: " + attempted);
		System.out.println("Marks: " + marksGot);
		
		Map<Object, Object> map = Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
		return ResponseEntity.ok(map);
	}
	
	
}
























