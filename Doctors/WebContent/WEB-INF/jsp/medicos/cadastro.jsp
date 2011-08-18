<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar novo Médico:</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>

	<form id="form" name="form" action="<c:url value="/medicos"/>" method="post">
		<fieldset>
			<legend>Adicionar Médico:</legend>

			<label for="nome">Nome:</label> <input id="nome" type="text"
				name="medico.nome" value="${medico.nome}" /><br /> 
			
			<label for="crm">CRM:</label> <input id="crm" type="text"
				name="medico.crm" value="${medico.crm}"/><br /> 
			
			<label for="ufRegistro">UF de Registro:</label> <input id="ufRegistro" type="text" 
				name="medico.ufRegistro" value="${medico.ufRegistro}"/><br />

			<label for="especialidade">Especialidade:</label> <input id="especialidade" type="text" 
				name="medico.especialidade" value="${medico.especialidade}"/><br />

			<label for="endereco">Endereço:</label> <input id="endereco" type="text" 
				name="medico.endereco" value="${medico.endereco}"/><br /> 
				
			<label for="cpf">CPF:</label> <input id="cpf" type="text" 
				name="medico.cpf" value="${medico.cpf}"/><br /> 
			
			<label for="telefone">Telefone:</label> <input id="telefone" type="text" 
				name="medico.telefone" value="${medico.telefone}"/><br /> 
			
			<label for="email">Email:</label> <input id="email" type="text"
				name="medico.email" value="${medico.email}"/><br /> 
				
			<label for="dataNasc">Data de Nascimento:</label> <input id="dataNasc" type="text"
				name="medico.dataDeNascimento" value="<fmt:formatDate value="${medico.dataDeNascimento}" pattern="dd/MM/yyyy"/>" /> <br /> 
				
			<label for="login">Login:</label><input id="login" type="text" 
				name="medico.perfil.login" value="${medico.perfil.login}"/><br /> 
			
			<label for="senha">Senha:</label> <input id="senha" type="password"
				name="medico.perfil.senha" value="${medico.perfil.senha}"/><br />

				
			<button id="submit-button" type="submit">Enviar</button>
			<a href="./"><button id="cancel-operation" type="submit" >Cancelar</button></a>
		</fieldset>
	</form>
		
</body>
</html>