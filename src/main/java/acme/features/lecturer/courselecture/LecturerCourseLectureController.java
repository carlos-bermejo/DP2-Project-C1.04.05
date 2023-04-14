
package acme.features.lecturer.courselecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.CourseLecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseLectureController extends AbstractController<Lecturer, CourseLecture> {

	@Autowired
	protected LecturerCourseLectureAddLectureService addLectureService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.addLectureService);
	}
}
