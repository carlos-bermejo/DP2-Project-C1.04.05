
package acme.features.administrator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.SystemCurrency;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

public class AdministratorSystemCurrencyController extends AbstractController<Administrator, SystemCurrency> {

	@Autowired
	protected AdministratorSystemCurrencyShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
