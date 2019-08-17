package pt.ulisboa.tecnico.socialsoftware.tutor.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    @Query(value = "select * from topics t where t.name = :name", nativeQuery = true)
    Topic findByName(String name);
}