
package acme.features.student.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode = 0")
	List<Course> findAllPublishCourses();
}
