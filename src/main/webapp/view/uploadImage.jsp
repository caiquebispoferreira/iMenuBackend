<!-- 
Purpose: 
Author: Ashik Lochna, Nazif Sahim, Wahida Hossain, Caique Ferreira, Irisi Meko
Date: April 09, 2021
Version: iMenu v6.0
Parameters: 
-->

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/resources/css/style.css" />
<style>
.form-control {
	color: black;
	border-width: thin;
	height: 45px;
	border-color: black;
}
</style>
<div class="center">
	<div class="col-md-9 order-md-2">
		<form enctype="multipart/form-data" action="/uploadImage"
			method="post">
			<div class="mb-3">
				<input type="hidden" name="productId" value="${object.id}" /> <input
					id="image" type="file" accept=".png" name="image"
					class="form-control">
			</div>
			<button class="btn btn-light addUserBtn" type="submit">Submit</button>
		</form>
	</div>
</div>
