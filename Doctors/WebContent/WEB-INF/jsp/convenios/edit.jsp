<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>

</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>
	<form action="alterar">
		<fieldset>
			<legend>Editar Convênio:</legend>

			<input id="id" type="hidden" name="convenio.id"
				value="${convenio.id}" /> <label for="nome">Nome:</label> <input
				id="nome" type="text" name="convenio.nome" value="${convenio.nome}" />

			<button id="editar" type="submit">Editar</button>
		</fieldset>
	</form>
</body>
</html>