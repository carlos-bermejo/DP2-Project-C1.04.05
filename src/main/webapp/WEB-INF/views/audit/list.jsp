<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.audit.list.label.conclusion" path="title" width="40%"/>
	<acme:list-column code="auditor.audit.list.label.strongPoints" path="strongPoints" width="10%"/>	
	<acme:list-column code="auditor.audit.list.label.weakPoints" path="weakPoints" width="40%"/>	
		<acme:list-column code="auditor.audit.list.label.mark" path="mark" width="40%"/>
	<acme:list-column code="auditor.audit.list.label.draftMode" path="draftMode" width="40%"/>		
</acme:list>
