<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code"/>	
	<acme:list-column code="auditor.audit.list.label.mark" path="mark"/>
	<acme:list-column code="auditor.audit.list.label.draftMode" path="draftMode"/>

	<acme:list-column code="auditor.audit.list.label.conclusion" path="conclusion"/>	
	<acme:list-column code="auditor.audit.list.label.strongPoints" path="strongPoints"/>
	<acme:list-column code="auditor.audit.list.label.weakPoints" path="weakPoints"/>			
</acme:list>