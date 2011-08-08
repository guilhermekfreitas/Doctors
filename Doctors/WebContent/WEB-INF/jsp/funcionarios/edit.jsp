<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Funcionário</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>

</head>
<body>
	<c:import url="../header.jsp"></c:import>

	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>
	<form action="<c:url value="/funcionarios/${funcionario.id}"/>" method="post">
		<fieldset>
			<legend>Editar Funcionário:</legend>

			<input id="id3" type="hidden" name="funcionario.perfil.id"	value="${funcionario.perfil.id}" />

			<label for="nome">Nome:</label> <input id="nome" type="text"
				name="funcionario.nome" value="${funcionario.nome}" /><br /> 
			
			<label for="dataAdmissao">Data de Admissão:</label> <input id="dataAdmissao" type="text"
				name="funcionario.dataAdmissao" value="${funcionario.dataAdmissao}"/><br /> 
			
			<label for="matricula">Número de Matrícula:</label> <input id="matricula" type="text" 
				name="funcionario.matricula" value="${funcionario.matricula}"/><br />
				
			<label for="endereco">Endereço:</label> <input id="endereco" type="text" 
				name="funcionario.endereco" value="${funcionario.endereco}"/><br /> 
				
			<label for="cpf">CPF:</label> <input id="cpf" type="text" 
				name="funcionario.cpf" value="${funcionario.cpf}"/><br /> 
			
			<label for="telefone">Telefone:</label> <input id="telefone" type="text" 
				name="funcionario.telefone" value="${funcionario.telefone}"/><br /> 
			
			<label for="email">Email:</label> <input id="email" type="text"
				name="funcionario.email" value="${funcionario.email}"/><br /> 
				
			<label for="dataNasc">Data de Nascimento:</label> <input id="dataNasc" type="text"
				name="funcionario.dataDeNascimento" value="<fmt:formatDate value="${funcionario.dataDeNascimento}" pattern="dd/MM/yyyy"/>" /> <br /> 
				
			<label for="login">Login:</label> <input id="login" type="text"
				name="funcionario.perfil.login" value="${funcionario.perfil.login}" disabled="disabled" /><br />
				
			<label for="senha">Senha:</label> <input id="senha" type="text"
				name="funcionario.perfil.senha" value="${funcionario.perfil.senha}" disabled="disabled" /><br />

			<button type="submit" name="_method" value="PUT">Editar</button>
			<a href="./"><button id="cancelar" type="submit" >Cancelar</button></a>
		</fieldset>
	</form>
</body>
</html>