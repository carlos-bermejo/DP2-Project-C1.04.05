
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
<acme:input-textbox code="lecturer.courseLecture.form.course" path="course" readonly="true"/>
<acme:input-select code="lecturer.courseLecture.form.lecture" path="lectures" choices="${lectures}"/>
<acme:submit code="lecturer.courseLecture.form.button.add" action="/lecturer/course-lecture/create"/>
</acme:form>
