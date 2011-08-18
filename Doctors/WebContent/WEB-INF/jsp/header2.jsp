<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>  
<style>

#principal {
	font-family: times, Times New Roman, times-roman, georgia, serif;
	color: #444;
	margin: 0;
	padding: 0px 0px 6px 0px;
	font-size: 51px;
	line-height: 44px;
	letter-spacing: -2px;
	font-weight: bold;
}

#secundario {
	font-family: Gill Sans, Verdana;
	font-size: 11px;
	line-height: 14px;
	text-transform: uppercase;
	letter-spacing: 2px;
	font-weight: bold;
}
</style>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/blue-tablesorter/style.css"/>"/>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="wrap">
		<div id="logo">
			<h1 id="sitename">Doctor<span class="green">'s</span></h1>
			<h2 class="description">Sistema de Gerenciamento de Consultório Médico</h2>
		</div>
		<div id="headercontent">
			<h2>Adicionar controle de acesso aqui</h2>
			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tristique, felis ut vehicula laoreet, nunc dolor bibendum tortor, a faucibus eros tortor sed nunc. Aliquam eget arcu. Nam sem justo, vulputate eget, cursus id, volutpat ullamcorper, felis. In eget velit ut diam ultrices ultrices. <img src="/img/bullet.png" alt="Bullet" width="10" height="10" /> <a href="#">Read More</a></p>
			<p>&nbsp;</p>
		</div>
		<div id="sitecption">
			Doctors doctors
			<span class="bigger">Descrição</span> aqui
		</div>
	</div>
	
	<label id="principal">Doctors</label><br />
	
	<c:if test="${empty userSession.usuario }">
		Olá, Visitante. <a href="<c:url value='/login'/>">Logar</a><br />
		Ainda não é usuário? <button type="button" onclick="abrirDialog()">Cadastre-se</button>
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

