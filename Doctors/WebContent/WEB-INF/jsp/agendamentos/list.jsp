<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Agendamentos</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<sec:authorize ifNotGranted="ROLE_PACIENTE">
		<h1>Lista de Agendamentos</h1>
		
		<table>
			<thead>
				<tr>
					<th>Data</th>
					<th>Hora</th>
					<th>Confirmado</th>
					<th>Cancelado</th>
					<th>Nome do Paciente</th>
					<th>Nome do Médico</th>
					<th>Funcionário?</th>
					<th>Consulta</th>
					<th>Alterar</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agendamentos}" var="agendamento">
					<tr>
						<td><joda:format value="${agendamento.dataAgendamento}"/></td>
						<td><joda:format value="${agendamento.horaAgendamento}" pattern="hh:mm"/></td>
						<td>
							<c:choose>
								<c:when test="${agendamento.confirmado}">Sim</c:when>
								<c:when test="${!agendamento.confirmado}">Não</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${agendamento.cancelado}">Sim</c:when>
								<c:when test="${!agendamento.cancelado}">Não</c:when>
							</c:choose>
						</td>
						<td>${agendamento.paciente.nome}</td>
						<td>${agendamento.medico.nome}</td>
						<td>${agendamento.funcionario.nome}</td>
						<td>
							${agendamento.consulta.id}
							<c:if test="${agendamento.isConsultaDisponivel()}">
								<c:choose>
									<c:when test="${ empty agendamento.consulta}">
										<a href="<c:url value="/consultas/novo/${agendamento.id}"/>">Iniciar Consulta</a>
									</c:when>
									<c:when test="${ !empty agendamento.consulta}">Já Efetuada</c:when>
								</c:choose>
							</c:if> 
						</td>
						<td><a href="<c:url value="/agenda/${agendamento.id}"/>">Alterar</a></td>
						<td><a href="<c:url value="/agenda/remover/${agendamento.id}"/>">Remover</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</sec:authorize>
	<a href="<c:url value="/agenda/novo"/>">Adicionar novo Agendamento</a><br />
	<a href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>