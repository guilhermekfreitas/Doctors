<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Consultas</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<h1>Lista de Consultas</h1>

	<table>
		<thead>
			<tr>
				<th>Paciente</th>
				<th>Médico</th>
				<th>Data</th>
				<th>Hora</th>
				<th>Queixa Principal</th>
				<th>Observações</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${consultas}" var="consulta">
				<tr>
					<td>${consulta.agendamento.paciente.nome}</td>
					<td>${consulta.agendamento.medico.nome}</td>
					<td>${consulta.agendamento.data}</td>
					<td>${consulta.agendamento.hora}</td>
					<td>${consulta.queixaPrincipal}</td>
					<td>${consulta.observacoes}</td>
					<td><a href="<c:url value="/consultas/${consulta.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/consultas/remover/${consulta.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/consultas/novo"/>">Adicionar nova Consulta</a><br />
	<a href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>