
package acme.entities.enrolment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}\\d{3}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	protected String			holder;

	protected Integer			lowerNibble;

	// Derived attributes -----------------------------------------------------

	@Digits(integer = 3, fraction = 2)
	public double				workTime; //This property must be computed in the service

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Student			student;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;
}
