
package acme.features.lecturer.courselecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.userAccount.id = :id and l not in (select cl.lecture from CourseLecture cl where cl.course.id = :courseId)")
	public List<Lecture> getAllLecturesNotInCourseFromLecturer(int id, int courseId);
}
