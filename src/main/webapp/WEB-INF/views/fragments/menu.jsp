<%--
- menu.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.pedgonmar2" action="https://twitch.tv/"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.fermatgom" action="https://www.youtube.com/"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.carbersor" action="https://www.youtube.com/watch?v=w-VlBV4hmcE&ab_channel=Yumirubeats%E2%99%A5slow"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.dangalmar" action="https://openai.com/"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.carzarrei" action="https://boardgamegeek.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.system-currency" action="/administrator/system-currency/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>
	
		<acme:menu-option code="master.menu.banner" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.banner.list" action="/administrator/banner/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.audit.list" action="/auditor/audit/list"/>
		</acme:menu-option>

     	<acme:menu-option code="master.menu.note" access="isAuthenticated()" action="/authenticated/note/list"/>
      	
		<acme:menu-option code="master.menu.peeps" access="hasRole('Any')" action="/any/peep/list" />
			
      	
      	<acme:menu-option code="master.menu.courses" access="hasRole('Any') && !hasRole('Student')">
      		<acme:menu-suboption code="master.menu.any.courses" action="/any/course/list"/>
			<acme:menu-suboption code="master.menu.lecturer.courses" action="/lecturer/course/list-mine" access="hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.lecturer.courseLectures" action="/lecturer/course-lecture/list" access="hasRole('Lecturer')"/>			
      	</acme:menu-option>
      	
      	<acme:menu-option code="master.menu.lectures" access="hasRole('Lecturer')" action="/lecturer/lecture/list-all" />
      	
		<acme:menu-option code="master.menu.bulletin" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.bulletin.list" action="/authenticated/bulletin/list"/>
			<acme:menu-suboption code="master.menu.bulletin.create" access="hasRole('Administrator')" action="/administrator/bulletin/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.tutorial" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.tutorial.list" action="/authenticated/tutorial/list"/>
			<acme:menu-suboption code="master.menu.tutorial.list-mine" action="/assistant/tutorial/list" access="hasRole('Assistant')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.student.enrolment.head" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.enrolment" action="/student/enrolment/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.student.course.head" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.course" action="/student/course/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.student.dashboard.head" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.dashboard" action="/student/student-dashboard/show"/>
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>