
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Principal principal = super.getRequest().getPrincipal();
		final int id = super.getRequest().getData("id", int.class);
		final Lecture lecture = this.repository.showLecture(id);
		status = lecture.getLecturer().getUserAccount().getId() == principal.getAccountId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Lecture object = this.repository.showLecture(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "title", "lectureAbstract", "nature", "body", "moreInfo");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		super.getResponse().setData(tuple);
	}
}
