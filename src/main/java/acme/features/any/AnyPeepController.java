
package acme.features.any;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.message.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyPeepController extends AbstractController<Any, Peep> {

	@Autowired
	protected AnyPeepCreateService	createService;

	@Autowired
	protected AnyPeepShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("show", this.showService);
	}
}
