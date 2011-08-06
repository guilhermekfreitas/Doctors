<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Funcionários</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.tablesorter.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
</head>
<body>
	<c:import url="../header.jsp"></c:import>
	
	<div id="lista-regs" class="ui-widget">
	<h1>Lista de Funcionários</h1>
	<table class="tablesorter">
		<thead>
			<tr>
				<th>Nome</th>
				<th>Data de Admissão</th>
				<th>Matrícula</th>
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
			<c:forEach items="${funcionarios}" var="funcionario">
				<tr>
					<td>${funcionario.nome}</td>
					<td>${funcionario.dataAdmissao}</td>
					<td>${funcionario.matricula}</td>
					<td>${funcionario.endereco}</td>
					<td>${funcionario.cpf}</td>
					<td>${funcionario.telefone}</td>
					<td>${funcionario.email}</td>
					<td><fmt:formatDate value="${funcionario.dataDeNascimento}" pattern="dd/MM/yyyy"/></td>
					<td>${funcionario.login}</td>
					<td><a href="<c:url value="/funcionarios/${funcionario.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/funcionarios/remover/${funcionario.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<a id="submit-button" href="<c:url value="/funcionarios/novo"/>">Adicionar novo Funcionário</a>
	<a id="cancel-operation" href="<c:url value="./"/>">Voltar à Página Inicial</a><br />
</body>
</html>