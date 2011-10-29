<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema Doctor's - Página Inicial</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
</head>
<body>
	<div id="content">
	<sec:authorize ifAnyGranted="ROLE_PACIENTE,ROLE_FUNCIONARIO,ROLE_MEDICO,ROLE_ADMIN">
		Ações: <br />
		<a href="#">Meus Dados</a><br />
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_PACIENTE,ROLE_ADMIN">
		<a href="agenda/novo">Pré-Agendar Consulta</a><br />
		<a href="#">Ver Histórico de Consultas</a><br />
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_FUNCIONARIO,ROLE_MEDICO,ROLE_ADMIN">
		<a href="agenda">Ver Agenda</a><br />
		<a href="pacientes">Controle de Pacientes</a><br />
		<a href="convenios">Controle de Convênios</a><br />
	</sec:authorize>				
	<sec:authorize ifAllGranted="ROLE_ADMIN">
		<a href="medicos">Controle de Médicos</a><br />
		<a href="funcionarios">Controle de Funcionários</a><br />
		<br />Area administrativa:<br /><a href="painel_admin">Administração</a><br />
	</sec:authorize>
	</div>
</body>
</html>