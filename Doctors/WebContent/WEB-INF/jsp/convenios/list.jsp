<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Conv�nios</title>
</head>
<body>
	<h1> Lista de Conv�nios </h1>
	
	<c:forEach items="${convenios}" var="convenio">
		${convenio.nome} - ${convenio.tipo} <br/>
	</c:forEach>
	<a href="../convenios/">Voltar p�gina inicial do m�dulo</a><br/>
</body>
</html>