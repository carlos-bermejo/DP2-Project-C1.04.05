
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListFromCourseService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected LecturerCourseRepository	courseRepository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Principal principal = super.getRequest().getPrincipal();
		final int id = super.getRequest().getData("masterId", int.class);
		final Course course = this.courseRepository.getCourseById(id);
		status = course.getLecturer().getUserAccount().getId() == principal.getAccountId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int courseId = super.getRequest().getData("masterId", int.class);
		final List<Lecture> object = this.repository.getLecturesFromCourse(courseId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "title", "lectureAbstract", "nature");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		tuple.put("lecturerId", lecturer.getId());
		super.getResponse().setData(tuple);
	}
}
