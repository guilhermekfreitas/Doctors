<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Pacientes</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<c:import url="../header.jsp"></c:import>

	<h1>Lista de Pacientes</h1>

	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Telefone</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pacientes}" var="paciente">
				<tr>
					<td>${paciente.nome}</td>
					<td>${paciente.email}</td>
					<td>${paciente.telefone}</td>
					<td><a href="<c:url value="/pacientes/${paciente.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/pacientes/remover/${paciente.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/pacientes/novo"/>">Adicionar novo Paciente</a><br />
	<a href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>