<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Painel do Administrador</title>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include><br />

    <c:out value="Painel Admin - Sess�o iniciada"/><br/>
    <c:out value="Seja bem vindo ${nomeUsuario}" /><br />
    
    <a href="<c:url value='/' />" >Voltar a pagina principal</a>
</body>
</html>