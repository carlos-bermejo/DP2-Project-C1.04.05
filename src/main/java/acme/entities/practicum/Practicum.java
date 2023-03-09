
package acme.entities.practicum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
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
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstrac;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	protected boolean			draftMode;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@Transient
	@Digits(integer = 4, fraction = 2)
	protected Double			sessionsTotalOfHours;

	@Transient
	@Digits(integer = 4, fraction = 2)
	protected double			sessionsTotalOfHoursPlusTenPercent;

	@Transient
	@Digits(integer = 4, fraction = 2)
	protected double			sessionsTotalOfHoursMinusTenPercent;

	@Transient
	@Digits(integer = 4, fraction = 2)
	protected double			practicaPeriodLength;

}
