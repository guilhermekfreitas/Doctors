<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Médicos</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.tablesorter.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/blue-tablesorter/style.css"/>"/>
</head>
<body>
	<div id="lista-regs" class="ui-widget">
	<h1>Lista de Médicos</h1>
	<table class="tablesorter">
		<thead>
			<tr>
				<th>Nome</th>
				<th>CRM</th>
				<th>UF de Registro</th>
				<th>Especialidade</th>
				<th>Endereco</th>
				<th>Cpf</th>
				<th>telefone</th>
				<th>Email</th>
				<th>Data de Nascimento</th>
				<th>Login</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${medicos}" var="medico">
				<tr>
					<td>${medico.nome}</td>
					<td>${medico.crm}</td>
					<td>${medico.ufRegistro}</td>
					<td>${medico.especialidade}</td>
					<td>${medico.endereco}</td>
					<td>${medico.cpf}</td>
					<td>${medico.telefone}</td>
					<td>${medico.email}</td>
					<td>${medico.dataNascimento}</td>
					<td>${medico.perfil.login}</td>
					<td><a href="<c:url value="/medicos/${medico.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/medicos/remover/${medico.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<a id="submit-button" class="btn-layout" href="<c:url value="/medicos/novo"/>">Adicionar novo Médico</a>
	<a id="cancel-operation" href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>