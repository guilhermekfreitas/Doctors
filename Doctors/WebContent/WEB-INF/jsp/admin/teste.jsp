<%@page contentType="text/html"%>
<%@page pageEncoding="iso-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=iso-8859-1" />
		<title>Security - Admin</title>
	</head>
	<body>
		<c:out value="Painel Admin - Sessão iniciada"/><br/>
		
		<a href="<c:url value='/' />" >Voltar a pagina principal</a>
		<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
	</body>
</html>