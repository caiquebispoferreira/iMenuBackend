<!-- 
Purpose: 
Author: Ashik Lochna, Nazif Sahim, Wahida Hossain, Caique Ferreira, Irisi Meko
Date: April 09, 2021
Version: iMenu v6.0
Parameters: 
-->

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${contextPath}/resources/css/style.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Syncopate:wght@700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;700&display=swap"
	rel="stylesheet">


<div class="center">
	<div style="justify-content: center;" class="col-md-12 order-md-2">
		<form action="${action}" method="post">
			<input type="hidden" name="id" value="${object.id}" />
			<div class="mb-3">
				<label for="name">Name</label>
				<c:if test="${readonly}">
					<input id="name" type="text" name="name" value="${object.name}"
						class="form-control" readonly>
				</c:if>
				<c:if test="${!readonly}">
					<input id="name" type="text" name="name" value="${object.name}"
						placeholder="Please enter your Name" class="form-control">
				</c:if>
			</div>
			<div class="mb-3">
				<label for="username">User name</label>
				<c:if test="${readonly}">
					<input id="username" type="text" name="username"
						value="${object.username}" class="form-control" readonly>
				</c:if>
				<c:if test="${!readonly}">
					<input id="username" type="text" name="username"
						value="${object.username}"
						placeholder="Please enter your username" class="form-control">
				</c:if>
			</div>
			<div class="mb-3">
				<label for="role">Role</label> <select id="role" name="role"
					class="custom-select d-block w-100"required ${readonly ? 'disabled' : ''}>
					<option value="">Select an option</option>
					<option value="ADMINISTRATOR"
						${object.role == 'ADMINISTRATOR' ? 'selected' : ''}>Administrator</option>
					<option value="STAFF" ${object.role == 'STAFF' ? 'selected' : ''}>Staff</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="password">Password</label>
				<c:if test="${readonly}">
					<input id="password" type="password" name="password"
						value="${object.password}" class="form-control" readonly>
				</c:if>
				<c:if test="${!readonly}">
					<input id="password" type="password" name="password"
						value="${object.password}" placeholder="Please enter your name"
						class="form-control">
				</c:if>
			</div>

			<hr class="mb-4">
			<button class="btn btn-light addUserBtn" type="submit">Submit</button>
		</form>
	</div>
</div>











