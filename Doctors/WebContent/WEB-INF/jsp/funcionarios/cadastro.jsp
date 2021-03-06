<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar novo Funcion�rio:</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
</head>
<body>
		<div class="ui-state-error">
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
		</div>

	<form id="form" name="form" action="<c:url value="/funcionarios"/>" method="post">
		<fieldset>
			<legend>Adicionar Funcion�rio:</legend>

			<label for="nome">Nome:</label><input id="nome" type="text"
				name="funcionario.nome" value="${funcionario.nome}" /><br /> 

			<label for="dataAdmissao">Data de Admiss�o:</label><input id="dataAdmissao" type="text"
				name="funcionario.dataAdmissao" value="${funcionario.dataAdmissao}"/><br /> 

			<label for="matricula">N�mero de Matr�cula:</label> <input id="matricula" type="text" 
				name="funcionario.matricula" value="${funcionario.matricula}"/><br />
			
			<label for="endereco">Endere�o:</label> <input id="endereco" type="text" 
				name="funcionario.endereco" value="${funcionario.endereco}"/><br /> 
				
			<label for="cpf">CPF:</label> <input id="cpf" type="text" 
				name="funcionario.cpf" value="${funcionario.cpf}"/><br /> 
			
			<label for="telefone">Telefone:</label> <input id="telefone" type="text" 
				name="funcionario.telefone" value="${funcionario.telefone}"/><br /> 
			
			<label for="email">Email:</label> <input id="email" type="text"
				name="funcionario.email" value="${funcionario.email}"/><br /> 
				
			<label for="dataNasc">Data de Nascimento:</label> <input id="dataNasc" type="text"
				name="funcionario.dataNascimento" value="${funcionario.dataNascimento}"/> <br /> 
				
			<label for="login">Login:</label> <input id="login" type="text"
				name="funcionario.perfil.login" value="${funcionario.perfil.login}" /><br />
				
			<label for="senha">Senha:</label> <input id="senha" type="password"
				name="funcionario.perfil.senha" value="${funcionario.perfil.senha}" /><br />
			
			<button id="submit-button" type="submit">Enviar</button>
			<a href="./"><button id="cancel-operation" type="submit" >Cancelar</button></a>
		</fieldset>
	</form>
	<script>
		$(function(){
			$("#dataNasc").datepicker({
				changeMonth:true,changeYear:true,yearRange:'-100,+0',
				autoSize:true,dateFormat:'dd/mm/yy',maxDate: 0
			});
		});
	</script>
</body>
</html>