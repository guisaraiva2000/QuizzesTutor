package pt.ulisboa.tecnico.socialsoftware.tutor.user.domain

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.OpenAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuestionAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuizAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.dto.QuizDto

@DataJpaTest
class AddQuizOpenAnswerTest extends SpockTest {
    def user
    def quiz

    def setup() {
        createExternalCourseAndExecution()

        user = new User(USER_1_NAME, USER_1_USERNAME, USER_1_EMAIL, User.Role.STUDENT, false, AuthUser.Type.EXTERNAL)
        userRepository.save(user)

        QuizDto quizDto = new QuizDto()
        quizDto.setKey(1)
        quizDto.setTitle(QUIZ_TITLE)
        quizDto.setAvailableDate(STRING_DATE_TODAY)
        quizDto.setConclusionDate(STRING_DATE_TOMORROW)
        quizDto.setResultsDate(STRING_DATE_LATER)

        Question question = new Question()
        question.setKey(1)
        question.setCourse(externalCourse)
        question.setTitle(QUESTION_1_TITLE)
        def questionDetails = new OpenAnswerQuestion()
        questionDetails.setCorrectAnswer(OPEN_QUESTION_1_ANSWER)
        question.setQuestionDetails(questionDetails)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        QuestionDto questionDto = new QuestionDto(question)
        questionDto.setKey(1)
        questionDto.setSequence(1)

        def questions = new ArrayList()
        questions.add(questionDto)
        quizDto.setQuestions(questions)

        quiz = new Quiz(quizDto)
        quiz.setCourseExecution(externalCourseExecution);
        quizRepository.save(quiz)

        QuizQuestion quizQuestion = new QuizQuestion(quiz, question, 1)

        QuizAnswer quizAnswer = new QuizAnswer(user, quiz)
        quizAnswer.setCompleted(true)

        def questionAnswer = new QuestionAnswer(quizAnswer, quizQuestion, 1, 1)
        questionAnswer.setAnswerDetails(new OpenAnswer(questionAnswer))
    }

    def "addQuizOpenAnswer" (){
        given:
        def quizAnswer = new QuizAnswer()
        def previousNumberQuizAnswers = user.getQuizAnswers().size()

        when:
        user.addQuizAnswer(quizAnswer)

        then:
        user.getQuizAnswers().size() == previousNumberQuizAnswers + 1
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration { }
}
