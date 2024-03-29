
package acme.testing.auditor.auditrecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

class AuditorAuditRecordDeleteTest extends TestHarness {

	private String	auditPath	= null;
	private String	auditQuery	= null;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditrecord/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String auditIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List Audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(Integer.parseInt(auditIndex));

		super.checkButtonExists("Audit Records");
		super.clickOnButton("Audit Records");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);

		// Delete
		super.checkFormExists();

		if (this.auditPath == null) {
			this.auditPath = super.getCurrentPath().replaceAll("show", "delete");
			this.auditQuery = super.getCurrentQuery();
			this.auditQuery = this.auditQuery.substring(1, this.auditQuery.length());
		}

		super.clickOnSubmit("Delete");

		super.signOut();
	}

	@Test
	void test300Hacking() {
		super.signIn("student1", "student1");
		super.request(this.auditPath, this.auditQuery);
		super.checkPanicExists();

		super.signOut();
	}
}
