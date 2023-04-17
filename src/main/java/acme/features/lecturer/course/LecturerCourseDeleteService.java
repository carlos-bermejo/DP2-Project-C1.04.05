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

package acme.features.lecturer.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.features.lecturer.courselecture.LecturerCourseLectureRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository			repository;

	@Autowired
	protected LecturerCourseLectureRepository	relationsRepository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.getCourseById(masterId);
		lecturer = course.getLecturer();
		status = course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.getCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		super.bind(object, "code", "title", "courseAbstract", "retailPrice", "moreInfo");
	}

	@Override
	public void validate(final Course object) {
		//No custom constraints to implement
	}

	@Override
	public void perform(final Course object) {
		int courseId;
		final List<CourseLecture> relations;
		courseId = super.getRequest().getData("id", int.class);
		relations = this.relationsRepository.getRelationsFromCourseId(courseId);

		this.relationsRepository.deleteAll(relations);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "code", "title", "courseAbstract", "retailPrice", "moreInfo");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		super.getResponse().setData(tuple);
	}

}
