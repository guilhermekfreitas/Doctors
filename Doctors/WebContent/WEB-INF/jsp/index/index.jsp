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
</head>
<body>
	<div id="content">
	<br />Cadastros Básicos:<br/>
	<a href="medicos">Médico</a><br />
	<a href="funcionarios">Funcionário</a><br />
	<a href="pacientes">Paciente</a><br />
	<a href="convenios">Convênio</a><br/><br/><br/>

	Outros (em desenvolvimento):<br/>
	<a href="consultas">Consulta</a><br />
	<a href="exames">Exame</a><br />
	<a href="agenda">Agendamento</a><br /><br />
	
	<sec:authorize ifAllGranted="ROLE_ADMIN">
		Area administrativa:<br /><a href="painel_admin">Administração</a><br />
	</sec:authorize>
	</div>
</body>
</html>