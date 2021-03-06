package pt.ulisboa.tecnico.socialsoftware.tutor.question.webservices

import com.fasterxml.jackson.databind.ObjectMapper
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpStatus
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.auth.domain.AuthUser
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.OpenAnswerQuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.QuestionDto
import pt.ulisboa.tecnico.socialsoftware.tutor.user.domain.User


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateOpenAnswerQuestionWebServiceIT extends SpockTest {
    @LocalServerPort
    private int port
    def mapper
    def teacher
    def student
    def response

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
    }

    def "teacher create open answer question"() {
        given: "a questionDto"

        QuestionDto questionDto = new QuestionDto()
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        questionDto.getQuestionDetailsDto().setCorrectAnswer(OPEN_QUESTION_1_ANSWER)
        questionDto.getQuestionDetailsDto().setExpression(OPEN_QUESTION_1_EXPRESSION)

        when:
        response = restClient.post(
                path: '/questions/courses/' + externalCourse.getId(),
                body: mapper.writeValueAsString(questionDto),
                requestContentType: 'application/json'
        )

        then: "check the response status"
        response != null
        response.status == 200

        def question = response.data
        question.id != null
        question.title == questionDto.getTitle()
        question.content == questionDto.getContent()
        question.status == Question.Status.AVAILABLE.name()
        question.questionDetailsDto != null
        question.questionDetailsDto.correctAnswer == OPEN_QUESTION_1_ANSWER
        question.questionDetailsDto.expression == OPEN_QUESTION_1_EXPRESSION
    }

    def "student create open answer question"() {
        given: 'a student login'
        createdUserLogin(USER_2_EMAIL, USER_2_PASSWORD)

        and: 'a questionDto'
        QuestionDto questionDto = new QuestionDto()
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        questionDto.getQuestionDetailsDto().setCorrectAnswer(OPEN_QUESTION_1_ANSWER)
        questionDto.getQuestionDetailsDto().setExpression(OPEN_QUESTION_1_EXPRESSION)

        when:
        response = restClient.post(
                path: '/questions/courses/' + externalCourseExecution.getId(),
                body: mapper.writeValueAsString(questionDto),
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
    }

}