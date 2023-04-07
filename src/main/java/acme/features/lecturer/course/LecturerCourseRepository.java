
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	public Course showCourse(int id);

	@Query("select c from Course c where c.lecturer.userAccount.id = :id and c.draftMode = false")
	public List<Course> listCoursesFromLecturer(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	public List<Lecture> listLecturesFromCourse(int id);
}
