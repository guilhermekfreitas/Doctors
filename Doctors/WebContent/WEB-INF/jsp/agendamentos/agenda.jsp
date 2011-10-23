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
	
	<script type="text/javascript">
		$("#agenda").hide();
		$("#dialog-detalhes").hide();
		
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
			alert("Falta implementar");
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
					//{name:'id',index:'id',hidden:true, width:60},
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
			   	//sortname: 'horario',
			   	//pager: "#pager2",
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
			//$("#agendamentos2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
			
			atualizaGrid();
			}
		});		
		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				atualizaGrid();
			}});	
		
		function Dia(data,horarios) {
			this.data = data;
			this.horarios = horarios;
		}
		
		
	function showCaption(){
		return "Agenda do dia: " + $("#calendario").attr("value") + " para médico: " + $("#medicos option:selected").text(); 
	}	
	
	function atualizaGrid(){
		$("#agendamentos").jqGrid('clearGridData');
		$("#agendamentos").jqGrid().setGridParam({postData: {idMedico: $("#medicos").val(), data: $("#calendario").attr("value")}});
		$("#agendamentos").jqGrid('setCaption',showCaption());
		$("#agendamentos").trigger("reloadGrid");
	}
	</script>	
</body>
</html>