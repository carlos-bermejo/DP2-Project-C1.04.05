/*
 * EmployerJobDeleteService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialsession;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.enumerates.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Tutorial tutorial;
		Assistant assistant;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialByTutorialSessionId(id);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant) && assistant.getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionsById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialByTutorialSessionId(super.getRequest().getData("id", int.class));

		super.bind(object, "title", "abstrac", "goals", "sessionNature", "startDate", "finishDate");
		object.setTutorial(tutorial);
	}

	@Override
	public void validate(final TutorialSession object) {
		if (!super.getBuffer().getErrors().hasErrors("tutorial")) {
			Tutorial tutorial;

			tutorial = this.repository.findOneTutorialByTutorialSessionId(super.getRequest().getData("id", int.class));

			super.state(tutorial.isDraftMode(), "tutorial", "assistant.tutorialsession.form.error.tutorial");
		}
		if (!super.getBuffer().getErrors().hasErrors("title")) {
			int numOfSessions;

			numOfSessions = this.repository.findTutorialSessionsByTutorialId(object.getTutorial().getId()).size();

			super.state(numOfSessions > 1, "title", "assistant.tutorialsession.form.error.title");
		}
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;

		tutorial = this.repository.findOneTutorialByTutorialSessionId(object.getId());
		tutorial = this.getUpdatedTutorial(tutorial, object);

		this.repository.save(tutorial);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;
		SelectChoices choices;

		tutorial = object.getTutorial();
		choices = SelectChoices.from(Nature.class, object.getSessionNature());

		tuple = super.unbind(object, "title", "abstrac", "goals", "startDate", "finishDate");
		tuple.put("natures", choices);
		tuple.put("sessionNature", choices.getSelected().getKey());
		tuple.put("tutorialId", tutorial.getId());
		tuple.put("draftMode", tutorial.isDraftMode());

		super.getResponse().setData(tuple);
	}

	private Tutorial getUpdatedTutorial(final Tutorial tutorial, final TutorialSession object) {
		TutorialSession oldSession;
		double oldHours;
		Date oldStart;
		Date oldFinish;

		oldSession = this.repository.findOneTutorialSessionsById(object.getId());
		oldStart = oldSession.getStartDate();
		oldFinish = oldSession.getFinishDate();
		oldHours = (double) MomentHelper.computeDuration(oldStart, oldFinish).toMinutes() / 60;

		tutorial.setEstimatedHours(tutorial.getEstimatedHours() - oldHours);

		return tutorial;
	}

}