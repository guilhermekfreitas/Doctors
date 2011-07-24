<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<form action="<c:url value="/exames"/>" method="post">
		<fieldset>
			<legend>Adicionar Exame:</legend>

			<label for="descricao">Descrição:</label> <input id="descricao" type="text"
				name="exame.descricao" />

			<button id="enviar" type="submit">Enviar</button>
		</fieldset>
	</form>
</body>
</html>