<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema Doctor's - Página Inicial</title>
</head>
<body>
	<jsp:include page="WEB-INF/jsp/header.jsp"></jsp:include><br /> 
	
	Cadastros Básicos:<br/>
	<a href="medicos">Médico</a><br />
	<a href="funcionarios">Funcionário</a><br />
	<a href="pacientes">Paciente</a><br />
	<a href="convenios">Convênio</a><br/><br/><br/>

	Outros (em desenvolvimento):<br/>
	<a href="consultas">Consulta</a><br />
	<a href="exames">Exame</a><br />
	<a href="agenda">Agendamento</a><br />
	
	Minhas melhorias
</body>
</html>