<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pré-Agendar Nova Consulta:</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/grid.locale-pt-br.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/jquery.jqGrid.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/logic-preagendar.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.css"/>"/>
<style>
		div#lista {
			float:right;
			width: 75%;
		}
		div#divesq {
			float:left;
			width: 25%;
		}
		div#botoesDiv {
			clear: both;
		}
</style>
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
			<input type="hidden" name="agendamento.paciente.id" value="${paciente.id}"/>
			
			<table>
			<tr><td><label for="pacientes">Paciente:</label></td> 
			<td><input type="text" disabled="disabled" name="paciente.nome" value="${paciente.nome}" size="35" /></td></tr>

			<tr><td><label for="medicos">Médico:</label></td> 
			<td><select id="medicos" name="agendamento.medico.id">
				<option value="">Selecione um médico..</option>
				<c:forEach items="${medicos}" var="medico">
				<option value="${medico.id}">${medico.nome}</option>
				</c:forEach>
			</select></td></tr>

			<tr><td><label for="convenios">Convênio:</label></td> 
			<td><select id="convenios" name="agendamento.convenio.id" ${empty convenios?'disabled':''}>
				<c:choose>
					<c:when test="${!empty convenios}">
						<option value="">Selecione um convênio..</option>
						<c:forEach items="${convenios}" var="convenio">
						<option value="${convenio.id}">${convenio.nome}</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option value="0">Particular</option>
					</c:otherwise>
				</c:choose>
			</select></td></tr>

			 </table>
			<div id="dadosHorario">
			<fieldset > <legend>Horário de Agendamento:</legend>
			<input id="horaAgendamento" type="hidden"
				name="agendamento.horaAgendamento" value='${agendamento.horaAgendamento}'/><br /> 
			
			<input id="dataAgendamento" type="hidden"
				name="agendamento.dataAgendamento" value='<joda:format value="${agendamento.dataAgendamento}"/>'/><br /> 
			
			</fieldset>
				
			</div>
			
			<div id="agenda">
				<div id="divesq">
					<div class="calendar" id="calendar"></div>
				</div>
				<div id="lista">
					<table id="horariosLivres"></table>
				</div>
			</div>
			<div id="botoesDiv">
				<button id="enviar" type="submit">Enviar</button>
				<a href="./"><button id="cancel-operation" type="submit" >Cancelar</button></a>
			</div>
		</fieldset>
	</form>
		
</body>
</html>