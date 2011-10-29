<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agenda do Médico</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/js/grid.locale-pt-br.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/js/jquery.jqGrid.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.printArea.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/doctors.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/logic.agenda.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
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
		.lista {
			float:right;
			width: 30%;
		}
		.divesq {
			float:left;
			width: 70%;
		}
		div#relatorios {
			float:right;
			width: 30%;
		}
		div#form {
			float:left;
			width: 70%;
		}
		div#foot{
			clear:both;
		}
		.acoes{
			margin-bottom: 4px;
		}
		div#consulta fieldset{
			padding:0; border:0; margin-top:25px;
		}
		div#consulta input.text { 
			margin-bottom:12px; width:95%; padding: .4em; 
		}
		div#divresultados {
			float:right;
			width: 75%;
		}
		div#divhistorico {
			float:left;
			width: 25%;
		}
</style>
</head>
<body>
	<label for="medicos">Médico:</label>
	<select id="medicos" name="agendamento.medico.id">
		<option value="">Selecione um médico..</option>
		<c:forEach items="${medicos}" var="medico">
		<option value="${medico.id}">${medico.nome}</option>
		</c:forEach>
	</select>
	
	<div id="agenda" >
		<div id="divesq">
			<div class="calendario" id="calendario"></div>
			<br /><input type="checkbox" id="showPreAgendam" class="button" /><label for="showPreAgendam">Visualizar apenas consultas a confirmar</label>
			<br /><button id="novaConsulta" class="button" disabled="disabled">Nova Consulta</button>
		</div>
		<div id="lista">
			<table id="agendamentos"></table>
		</div>
	</div>
	
	<div id="dialog-detalhes" class="dialog" title="Informações sobre a Consulta">
		<label class="label">Agendamento(#id): </label><label id="idAgendamento"></label><br />
		<label class="label">Nome do paciente: </label><label id="nomePaciente"></label><br />
		<label class="label">Nome do médico: </label><label id="nomeMedico"></label><br />
		<label class="label">Data do Agendamento: </label><label id="dataConsulta"></label><br />
		<label class="label">Hora do Agendamento: </label><label id="horaConsulta"></label><br />
		<label class="label">Convênio Utilizado: </label><label id="nomeConvenio"></label><br />
		<label class="label">Funcionário que realizou confirmação: </label><label id="nomeFuncionario"></label><br />
		
		<br /><h3>Ações:</h3><br />
		<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_FUNCIONARIO">
			<button id="btnConfirmar" class="button">Confirmar Pré-Agendamento</button>
			<button id="btnTransferir" class="button">Transferir Horário</button>
			<button id="btnNotificar" class="button">Notificar Chegada do Paciente</button>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MEDICO">
			<button id="btnIniciar" class="button">Iniciar Consulta</button>
		</sec:authorize>
	</div>
	
	<div id="dialog-confirmacao" class="dialog" title="Confirmação de Pré-Agendamento">
		<p>Escolha uma das opções abaixo:</p>
	</div>
	
	<div id="dialog-msg-confirmacao" class="dialog" title="Confirmação de Agendamento">
		<br /><p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
			O agendamento foi confirmado com sucesso.</p>
	</div>
	
	<div id="dialog-msg-cancelamento" class="dialog" title="Cancelamento de Agendamento">
		<p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
			O agendamento foi cancelado com sucesso.</p>
	</div>
	
	<div id="dialog-transferir" class="dialog" title="Transferir Horário de Consulta">
			<div class="divesq">
				<label>Nova Data: </label><br />
				<div class="calendar" id="novaData"></div>
			</div>
			<div class="lista">
				<table id="horariosList"></table>
				<br ><input type="hidden" id="novaHora" name="novaHora" />
			</div>
	</div>
	
	<div id="dialog-msg-sucesso" class="dialog" >
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		<div id="mensagem"></div>
	</div>
	
	
	<div id="consulta">
		<div id="dialog-form" class="consulta dialog" title="Nova Consulta">
				<div id="form">
					<form id="consulta-form" action="<c:url value="/consultas/efetuarConsulta"/>" method="post">
							<h2>Dados do paciente:</h2>
							Nome do Paciente: Guilherme Kamizake de Freitas <button id="historico">Ver Histórico</button><br/>
							Idade: 19 (07/10/1991)
							<h3>Queixa Principal:</h3>
							<textarea id="queixaprinc" name="consulta.queixaPrincipal" rows="12" cols="80">Modelo de Documento aqui?</textarea><br/>
							
							<h3>Observações:</h3>
							<textarea id="observacoes" name="consulta.observacoes" rows="4" cols="80">[Observações da Consulta]</textarea>
						<input type="hidden" id="agendamentoId" name="consulta.agendamento.id" />
					</form>
				</div>
				<div id="relatorios">
					<h2>Ações Disponíveis:</h2><br />
						<button id="emit-receita" type="button" class="button">Emitir Receita</button><br/>
						<button id="emit-atestado" class="button">Emitir Atestado</button><br/>
						<button id="solic-exame" class="button">Emitir Solicitação de Exame</button><br/>
						<br /><h2>Lista de Relatórios Emitidos:</h2><br />
						<table id="list-documentos" class="ui-widget ui-widget-content">
							<thead>
								<tr class="ui-widget-header ">
									<th>Id   </th>
									<th>Tipo              </th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<tr><td>1</td><td>Atestado</td><td><button id="edit" class="button">Editar</button></td><tr>
								<tr><td>1</td><td>Receita Médica</td><td><button id="edit" class="button">Editar</button></td><tr>
								<tr><td>1</td><td>Solicitação de Exame</td><td><button id="edit" class="button">Editar</button></td><tr>
							</tbody>
						</table>
				</div>
				<div id="foot">
				</div>
		</div>
		
		<div id="dialog-confirm" class="dialog" title="Cancelar consulta">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Você está saindo da consulta. Tem certeza disso?</p>
		</div>
	
		<div id="documento-form" class="dialog">
			<h3 id="label-documento">Informações do Documento:</h3>
			<textarea id="documento-info" rows="15"  cols="110"></textarea>
		</div>
		
		<div id="historico-busca" class="dialog" title="Consultar Histórico do Paciente">
			<div id="div-filtro">
				<h3>Opções de Busca:</h3>
				<table>
					<tr><td><label for="nomePaciente">Nome do paciente: </label></td>
						<td><input type="text" id="nomePaciente" /></td></tr>
					<tr><td><label for="dataInicial">Data Inicial: </label></td>
						<td><input type="text"  id="dataInicial" class="data" /></td></tr>
					<tr><td><label for="dataFinal">Data Final: </label></td>
						<td><input type="text" id="dataFinal" class="data" /></td></tr>
					<tr><td><label for="idMedico">Médico: </label></td>
					 	<td><select	id="idMedico">
								<c:forEach items="${medicos}" var="medico">
									<option value="${medico.id}">${medico.nome}</option>
								</c:forEach>
							</select></td></tr>
				</table>
				<br /><input type="text" id="idPaciente" name="idPaciente"/> 
				<br /><button id="btn-consultaHist" class="button" >Buscar Históricos</button>
			</div>
			<div id="divresultados">
				<table id="lista-resultados"></table>
			</div>
			<div id="divhistorico">
			</div>
		</div>
		
	</div>
	
	<div id="impressao" class="hidden"></div>
</body>
</html>