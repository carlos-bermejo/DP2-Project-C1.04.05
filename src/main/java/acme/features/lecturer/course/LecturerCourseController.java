
package acme.features.lecturer.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseController extends AbstractController<Lecturer, Course> {

	@Autowired
	protected LecturerCourseListMineService		listService;

	@Autowired
	protected LecturerCourseShowService		showService;

	@Autowired
	protected LecturerCourseUpdateService	updateService;

	@Autowired
	protected LecturerCourseDeleteService	deleteService;

	@Autowired
	protected LecturerCourseCreateService	createService;

	@Autowired
	protected LecturerCoursePublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
