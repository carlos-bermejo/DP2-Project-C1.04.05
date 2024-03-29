
package acme.features.auditor.auditrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

	private static final String				ENDDATE		= "endDate";
	private static final String				INITDATE	= "initDate";

	@Autowired
	protected AuditorAuditRecordRepository	repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(auditRecordId);
		status = auditRecord != null && auditRecord.getAudit().isDraftMode() && super.getRequest().getPrincipal().hasRole(auditRecord.getAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		super.bind(object, "subject", "assesment", "mark", AuditorAuditRecordUpdateService.INITDATE, AuditorAuditRecordUpdateService.ENDDATE, "moreInfo");
	}

	@Override
	public void validate(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		if (!super.getBuffer().getErrors().hasErrors(AuditorAuditRecordUpdateService.INITDATE) && !super.getBuffer().getErrors().hasErrors(AuditorAuditRecordUpdateService.ENDDATE))
			if (!MomentHelper.isBefore(object.getInitDate(), object.getEndDate()))
				super.state(false, AuditorAuditRecordUpdateService.ENDDATE, "auditor.audit-record.form.error.end-before-start");
			else {
				final double hours = MomentHelper.computeDuration(object.getInitDate(), object.getEndDate()).toHours();
				if (hours < 1)
					super.state(false, AuditorAuditRecordUpdateService.ENDDATE, "auditor.audit-record.form.error.duration");
			}
	}

	@Override
	public void perform(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		if (object == null)
			throw new NullPointerException();

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assesment", "mark", AuditorAuditRecordUpdateService.INITDATE, AuditorAuditRecordUpdateService.ENDDATE, "moreInfo");
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
