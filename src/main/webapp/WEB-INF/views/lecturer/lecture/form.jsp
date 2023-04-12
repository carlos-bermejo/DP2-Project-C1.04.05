
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.form.label.lectureAbstract" path="lectureAbstract"/>
	<acme:input-textbox code="lecturer.lecture.form.label.nature" path="nature"/>
	<acme:input-money code="lecturer.lecture.form.label.retailPrice" path="body"/>
	<acme:input-url code="lecturer.lecture.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textbox code="lecturer.lecture.form.label.lecturer" path="lecturer"/>
</acme:form>
