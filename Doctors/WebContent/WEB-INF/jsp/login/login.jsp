<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doctor's - Login</title>
</head>
<body>
	<c:import url="../header.jsp"></c:import>

	<form action="<c:url value='/login'/>" method="post">
		Nome do usuário: <input type="text" name="pessoa.login"/>
		Senha: <input type="password" name="pessoa.senha" />
		<input type="submit" value="Logar"/>
	</form>
	
	Não tem login? <a href="<c:url value='/cadastro'/>">Cadastre-se (Link quebrado)</a>
</body>
</html>