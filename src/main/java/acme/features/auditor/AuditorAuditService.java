
package acme.features.auditor;

import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditService extends AbstractService<Auditor, Audit> {

}
