
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	//Internal State

	@Autowired
	protected AdministratorBannerRepository repository;

	//AbstractService Interface


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);
		object.setLastModified(MomentHelper.getCurrentMoment());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {

		super.bind(object, "displayStart", "displayFinish", "slogan", "picture", "target");
		object.setLastModified(MomentHelper.getCurrentMoment());
	}

	@Override
	public void validate(final Banner object) {

		if (!super.getBuffer().getErrors().hasErrors("displayFinish")) {
			final Date start = object.getDisplayStart();
			final Date finish = object.getDisplayFinish();
			super.state(MomentHelper.isLongEnough(start, finish, 7, ChronoUnit.DAYS), "displayFinish", "administrator.banner.error.short-display-period");
		}

	}

	@Override
	public void perform(final Banner object) {

		object.setLastModified(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {

		Tuple tuple;

		tuple = super.unbind(object, "displayStart", "displayFinish", "slogan", "picture", "target");
		tuple.put("lastModified", MomentHelper.getCurrentMoment());

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
