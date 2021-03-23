package pt.ulisboa.tecnico.socialsoftware.tutor.question.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

@DataJpaTest
class UpdateOpenAnswerQuestionTest extends SpockTest {
    def question

    def setup() {
    //TODO
    }


    def "update an open answer question"() {
    //TODO
    }

    def "update open answer question title with missing data"() {
    //TODO
    }

    def "update open answer question correct answer with missing data"() {
    //TODO
    }


    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
