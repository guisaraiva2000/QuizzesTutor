package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservices

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.QuizAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.OpenAnswerStatementAnswerDetailsDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementAnswerDto
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.dto.StatementQuizDto
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.OpenAnswerQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.Quiz
import pt.ulisboa.tecnico.socialsoftware.tutor.quiz.domain.QuizQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User
import pt.ulisboa.tecnico.socialsoftware.tutor.utils.DateHandler


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeacherSeeQuizResultsWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port
    def mapper
    def teacher
    def student
    def response
    def quizQuestion
    def quizAnswer
    def date
    def quiz

    def setup() {
        restClient = new RESTClient("http://localhost:" + port)
        mapper = new ObjectMapper()
        createExternalCourseAndExecution()
        teacher = new User(USER_1_NAME, USER_1_EMAIL, USER_1_EMAIL,
                User.Role.TEACHER, false, AuthUser.Type.TECNICO)
        teacher.authUser.setPassword(passwordEncoder.encode(USER_1_PASSWORD))
        teacher.addCourse(externalCourseExecution)
        externalCourseExecution.addUser(teacher)
        userRepository.save(teacher)

        student = new User(USER_2_NAME, USER_2_EMAIL, USER_2_EMAIL,
                User.Role.STUDENT, false, AuthUser.Type.EXTERNAL)
        student.authUser.setPassword(passwordEncoder.encode(USER_2_PASSWORD))
        student.addCourse(externalCourseExecution)
        externalCourseExecution.addUser(student)
        userRepository.save(student)
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        quiz = new Quiz()
        quiz.setKey(1)
        quiz.setTitle("Quiz Title")
        quiz.setType(Quiz.QuizType.PROPOSED.toString())
        quiz.setCourseExecution(externalCourseExecution)
        quiz.setAvailableDate(DateHandler.now())
        quizRepository.save(quiz)

        def question = new Question()
        question.setKey(1)
        question.setTitle("Question Title")
        question.setCourse(externalCourse)
        def questionDetails = new OpenAnswerQuestion()
        question.setQuestionDetails(questionDetails)
        questionDetails.setCorrectAnswer(OPEN_QUESTION_1_ANSWER)
        questionDetailsRepository.save(questionDetails)
        questionRepository.save(question)

        quizQuestion = new QuizQuestion(quiz, question, 0)
        quizQuestionRepository.save(quizQuestion)

        date = DateHandler.now()

        quizAnswer = new QuizAnswer(student, quiz)
        quizAnswerRepository.save(quizAnswer)

        quiz.setConclusionDate(DateHandler.now().plusDays(2))
        def statementQuizDto = new StatementQuizDto()
        statementQuizDto.id = quiz.getId()
        statementQuizDto.quizAnswerId = quizAnswer.getId()
        def statementAnswerDto = new StatementAnswerDto()
        def openAnswerDto = new OpenAnswerStatementAnswerDetailsDto()
        openAnswerDto.setAnswer(OPEN_QUESTION_1_ANSWER)
        statementAnswerDto.setAnswerDetails(openAnswerDto)
        statementAnswerDto.setTimeTaken(100)
        statementAnswerDto.setQuestionAnswerId(quizAnswer.getQuestionAnswers().get(0).getId())
        statementQuizDto.getAnswers().add(statementAnswerDto)

        answerService.concludeQuiz(statementQuizDto)

    }

    def "teacher see quiz results"() {
        given:
        createdUserLogin(USER_1_EMAIL, USER_1_PASSWORD)

        when:
        response = restClient.get(
                path: "/quizzes/" + quiz.getId() + "/answers",
                requestContentType: 'application/json'
        )
        then:
        response != null
        response.status == 200

        def quizAnswersDtos = response.data
        quizAnswersDtos.correctSequence.get(0) == OPEN_QUESTION_1_ANSWER
        def quizAnswers = quizAnswersDtos.quizAnswers.get(0)
        quizAnswers.name == USER_2_NAME
        quizAnswers.username == USER_2_EMAIL
        quizAnswers.answerDate != null
        quizAnswers.questionAnswers != null
    }

    def "student cant see quiz results"() {
        createdUserLogin(USER_2_EMAIL, USER_2_PASSWORD)

        when:
        response = restClient.get(
                path: "/quizzes/" + quiz.getId() + "/answers",
                requestContentType: 'application/json'
        )

        then: "the request returns 403"
        def error = thrown(HttpResponseException)
        error.response.status == HttpStatus.SC_FORBIDDEN

    }

    def cleanup() {
        userRepository.deleteById(teacher.getId())
        userRepository.deleteById(student.getId())
        courseExecutionRepository.deleteById(externalCourseExecution.getId())

        courseRepository.deleteById(externalCourse.getId())
        questionRepository.deleteAll()
        quizQuestionRepository.deleteAll()
        answerDetailsRepository.deleteAll()
        questionAnswerRepository.deleteAll()
    }
}