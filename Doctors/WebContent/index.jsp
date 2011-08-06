<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema Doctor's - Página Inicial</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>  
</head>
<body>
	<jsp:include page="WEB-INF/jsp/header.jsp"></jsp:include><br /> 
	
	<c:if test="${empty userSession.usuario }">
		Olá, Visitante. <a href="<c:url value='/login'/>">Logar</a><br />
		<button type="button" onclick="abrirDialog()">Cadastre-se</button>
	</c:if>
	<c:if test="${!empty userSession.usuario }">
		Olá, ${userSession.usuario.login} <a href="<c:url value='/logout'/>">Logout</a>
		Tipo de Perfil: ${userSession.usuario.tipo}	<br />
	</c:if>
	
	<div style="display:none;" id="dialog-cadastro" title="Cadastro de novo usuário" >
		<p>Para se cadastrar, confirme o perfil abaixo: </p>
		
		<a href="<c:url value='/pacientes/novo'/>">Paciente</a><br />
		<a href="<c:url value='/medicos/novo'/>">Medico</a><br />
		<a href="<c:url value='/funcionarios/novo'/>">Funcionario</a><br />
	</div>
	
	<br />Cadastros Básicos:<br/>
	<a href="medicos">Médico</a><br />
	<a href="funcionarios">Funcionário</a><br />
	<a href="pacientes">Paciente</a><br />
	<a href="convenios">Convênio</a><br/><br/><br/>

	Outros (em desenvolvimento):<br/>
	<a href="consultas">Consulta</a><br />
	<a href="exames">Exame</a><br />
	<a href="agenda">Agendamento</a><br />
	
	
	[ Projeto de ES II ]
	
	<script type="text/javascript">
	
		function abrirDialog(){
			$( "#dialog-cadastro" ).dialog({
				height: 140,
				modal: true
			});
		}
		
	</script>
</body>
</html>