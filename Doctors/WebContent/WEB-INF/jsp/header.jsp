<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="wrap">
	<div id="header">
		<div id="logo">
			<h1 id="sitename">Doctor<span class="green">'s</span></h1>
			<h2 class="description">Sistema de Gerenciamento de Consult�rio M�dico</h2>
		</div>
		<div id="headercontent">
			<h2>Adicionar controle de acesso aqui</h2>
			<c:if test="${empty userSession.usuario }">
				Ol�, Visitante. <a href="<c:url value='/login'/>">Logar</a><br />
				Ainda n�o � usu�rio? <button type="button" onclick="abrirDialog()">Cadastre-se</button>
			</c:if>
			<c:if test="${!empty userSession.usuario }">
				Ol�, ${userSession.usuario.login} <a href="<c:url value='/logout'/>">Logout</a>
				Tipo de Perfil: ${userSession.usuario.tipo}	<br />
			</c:if>
			<p>&nbsp;</p>
		</div>
		<div id="sitecption">
			Doctors: Sistema para seu Consult�rio M�dico
			<span class="bigger">Descri��o</span> aqui
		</div>
	</div>
	<div id="main">
		<div id="menus">
			<div id="mainmenu">
				<ul>
				<li class="first" id="active"><a href="/Doctors">Home</a></li>
				<li><a href="#">Quem Somos</a></li>
				<li><a href="#">Corpo Cl�nico</a></li>
				<li><a href="#">Conv�nios de Cobertura</a></li>
				<li><a href="#">Contato</a></li>
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
</body>
</html>
