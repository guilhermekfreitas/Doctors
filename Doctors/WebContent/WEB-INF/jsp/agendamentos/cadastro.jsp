<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar novo Agendamento:</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>

	<form name="form" action="<c:url value="/agenda"/>" method="post">
		<fieldset>
			<legend>Adicionar Agendamento:</legend>

			<label for="pacientes">Paciente:</label> 
			<select id="pacientes" name="agendamento.paciente.id">
				<c:forEach items="${pacientes}" var="paciente">
				<option value="${paciente.id}">${paciente.nome}</option>
				</c:forEach>
			</select><br />

			<label for="medicos">Médico:</label> 
			<select id="medicos" name="agendamento.medico.id">
				<c:forEach items="${medicos}" var="medico">
				<option value="${medico.id}">${medico.nome}</option>
				</c:forEach>
			</select><br />

			<label for="data">Data:</label> <input id="data" type="text"
				name="agendamento.data" /><br /> 
			
			<label for="hora">Hora:</label> <input id="hora" type="text"
				name="agendamento.hora" /><br /> 
			
			<!--  Falta inserir:
			    - paciente;
			    - médico;
			 -->
			 
			<button id="enviar" type="submit">Enviar</button>
		</fieldset>
	</form>
		
</body>
</html>