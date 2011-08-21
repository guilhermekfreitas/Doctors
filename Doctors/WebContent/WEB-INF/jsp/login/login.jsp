<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Página de Login</title>
</head>
<body>
	<c:if test="${param.error eq 'invalido'}">
	     <c:out value="Usuário e/ou senha inválido(s)" />
	</c:if>
	<c:if test="${!empty error}">
	     <c:out value="Usuário e/ou senha inválido(s)" />
	</c:if>
	<form name="f" action="<c:url value="/logar"/>" method="POST">
	  <table>
	    <tr>
	        <td><c:out value="Usuário:"/> </td>
	        <td><input type='text' name='j_username'/></td>
	    </tr>
	    <tr>
	        <td><c:out value="Senha:"/></td>
	        <td><input type='password' name='j_password'></td>
	    </tr>
	    <tr><td colspan='2'><input name="submit" type="submit"></td>
	    </tr>
	    <tr>
	        <td colspan='2'> <input name="reset" type="reset"> </td>
	    </tr>
	  </table>
	</form>
</body>
</html>