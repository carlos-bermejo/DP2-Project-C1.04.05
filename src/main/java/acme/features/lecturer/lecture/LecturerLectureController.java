
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureListAllService			listAllService;

	@Autowired
	protected LecturerLectureListFromCourseService	listFromCourseService;

	@Autowired
	protected LecturerLectureShowService			showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("listAll", "list", this.listAllService);
		super.addCustomCommand("listFromCourse", "list", this.listFromCourseService);
	}
}
