
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.offer.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	//Internal State

	@Autowired
	protected AdministratorBannerShowService	showService;

	@Autowired
	protected AdministratorBannerListService	listService;

	//Constructors


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
