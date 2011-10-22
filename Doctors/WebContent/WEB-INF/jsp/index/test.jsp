<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/grid.locale-pt-br.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/jquery.jqGrid.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.css"/>"/>
<title>Insert title here</title>
</head>
<body>

	<button id="btn">Enviar dados</button>
	<table id="agendamentos"></table>
	<div id="pager2"></div>
			
	<input id="input" type="text"/>
			
	<script type="text/javascript">
		$("#btn").click(function(){
			
			jQuery("#agendamentos").jqGrid({
			   	url:'testJSONPost',
			   	mtype: 'POST',
			   	postData: {idMedico: $("#input").val(), horario: '22/10/2011'},
				datatype: "json",
			   	colNames:['ID','ID do Agendamento','Data','Horário','Nome do Paciente', 'Status','Convenio','Médico','Funcionário'],
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
			
			
		});
		
		
	function showCaption(){
		return "Agenda do dia: " + $("#calendario").attr("value") + " para médico: " + $("#medicos option:selected").text(); 
	}	
	</script>

</body>
</html>