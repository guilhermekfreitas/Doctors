<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<!-- <script type="text/javascript" src="<c:url value='/js/jquery.timers-1.2.js'/>"></script> -->
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<style>
	.item {
	
	}
</style>
</head>
<body>
<div id="wrap">
	<div id="header">
		<div id="logo">
			<h1 id="sitename">Doctor<span class="green">'s</span></h1>
			<h2 class="description">Sistema de Gerenciamento de Consultório Médico</h2>
		</div>
		<div id="headercontent">
			<c:if test="${empty userSession.usuario }">
				Olá, Visitante. <a href="<c:url value='/login'/>">Logar</a><br />
				Ainda não é usuário? <button id="btnNovoCadastro" class="button" type="button">Cadastre-se</button>
			</c:if>
			<c:if test="${!empty userSession.usuario }">
				Olá, ${userSession.usuario.login} <a href="<c:url value='/logout'/>">Logout</a>
				Tipo de Perfil: ${userSession.usuario.tipo}	<br />
			</c:if>
			<p>&nbsp;</p>
			<sec:authorize ifAllGranted="ROLE_MEDICO">
				<button id="btnVerNotificacoes" class="button">Notificações</button>
			</sec:authorize>
		</div>
		<div id="sitecption">
			Doctors: Sistema para seu <span class="bigger">Consultório Médico</span>
		</div>
	</div>
	<div id="main">
		<div id="menus">
			<div id="mainmenu">
				<ul>
				<li class="first" id="active"><a href="/Doctors">Home</a></li>
				<li><a href="#">Quem Somos</a></li>
				<li><a href="#">Corpo Clínico</a></li>
				<li><a href="<c:url value='/conveniosassoc'/>">Convênios de Cobertura</a></li>
				<li><a href="<c:url value='/contato'/>">Contato</a></li>
				</ul>
			</div>
			<!-- <div id="submenu">
				<ul>
				<li><a href="#">Lorem Ipsum</a></li>
				<li><a href="#">Dolor Sit Amet</a> </li>
				<li><a href="#">Consectetur Adipiscing </a></li>
				<li><a href="#">Cras Tristiquee</a></li>
				</ul>
			</div>  -->
		</div>
	</div>
</div>

<div id="notificacoes-list" class="hidden">

<div id="notificacao" class="item">
	<label for="horaNotificacao"></label><label id="horaNotificacao"></label><br />
	<label id="idPaciente"></label><br />
	<label id="nomePaciente"></label><br />
	<label id="funcionario"></label><br />
	<label id="horario"></label><br />
</div>
</div>

<script type="text/javascript">

/*$(document).everyTime(15000, function(){
		
		$.ajax({
			url: 'notificacoes/verifica',
			type: 'get',
			dataType: 'json',
			success: function(data){
				
			},
			error: function(data){
				alert('ERRO!');
			} 
		});	
		
	},0);*/

	$(".hidden").hide();
	$(".button").button();

	$("#btnVerNotificacoes").click(function(){
		$.ajax({
			url: 'notificacoes/verifica',
			type: 'get',
			dataType: 'json',
			success: function(data){
				console.log(data.notificacoes);
				//for (var i in data.notificacoes.length){
					var json = data.notificacoes[0];
					var notificacao = {
							horaNotificacao: json.horarioNotificacao,
							idPaciente: json.idPaciente,
							nomePaciente: json.nomePaciente,
							funcionario: json.nomeFuncionario,
							horario: json.horarioConsulta
					};
				//}
				
					
				$("#horaNotificacao").text(notificacao.horaNotificacao);
				$("#idPaciente").text(notificacao.idPaciente);
				$("#nomePaciente").text(notificacao.nomePaciente);
				$("#funcionario").text(notificacao.funcionario);
				$("#horario").text(notificacao.horario);
				
				$("#notificacoes-list").dialog({					
				});
			},
			error: function(data){
				alert('ERRO!');
			} 
		});	
	});
	
	$("#btnNovoCadastro").click(function(){
		
	});
</script>
</body>
</html>
