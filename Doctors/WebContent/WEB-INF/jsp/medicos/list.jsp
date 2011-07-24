<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de M�dicos</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<c:import url="../header.jsp"></c:import>

	<h1>Lista de M�dicos</h1>

	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>CRM</th>
				<th>UF</th>
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
					<td>${medico.uf}</td>
					<td>${medico.especialidade}</td>
					<td>${medico.endereco}</td>
					<td>${medico.cpf}</td>
					<td>${medico.telefone}</td>
					<td>${medico.email}</td>
					<td><fmt:formatDate value="${medico.dataDeNascimento}" pattern="dd/MM/yyyy"/></td>
					<td>${medico.login}</td>
					<td><a href="<c:url value="/medicos/${medico.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/medicos/remover/${medico.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/medicos/novo"/>">Adicionar novo M�dico</a><br />
	<a href="<c:url value="./"/>">Voltar � p�gina inicial</a><br />
</body>
</html>