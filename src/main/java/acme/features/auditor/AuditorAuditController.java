
package acme.features.auditor;

import org.springframework.stereotype.Controller;

import acme.entities.audit.Audit;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorAuditController extends AbstractController<Auditor, Audit> {
}
