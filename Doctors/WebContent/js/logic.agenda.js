$(document).ready(function(){
	
		MYAPP = {};
		MYAPP.agendamento = {
				// atributos
				id: '0',
				data: '',
				paciente: {
					id: '0',
					nome: '' },
				medico: {
					id: '0',
					nome: '' },
				// métodos
	   			carrega: function(registro){
	   						this.id = registro.idAgendamento;
	   						this.data = registro.data;
	   						this.horario = registro.horario;
	   						this.nomeConvenio = registro.convenio;
	   						this.nomeFuncionario = registro.funcionario;
	   						this.paciente.id = registro.idPaciente,
	   						this.paciente.nome = registro.nomePaciente;
	   						this.medico.id = '-1';
	   						this.medico.nomeMedico = registro.nomeMedico;
	   					},
	   			getDadosConsulta: function(){
	   				
	   					}
				};
	
		$("#agenda").hide();
		$("#dialog-detalhes").hide();
		$("#receita-form").hide();        // transformar em class ? p/ poder esconder em grupos?
		$("#atestado-form").hide();
		$("#exame-form").hide();
		$("#dialog-form").hide();
		$("#historico-busca").hide();
		
		//$( "#showPreAgendam" ).button();
		//$( "#novaConsulta").button();
		//$( "#btnConfirmar").button();
		//$( "#btnNotificar").button();
		//$( "#btnIniciar" ).button();
		
		$( ".button" ).button();

		$( "#btnConfirmar" ).click(function(){
			alert("Falta implementar");
		});
		$( "#btnNotificar" ).click(function(){
			alert("Falta implementar");
		});
		$( "#btnIniciar" ).click(function(){
			$("#agendamentoId").val($("#idAgendamento").text());
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
				   		
				   		MYAPP.agendamento.carrega(registro);
				   		
				   		var dados = MYAPP.agendamento;
				   		
				   		$("#idAgendamento").text(dados.idAgendamento);
				   		$("#nomePaciente").text(registro.nomePaciente);
				   		$("#nomeMedico").text(registro.nomeMedico);
				   		$("#dataConsulta").text(registro.data);
				   		$("#horaConsulta").text(registro.horario);
				   		$("#nomeConvenio").text(registro.convenio);
				   		$("#nomeFuncionario").text(registro.nomeFuncionario);
				   		alert(registro.idPaciente);
				   		$("#idPaciente").text(registro.idPaciente);
				   		
				   	    $("#dialog-detalhes").dialog({
				   	    	width: 540,
				   	    	resizable: false,
				   	    	modal: true
				   	    });
				   	 	$( "#novaConsulta").attr("disabled","disabled");
				   	 	debug();
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
				$("#consulta-form").submit();
				$( this ).dialog( "close" );
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			$( this ).dialog( "close" );
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
	
	$( ".data").datepicker({
		autoSize:true,dateFormat:'dd/mm/yy'});
	
//	$( "#dataInicial").datepicker({
//		autoSize:true,dateFormat:'dd/mm/yy'});
//	
//	$( "#dataFinal").datepicker({
//		autoSize:true,dateFormat:'dd/mm/yy'});
	
	$("#lista-resultados").hide();

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
		$("#nomePaciente").text(MYAPP.agendamento.paciente.nome);
		$("#lista-resultados").jqGrid({
		   	url:'consulta/consultarHistorico',
		   	mtype: 'POST',
		   	postData: {idPaciente: $("#idPaciente").val(), idMedico: $("#idMedico").val(), 
			     dataInicial: $("#dataInicial").val(), dataFinal: $("#dataFinal").val()},
			datatype: "json",
		   	colNames:['Data','Médico'],
		   	colModel:[
				{name:'data',index:'data',hidden:true, width:60},
		   		{name:'nomeMedico',index:'medico',hidden:true, width:50, align:"right"},
		   	],
		   	onSelectRow: function(id){
				alert("selecionou");		
		   	},
		   	rowNum:10,
		   	rowList:[10],
		    viewrecords: true,
		    sortorder: "asc",
		    width: 200,
		    height: 200,
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
		$( "#historico-busca").dialog("open");
		
		$("#btn-consultaHist").click(function(){
			$("#lista-resultados").trigger("reloadGrid");
		});
		
		
	});
	
	function debug(){
		var agendamento = MYAPP.agendamento;
		console.log( agendamento.id + " " 
			   + agendamento.data + " "
			   + agendamento.horario + " "
			   + agendamento.nomeConvenio + " "
			   + agendamento.nomeFuncionario + " " 
			   + agendamento.paciente.id + " "
			   + agendamento.paciente.nome + " "
			   + agendamento.medico.id + " "
			   + agendamento.nome);
	}
	
});