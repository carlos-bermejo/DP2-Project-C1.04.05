
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

class StudentActivityShowTest extends TestHarness {

	@Test
	void test100Hacking() {
		super.signIn("student1", "student1");
		super.clickOnMenu("Enrolments", "Your Enrolments");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Workspace");
		super.clickOnListingRecord(0);
		final String currentQuery = super.getCurrentQuery().split("=")[1];
		super.signOut();

		super.checkLinkExists("Sign in");
		super.request("/student/activity/show", "id=" + currentQuery);
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/student/activity/show", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/show", "id=" + currentQuery);
		super.checkPanicExists();
		super.signOut();
	}
}
