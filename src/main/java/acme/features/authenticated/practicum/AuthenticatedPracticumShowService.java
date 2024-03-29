
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	private AuthenticatedPracticumRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class) && practicum != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Practicum object) {

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "practicumAbstract", "goals", "startingDate", "endingDate", "estimatedTotalTime", "practicaPeriodLength");
		tuple.put("companyName", object.getCompany().getName());
		tuple.put("companyVatNumber", object.getCompany().getVatNumber());
		tuple.put("companySummary", object.getCompany().getSummary());
		tuple.put("companyMoreInfo", object.getCompany().getMoreInfo());

		super.getResponse().setData(tuple);
	}
}
