
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			name;

	@NotBlank
	@Length(min = 1, max = 25)
	protected String			vatNumber;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			summary;

	@URL
	protected String			moreInfo;

}
