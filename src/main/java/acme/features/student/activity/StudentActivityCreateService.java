
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.entities.enrolment.Enrolment;
import acme.enumerates.Nature;
import acme.features.student.enrolment.StudentEnrolmentRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository		repository;

	@Autowired
	protected StudentEnrolmentRepository	enrolmentRepository;

	// AbstractService<Authenticated, Activity> ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("enrolmentId", int.class);

		if (status) {
			final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
			final Enrolment enrolment = this.repository.findOneEnrolmentById(enrolmentId);
			status = !enrolment.isDraftMode();
		}

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		Enrolment enrolment;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		object = new Activity();
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "*", "Fecha final debe estar después de fecha inicial");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.updateWorkTime(object);

		this.repository.save(object);
	}

	private void updateWorkTime(final Activity object) {
		Enrolment enrolment;
		int enrolmentId;
		long horas;
		double minutosEnPorcentaje;

		horas = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() / 60;
		minutosEnPorcentaje = (double) (MomentHelper.computeDuration(object.getStartDate(), object.getEndDate()).toMinutes() - horas * 60) / 60;
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		final double newWorkTime = Math.round((enrolment.getWorkTime() + horas + minutosEnPorcentaje) * 100.0) / 100.0;
		enrolment.setWorkTime(newWorkTime);
	}

	@Override
	public void unbind(final Activity object) {
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(Nature.class, object.getType());

		tuple = super.unbind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");
		tuple.put("types", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
