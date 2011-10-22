<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agenda do M�dico</title>
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
	<label for="medicos">M�dico:</label>
	<select id="medicos" name="agendamento.medico.id">
		<option value="">Selecione um m�dico..</option>
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
			<div id="pager2"></div>
		</div>
	</div>
	
	<div id="dialog-detalhes" title="Informa��es sobre a Consulta">
		<label class="label">Agendamento(#id): </label><label id="idAgendamento"></label><br />
		<label class="label">Nome do paciente: </label><label id="nomePaciente"></label><br />
		<label class="label">Nome do m�dico: </label><label id="nomeMedico"></label><br />
		<label class="label">Data do Agendamento: </label><label id="dataConsulta"></label><br />
		<label class="label">Hora do Agendamento: </label><label id="horaConsulta"></label><br />
		<label class="label">Conv�nio Utilizado: </label><label id="nomeConvenio"></label><br />
		<label class="label">Funcion�rio que realizou confirma��o: </label><label id="nomeFuncionario"></label><br />
		
		<br /><h3>A��es:</h3><br />
		<button id="btnConfirmar">Confirmar Pr�-Agendamento</button>
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
				
				// 20/10/2011
				$.getJSON('<c:url value="/agenda/carregaAgenda/' + idMedico + '"/>', function(json){
				
					
				if (json.datas.length != 0){
					
					// popula array que cont�m as datas, de cada dia do calend�rio.
					agenda = [];
					
					consulta = {
						id:"1",
						idAgendamento:"123",
						dataConsulta:"21/10/2011",
						horario:"08:00-08:30",
						nomePaciente: "Guilherme Kamizake de Freitas",
						status: "N�o Confirmado",
						convenio: "Cassi",
						medico: "Dr. Paulo Jos�",
						funcionario: "Silvana"
					};
					
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					agenda.push(consulta);
					//$("#agendamentos").jqGrid('addRowData',1,agenda[0]);
					
					$("#agendamentos").jqGrid('setCaption',showCaption());
					
				}
			});
			}
		});		
		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				//$("#dataAgendamento").attr("value",dateText);
				$("#agendamentos").jqGrid('clearGridData');
				
				$("#agendamentos").jqGrid('setCaption',showCaption());
				for (var i=0;i<=agenda.length;i++ ){
					$("#agendamentos").jqGrid('addRowData',i+1,agenda[i]);
				}
				
			}});	
		
		function Dia(data,horarios) {
			this.data = data;
			this.horarios = horarios;
		}
		
		jQuery("#agendamentos").jqGrid({
		   	//url:'getLista',
			datatype: "local",
		   	colNames:['ID','ID do Agendamento','Data','Hor�rio','Nome do Paciente', 'Status','Convenio','M�dico','Funcion�rio'],
		   	colModel:[
				{name:'id',index:'id',hidden:true, width:60},
				{name:'idAgendamento',index:'idAgendamento',hidden:true, width:60},
				{name:'data',index:'dataConsulta',hidden:true, width:60},
		   		{name:'horario',index:'horario', width:60, align:"center"},
		   		{name:'nomePaciente',index:'nomePaciente', width:280},
		   		{name:'status',index:'status', width:120, align:"right"},
		   		{name:'convenio',index:'convenio',hidden:true, width:50, align:"right"},
		   		{name:'medico',index:'medico',hidden:true, width:50, align:"right"},
		   		{name:'funcionario',index:'funcionario',hidden:true, width:50, align:"right"},
		   	],
		   	onSelectRow: function(id){
		   		
		   		//getRowData >> para JSON
		   		
		   		var registro = $("#agendamentos").jqGrid('getLocalRow',id);
		   		$("#idAgendamento").text(registro.idAgendamento);
		   		$("#nomePaciente").text(registro.nomePaciente);
		   		$("#nomeMedico").text(registro.medico);
		   		$("#dataConsulta").text(registro.dataConsulta);
		   		$("#horaConsulta").text(registro.horario);
		   		$("#nomeConvenio").text(registro.convenio);
		   		$("#nomeFuncionario").text(registro.funcionario);
		   		
		   	    $("#dialog-detalhes").dialog({
		   	    	width: 540,
		   	    	resizable: false,
		   	    	modal: true
		   	    });
		   	},
		   	rowNum:16,
		   	rowList:[16],
		   	sortname: 'id',
		   	pager: "#pager2",
		    viewrecords: true,
		    sortorder: "desc",
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
		jQuery("#agendamentos2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
		
		
	function showCaption(){
		return "Agenda do dia: " + $("#calendario").attr("value") + " para m�dico: " + $("#medicos option:selected").text(); 
	}	
	
	function postjson(url,data,bS,s,e){
		$.ajax({
			type:"POST",
			url:url,
			data:data,
			dataType:"json",
			beforeSend:bS,
			success:s,
			error:e
		});
	}
	
	</script>	
</body>
</html>