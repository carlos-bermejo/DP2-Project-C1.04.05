
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	public Lecture showLecture(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.lecturer.userAccount.id = :id")
	public List<Lecture> listAllLecturesFromLecturer(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	public List<Lecture> listLecturesFromCourse(int id);
}
