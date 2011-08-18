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
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>

	<form action="<c:url value='/login'/>" method="post">
		<label for="login">Nome do Usuário:</label> <input id="login" 
			type="text" name="perfilUsuario.login"/><br >
		<label for="senha">Senha:</label> <input id="senha" 
			type="password" name="perfilUsuario.senha" /><br />
		<input type="submit" value="Logar"/><br />
	</form>
	
	<div id="mensagem">
		${message }
	</div>
	
	Não tem login? <a href="<c:url value='/cadastro'/>">Cadastre-se (Link quebrado)</a>
</body>
</html>