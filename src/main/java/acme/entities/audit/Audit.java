
package acme.entities.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}\\d{4}")
	@Column(unique = true)
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	@Pattern(regexp = ".+(;.+)*")
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	@Pattern(regexp = ".+(;.+)*")
	protected String			weakPoints;

	@Transient
	protected String			mark;

	@NotNull
	@ManyToOne
	@Valid
	protected Course			course;

}
