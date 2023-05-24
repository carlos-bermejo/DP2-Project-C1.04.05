
package acme.features.auditor.auditrecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	private static final String				MASTERID	= "masterId";

	@Autowired
	protected AuditorAuditRecordRepository	repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData(AuditorAuditRecordListService.MASTERID, int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData(AuditorAuditRecordListService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecord> objects;
		int masterId;

		masterId = super.getRequest().getData(AuditorAuditRecordListService.MASTERID, int.class);
		objects = this.repository.findManyAuditRecordsByAuditId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assesment", "mark", "initDate", "endDate", "moreInfo");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		if (objects == null)
			throw new NullPointerException();

		int masterId;
		final Audit audit;
		boolean showCreate;

		masterId = super.getRequest().getData(AuditorAuditRecordListService.MASTERID, int.class);
		audit = this.repository.findOneAuditById(masterId);
		showCreate = super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal(AuditorAuditRecordListService.MASTERID, masterId);
	}

}