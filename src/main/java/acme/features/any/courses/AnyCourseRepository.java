
package acme.features.any.courses;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select c from Course c")
	List<Course> listCourses();

	@Query("select c from Course c where c.id = :id")
	Course showCourse(int id);

}
