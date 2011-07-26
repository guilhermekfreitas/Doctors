<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Agendamento</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>

</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>
	<form action="<c:url value="/agenda/${agendamento.id}"/>" method="post">
		<fieldset>
			<legend>Editar Agendamento:</legend>

			<input type="hidden" name="agendamento.paciente.id" value="${agendamento.paciente.id}"/>
			<input type="hidden" name="agendamento.medico.id" value="${agendamento.medico.id}"/>

			<label for="paciente">Paciente:</label> <input id="paciente" type="text"
				 value="${agendamento.paciente.nome}" disabled="disabled" size="40"/><br /> 

			<label for="medico">Médico:</label> <input id="medico" type="text"
				 value="${agendamento.medico.nome}" disabled="disabled" size="40"/><br /> 

			<label for="data">Data:</label> <input id="data" type="text"
				name="agendamento.data" value="${agendamento.data}" /><br /> 
			
			<label for="hora">Hora:</label> <input id="hora" type="text"
				name="agendamento.hora" value="${agendamento.hora}"/><br /> 
			
			<label for="funcionarios">Funcionário:</label> 
			<select id="funcionarios" name="agendamento.funcionario.id">
				<c:forEach items="${funcionarios}" var="funcionario">
				<option value="${funcionario.id}">${funcionario.nome}</option>
				</c:forEach>
			</select><br />
			
			<label for="confirmado">Confirmado:</label> <input id="confirmado" type="checkbox"
				name="agendamento.confirmado" value="true" ${agendamento.confirmado?'checked':''}/><br />
				
			<label for="cancelado">Cancelado:</label> <input id="cancelado" type="checkbox"
				name="agendamento.cancelado" value="true" ${agendamento.cancelado?'checked':''}/><br />
				 
			<!--  Falta inserir:
				- 
			 -->

			<button type="submit" name="_method" value="PUT">Editar</button>
		</fieldset>
	</form>
</body>
</html>