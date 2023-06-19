<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<div class= "row">
	<div class="col-md">
		<p>
			<acme:message code="company.addendum.list.disclaimer"/>
		</p>
	</div>
</div>

<acme:list>
	<acme:list-column code="company.session.list.label.startingDate" path="startingDate"/>
	<acme:list-column code="company.session.list.label.endingDate" path="endingDate"/>
	<acme:list-column code="company.session.list.label.title" path="title"/>
	<acme:list-column code="company.session.list.label.addendum" path="addendum"/>
</acme:list>

<acme:button test="${showCreateAddendum}" code="company.session.form.button.create-addendum" action="/company/practicum-session/create-addendum?practicumId=${practicumId}"/>
<acme:button test="${showCreate}" code="company.session.form.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>
