
package acme.features.auditor;

import org.springframework.stereotype.Service;

import acme.entities.audit.AuditRecord;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordService extends AbstractService<Auditor, AuditRecord> {

}
