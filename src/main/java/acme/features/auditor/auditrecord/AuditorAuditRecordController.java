
package acme.features.auditor.auditrecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.audit.AuditRecord;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordListService		listService;

	@Autowired
	protected AuditorAuditRecordShowService		showService;

	@Autowired
	protected AuditorAuditRecordCreateService	createService;

	@Autowired
	protected AuditorAuditRecordUpdateService	updateService;

	@Autowired
	protected AuditorAuditRecordDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

	}
}
