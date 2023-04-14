/*
 * EmployerJobUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.courselecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.course.Lecture;
import acme.enumerates.Nature;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureAddLectureService extends AbstractService<Lecturer, CourseLecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	protected LecturerLectureRepository			lectureRepository;

	@Autowired
	protected LecturerCourseRepository			courseRepository;


	private void updateCourseNature(final Course object) {
		List<Lecture> lectures;
		lectures = this.lectureRepository.getLecturesFromCourse(object.getId());
		if (lectures.stream().map(Lecture::getNature).allMatch(n -> n == Nature.THEORETICAL))
			object.setNature(Nature.THEORETICAL);
		else if (lectures.stream().map(Lecture::getNature).allMatch(n -> n == Nature.HANDS_ON))
			object.setNature(Nature.HANDS_ON);
		else
			object.setNature(Nature.BALANCED);
		this.courseRepository.save(object);
	}


	private final String requestId = "masterId";


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData(this.requestId, int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;
		Lecturer lecturer;

		courseId = super.getRequest().getData(this.requestId, int.class);
		course = this.courseRepository.getCourseById(courseId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final CourseLecture object;
		final Course course;
		int courseId;
		courseId = super.getRequest().getData(this.requestId, int.class);

		course = this.courseRepository.getCourseById(courseId);
		object = new CourseLecture();
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		super.bind(object, "lecture");
	}

	@Override
	public void validate(final CourseLecture object) {
		//
	}

	@Override
	public void perform(final CourseLecture object) {
		int id;
		Course course;
		id = object.getCourse().getId();
		course = this.courseRepository.getCourseById(id);
		this.repository.save(object);
		this.updateCourseNature(course);
		this.courseRepository.save(course);
	}

	@Override
	public void unbind(final CourseLecture object) {
		Tuple tuple;
		int lecturerId;
		int courseId;
		SelectChoices choices;
		courseId = object.getCourse().getId();
		lecturerId = super.getRequest().getPrincipal().getAccountId();
		final List<Lecture> lectures = this.repository.getAllLecturesNotInCourseFromLecturer(lecturerId, courseId);
		choices = SelectChoices.from(lectures, "title", object.getLecture());
		tuple = new Tuple();
		tuple.put("course", object.getCourse().getTitle());
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);
		super.getResponse().setData(tuple);
	}

}
