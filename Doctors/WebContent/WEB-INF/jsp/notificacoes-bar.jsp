<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<!-- <script type="text/javascript" src="<c:url value='/js/jixedbar/jquery.jixedbar.js'/>"></script> -->
<script type="text/javascript" src="<c:url value='/js/doctors.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link type="text/css" href="<c:url value='/js/jixedbar/themes/vista/jx.stylesheet.css'/>" rel="stylesheet"/>
<link type="text/css" href="<c:url value='/js/jixedbar/themes/vista/jx.bar.css'/>" rel="stylesheet"/> 
</head>
<body>
	<sec:authorize ifAnyGranted="ROLE_MEDICO">
		<div id="sample-bar">
	
			<ul class="jx-bar-button-right">
				<li title="Notificações"><a href="#">Visualizar Notificações
				</a>
					<ul>
						<li><p>(23:33): Notificação de chegada de paciente: Guilherme Kamizake</p>
						    <button class="button">Detalhes</button>
						</li>
						<li><p>(11:33): Notificação de chegada de paciente: Juliana</p>
						    <button class="button">Detalhes</button>
						</li>
					</ul></li>
			</ul>
	
			<span class="jx-separator-right"></span>
		</div>
	</sec:authorize>
<script type="text/javascript">

	$(".button").button();

	$("#sample-bar").jixedbar({
		
	});
</script>
</body>
</html>