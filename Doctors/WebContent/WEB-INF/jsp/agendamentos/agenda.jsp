<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agenda do Médico</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/grid.locale-pt-br.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/jquery.jqGrid.min.js'/>"></script>
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
	
	<div id="agenda">
		<div id="divesq">
			<div class="calendario" id="calendario"></div>
			<br /><input type="checkbox" id="showPreAgendam" /><label for="showPreAgendam">Visualizar apenas consultas a confirmar</label>
			<br /><button id="novaConsulta" disabled="disabled">Nova Consulta</button>
		</div>
		<div id="lista">
			<table id="agendamentos"></table>
		</div>
	</div>
	
	<div id="dialog-detalhes" title="Informações sobre a Consulta">
		<label class="label">Agendamento(#id): </label><label id="idAgendamento"></label><br />
		<label class="label">Nome do paciente: </label><label id="nomePaciente"></label><br />
		<label class="label">Nome do médico: </label><label id="nomeMedico"></label><br />
		<label class="label">Data do Agendamento: </label><label id="dataConsulta"></label><br />
		<label class="label">Hora do Agendamento: </label><label id="horaConsulta"></label><br />
		<label class="label">Convênio Utilizado: </label><label id="nomeConvenio"></label><br />
		<label class="label">Funcionário que realizou confirmação: </label><label id="nomeFuncionario"></label><br />
		
		<br /><h3>Ações:</h3><br />
		<button id="btnConfirmar">Confirmar Pré-Agendamento</button>
		<button id="btnNotificar">Notificar Chegada do Paciente</button>
		<button id="btnIniciar">Iniciar Consulta</button>
	</div>
	
<div id="consulta">
	<div id="dialog-form" class="consulta" title="Nova Consulta">
			<div id="form">
				<form>
					<fieldset>
						<h1>Dados do paciente:</h1>
						Nome do Paciente: Guilherme Kamizake de Freitas <button id="historico">Ver Histórico</button><br/>
						Idade: 19 (07/10/1991)
					</fieldset>
					<fieldset>
						<h1>Queixa Principal</h1>
						<textarea id="queixaprinc" rows="12" cols="80">Modelo de Documento aqui?</textarea><br/>
						
						<h1>Observações</h1>
						<textarea id="observacoes" rows="5" cols="80">[Observações da Consulta]</textarea>
					</fieldset>
				</form>
			</div>
			<div id="relatorios">
				<h1>Ações Disponíveis:</h1>
				<fieldset>
					<button id="emit-receita" type="button" class="acoes">Emitir Receita</button><br/>
					<button id="emit-atestado" class="acoes">Emitir Atestado</button><br/>
					<button id="solic-exame" class="acoes">Emitir Solicitação de Exame</button><br/>
				</fieldset>
				<fieldset>
					<h1>Lista de Relatórios Emitidos:</h1>
					<table class="ui-widget ui-widget-content">
						<thead>
							<tr class="ui-widget-header ">
								<th>Descrição              </th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr><th>Atestado</th><th><button id="edit">Editar</button></th><tr>
							<tr><th>Receita Médica</th><th><button id="edit">Editar</button></th><tr>
							<tr><th>Solicitação de Exame</th><th><button id="edit">Editar</button></th><tr>
						</tbody>
					</table>
				</fieldset>
			</div>
			<div id="foot">
			</div>
	</div>

	<div id="receita-form" title="Nova Receita">
		<h1>Informações sobre Receita:</h1>
		<textarea rows="15" cols="110">[Informações sobre Receita]</textarea>
	</div>
	
	<div id="atestado-form" title="Novo Atestado">
		<h1>Informações sobre Atestado:</h1>
		<textarea rows="15" cols="110">[Modelo de Atestado]</textarea>
	</div>
	
	<div id="exame-form" title="Nova Solicitação de Exame">
		<h1>Informações sobre Exame:</h1>
		<textarea rows="15" cols="110">[Modelo de Exame]</textarea>
	</div>
	
	<div id="historico-busca" title="Consultar Histórico do Paciente">
		<fieldset>
			<h3>Opções de Busca:</h3>
			<br /><label for="nome-paciente">Nome do paciente: </label><label id="nome-paciente">Guilherme Kamizake de Freitas</label>
			<br /><label for="filtro-dtinicial">Data Inicial: </label><input type="text" id="filtro-dtinicial" />
			<br /><label for="filtro-dtfinal">Data Final: </label><input type="text" id="filtro-dtfinal" />
			<br /><label for="filtro-medico">Médico: </label><input type="text" id="filtro-medico" />
		</fieldset>
		<h3>Deve colocar tabela à direta ---- e visualizar a esquerda</h3>
	</div>
	
</div>
			
	<script type="text/javascript">
		$("#agenda").hide();
		$("#dialog-detalhes").hide();
		$("#receita-form").hide();        // transformar em class ? p/ poder esconder em grupos?
		$("#atestado-form").hide();
		$("#exame-form").hide();
		$("#dialog-form").hide();
		$("#historico-busca").hide();
		
		$( "#showPreAgendam" ).button();
		$( "#novaConsulta").button();
		$( "#btnConfirmar").button();
		$( "#btnNotificar").button();
		$( "#btnIniciar" ).button();

		$( "#btnConfirmar" ).click(function(){
			alert("Falta implementar");
		});
		$( "#btnNotificar" ).click(function(){
			alert("Falta implementar");
		});
		$( "#btnIniciar" ).click(function(){
			$( "#dialog-form" ).dialog( "open" );
		});
		
		$("#medicos").change(function(){
			var idMedico = $("#medicos").val();
			if (idMedico == 0){
				$("#agenda").hide();
			} else {
				$("#agenda").show();                      // passar a data tbm.
				
				jQuery("#agendamentos").jqGrid({
			   	url:'agenda/carregaAgenda',
			   	mtype: 'POST',
			   	postData: {idMedico: $("#medicos").val(), data: $("#calendario").attr("value")},
				datatype: "json",
			   	colNames:['ID do Agendamento','Data','Horário','','Nome do Paciente', 'Status','Convenio','','Médico','','Funcionário'],
			   	colModel:[
					{name:'idAgendamento',index:'idAgendamento',hidden:true, width:60},
					{name:'data',index:'data',hidden:true, width:60},
			   		{name:'horario',index:'horario', sortable:false,width:60, align:"center"},
			   		{name:'idPaciente',index:'idPaciente',hidden:true, width:60},
			   		{name:'nomePaciente',index:'nomePaciente', sortable:false, width:280},
			   		{name:'status',index:'status', sortable:false, width:120, align:"right"},
			   		{name:'convenio',index:'convenio',hidden:true, width:50, align:"right"},
			   		{name:'idMedico',index:'idMedico',hidden:true, width:60},
			   		{name:'nomeMedico',index:'medico',hidden:true, width:50, align:"right"},
			   		{name:'idFuncionario',index:'idFuncionario',hidden:true, width:60},
			   		{name:'nomeFuncionario',index:'funcionario',hidden:true, width:50, align:"right"}
			   	],
			   	onSelectRow: function(id){
			   		
				   	var registro = $("#agendamentos").jqGrid('getRowData',id);
				   	
				   	if (registro.idAgendamento !== "0"){
				   		$("#idAgendamento").text(registro.idAgendamento);
				   		$("#nomePaciente").text(registro.nomePaciente);
				   		$("#nomeMedico").text(registro.nomeMedico);
				   		$("#dataConsulta").text(registro.data);
				   		$("#horaConsulta").text(registro.horario);
				   		$("#nomeConvenio").text(registro.convenio);
				   		$("#nomeFuncionario").text(registro.nomeFuncionario);
				   		
				   	    $("#dialog-detalhes").dialog({
				   	    	width: 540,
				   	    	resizable: false,
				   	    	modal: true
				   	    });
				   	 	$( "#novaConsulta").attr("disabled","disabled");
				   	} else {
				   		$( "#novaConsulta").attr("disabled","");
				   	}
			   	},
			   	rowNum:20,
			   	rowList:[20],
			    viewrecords: true,
			    sortorder: "asc",
			    width: 640,
			    height: 380,
			    caption:showCaption(),
			    jsonReader : {
		     		root: "rows",
		     		page: "page",
		     		total: "total",
		    		records: "records",
		   		    repeatitems: true,
		   		    cell: "cells",
		   		    id: "id",
		   		    userdata: "userdata",
		   		    subgrid: {root:"rows", 
		    		    repeatitems: true, 
		    	         cell:"cells"
		             }}
			});
			
			atualizaGrid();
			}
		});		
		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				atualizaGrid();
			}});	
		
	function showCaption(){
		return "Agenda do dia: " + $("#calendario").attr("value") + " para médico: " + $("#medicos option:selected").text(); 
	}	
	
	function atualizaGrid(){
		$("#agendamentos").jqGrid('clearGridData');
		$("#agendamentos").jqGrid().setGridParam({postData: {idMedico: $("#medicos").val(), data: $("#calendario").attr("value")}});
		$("#agendamentos").jqGrid('setCaption',showCaption());
		$("#agendamentos").trigger("reloadGrid");
	}
	
	
	$( "#dialog-form" ).dialog({
		autoOpen: false,
		closeOnEscape: false,
		height: 500,
		width: 850,
		modal: true,
		buttons: {
			"Finalizar": function() {
				$( this ).dialog( "close" );
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	
	$( "#emit-receita" ).button().click(function() {
		$( "#receita-form" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 370,
			width: 600,
			modal: true,
			buttons: {
				"Salvar": function() {
					$( this ).dialog( "close" );
				},
				"Imprimir": function() {
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$( "#receita-form").dialog("open");
	});
	
	$( "#emit-atestado" ).button().click(function() {
		$( "#atestado-form" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 370,
			width: 600,
			modal: true,
			buttons: {
				"Salvar": function() {
					$( this ).dialog( "close" );
				},
				"Imprimir": function() {
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$( "#atestado-form").dialog("open");
	});
	
	$( "#solic-exame" ).button().click(function() {
		$( "#exame-form" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 370,
			width: 600,
			modal: true,
			buttons: {
				"Salvar": function() {
					$( this ).dialog( "close" );
				},
				"Imprimir": function() {
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$( "#exame-form").dialog("open");
	});
	
	$( "#historico" ).button().click(function() {
		$( "#historico-busca" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 370,
			width: 600,
			modal: true,
			buttons: {
				"Salvar": function() {
					$( this ).dialog( "close" );
				},
				"Imprimir": function() {
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$( "#historico-busca").dialog("open");
	});
	
	</script>	
</body>
</html>