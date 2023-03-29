
package acme.features.any.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseListService extends AbstractService<Any, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyCourseRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final List<Course> object = this.repository.listCourses();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "courseAbstract", "nature", "retailPrice", "moreInfo", "lecturer");

		super.getResponse().setData(tuple);
	}
}