package pt.ulisboa.tecnico.socialsoftware.tutor.question.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest

@DataJpaTest
class RemoveOpenAnswerQuestionTest extends SpockTest {
    def question

    def setup() {
    //TODO
    }

    def "remove an open answer question"() {
    //TODO
    }

    def "remove an open answer question used in a quiz"() {
    //TODO
    }

    def "remove an open answer question that has topics"() {
    //TODO
    }

    def "remove an open answer question that was submitted"() {
    //TODO
    }

    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}
