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
	   						this.nomeFuncionario = registro.nomeFuncionario;
	   						this.paciente.id = registro.idPaciente,
	   						this.paciente.nome = registro.nomePaciente;
	   						this.paciente.dataNasc = registro.dataNascimento;
	   						this.medico.id = registro.idMedico;
	   						this.medico.nome = registro.nomeMedico;
	   					},
	   			getDadosConsulta: function(){
	   				
	   					},
	   		    habilitaTodos: function(){
		   		    	$("#btnConfirmar").button( "option", "disabled", false);
		   		    	$("#btnIniciar").button( "option", "disabled", false);
		   		    	$("#btnNotificar").button( "option", "disabled", false);
		   		    	$("#btnTransferir").button( "option", "disabled", false);
		   		    },
		   		desabilita: function(nome){
			   			$(nome).button( "option", "disabled", true);
			   		},
			   	habilita: function(nome){
			   			$(nome).button( "option", "disabled", false);
			   	}
				};
		MYAPP.historico = {
				url: 'consulta/consultarHistorico',
				showCaption: function() {
					return "Resultados da busca";
				},
				getPostData: function() {
					return {
						idPaciente: MYAPP.agendamento.paciente.id, 
						idMedico: $("#idMedico").val(), 
						dataInicial: $("#dataInicial").val(), 
						dataFinal: $("#dataFinal").val()
						};
					}
		};
		MYAPP.consulta = {
				// atributos
				documentos: [],
				exames: [],
				atestados: [],
				receitas: [],
				total: 0,
				// funcoes
				addDocumento : function(documento,tipo){
					
					this.total++;
					switch(tipo){
						case "exame":
							this.add(this.exames,documento,tipo);
							this.show(this.atestados);
							break;
						case "atestado":
							this.add(this.atestados,documento,tipo);
							this.show(this.atestados);
							break;
						case "receita":
							this.add(this.receitas,documento,tipo);
							this.show(this.receitas);
							break;
					}
					
//					var indice = this.documentos.length;
//					// adiciona na div (dados + botao)
//					$("#list-documentos > tbody").append("<tr><td>" + (indice+1) + "</td><td>" + tipo + "</td><td>editar</td></tr>");
//					$("#consulta-form").append($('<input>',{
//						name: 'consulta.' + tipo + 's[' + indice + '].descricao',
//						value: documento
//					}));
//					this.documentos.push(documento);
				},
				showDocumentos : function(){
						for (var i in this.documentos){
							console.log(this.documentos[i]);
						}
				},
				show: function(array){
						for (var i in array){
							console.log(array[i]);
						}
				},
				add: function(array,documento,tipo){
						var indice = array.length;
						$("#list-documentos > tbody").append("<tr><td>" + this.total + "</td><td>" + 
															tipo + "</td><td><button type='button' class='button'>Editar</button></td></tr>");
						$("#consulta-form").append($('<input>',{
							name: 'consulta.' + tipo + 's[' + indice + '].descricao',
							value: documento
						}));
						array.push(documento);
				}
		};
		
	
		$("#agenda").hide();
		$( ".hidden" ).hide();
		$( ".dialog" ).hide();
		$( ".button" ).button();
		$( ".editDoc" ).button();

		$( ".editDoc").click(function(){
			alert("falta implementar");
		});
		
		$( "#btnConfirmar" ).click(function(){
			
			$("#dialog-confirmacao").dialog({
				modal:true,
				width: 300,
				buttons: {
					"Confirmar": function(){
						$.ajax({
							url: 'agenda/confirmaAgendamento/' + $( '#idAgendamento' ).text(),
							type: 'GET',
							dataType: 'json',
							success: function(data){
								exibeMsgSucesso({
									titulo: "Confirmação de Agendamento",
									mensagem: "O agendamento foi confirmado com sucesso."
								});
								 $("#dialog-confirmacao").dialog( "close" );
								 $("#dialog-detalhes").dialog( "close" );
								 atualizaGrid();
							},
							error: function(data){
								alert('ERRO!');
							} 
						});	
					},
					"Cancelamento": function(){
						$.ajax({
							url: 'agenda/cancelaAgendamento/' + $( '#idAgendamento' ).text(),
							type: 'GET',
							dataType: 'json',
							success: function(data){
								
								exibeMsgSucesso({
									titulo: "Cancelamento de Agendamento",
									mensagem: "O agendamento foi cancelado com sucesso."
								});
								 $("#dialog-confirmacao").dialog( "close" );
								 $("#dialog-detalhes").dialog( "close" );
								 atualizaGrid();
							},
							error: function(data){
								alert('ERRO!');
							} 
						});	
					},
					"Voltar": function(){
						$(this).dialog("close");
					}
				}
			});
		});
		
		$( "#btnTransferir" ).click(function(){
			$("#novaData").datepicker({
				autoSize:true,dateFormat:'dd/mm/yy', beforeShowDay: $.datepicker.noWeekends, minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
					
					$("#horariosList").recarregaGrid({
						postData:  {idMedico: MYAPP.agendamento.medico.id,data: dateText},
					});
					
				}
			});
			
			jQuery("#horariosList").jqGrid({
			   	url:'agenda/carregaHorarios.json',
			   	mtype: 'POST',
			   	postData: {idMedico: MYAPP.agendamento.medico.id, data: $("#novaData").attr("value")},
				datatype: "json",
			   	colNames:['Horários Livres'],
			   	colModel:[
			   		{name:'horario',index:'horario', sortable:false, width:70, align:"center"}
			   	],
			   	onSelectRow: function(horario){

				   	var registro = $("#horariosList").jqGrid().getRowData(horario);
				   	$("#novaHora").attr("value",registro.horario);
			   	},
			   	rowNum:20,
			   	rowList:[20],
			   	sortname: 'horario',
			    viewrecords: true,
			    sortorder: "asc",
			    width: 130,
			    height: 200,
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
			
			$("#dialog-transferir").dialog({
				modal:true,
				width: 430,
				buttons: {
					"Transferir": function(){
						$.ajax({
							url: 'agenda/transferirHorario',
							type: 'POST',
							data: { idAgendamento: MYAPP.agendamento.id,
										novaData: $("#novaData").val(),
										novaHora: $("#novaHora").val()},
							success: function(data){
								
								exibeMsgSucesso({
									titulo: "Transferir Horário de Consulta",
									mensagem:"Consulta transferida com sucesso!"
								});
								
								 $("#dialog-transferir").dialog( "close" );
								 $("#dialog-detalhes").dialog( "close" );
								 atualizaGrid();
							},
							error: function(data){
								alert('ERRO!');
							} 
						});	
						//$(this).dialog("close");
						//$("#dialog-detalhes").dialog("close");
					},
					"Cancelar": function(){
						$(this).dialog("close");
					}
				}
			});
			
			
		});
		
		$( "#btnNotificar" ).click(function(){
			//alert("Falta implementar");
			//console.log(MYAPP.agendamento.id);
			postAjax({
				url:'notificacoes/notifica',
				type:'POST',
				postData:{idAgendamento: MYAPP.agendamento.id},
				success: function(){
					exibeMsgSucesso({
						titulo: "Notificação de chegada",
						mensagem:"Notificação realizada com sucesso!"
					});
				}
			});
			$("#dialog-detalhes").dialog("close");
			atualizaGrid();
			
		});
		$( "#btnIniciar" ).click(function(){
			$("#agendamentoId").val($("#idAgendamento").text());
			// reinicializa consulta, senao fica com dados de antes
//			MYAPP.consulta
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
			   	colNames:['ID do Agendamento','Data','Horário','','Nome do Paciente', 'Status','Convenio','','Médico','','',''],
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
			   		{name:'nomeFuncionario',index:'nomeFuncionario',hidden:true, width:50, align:"right"},
			   		{name:'dataNascimento',index:'dataNascimento',hidden:true, width:50, align:"right"}
			   	],
			   	onSelectRow: function(id){
			   		
				   	var registro = $("#agendamentos").jqGrid('getRowData',id);
				   	
				   	if (registro.status !== "Cancelado" && registro.status !== "Livre" && registro.status !== "Finalizado"){
				   		
				   		MYAPP.agendamento.carrega(registro);
				   		MYAPP.agendamento.habilitaTodos();
				   		debug();
				   		
				   		var dados = MYAPP.agendamento;
				   		
				   		console.log(dados.paciente.nome);
				   		
				   		$("#idAgendamento").text(dados.id);
				   		$("#nomePaciente").text(dados.paciente.nome);
				   		$("#nomeMedico").text(dados.medico.nome);
				   		$("#dataConsulta").text(dados.data);
				   		$("#horaConsulta").text(dados.horario);
				   		$("#nomeConvenio").text(dados.nomeConvenio);
				   		$("#nomeFuncionario").text(dados.nomeFuncionario);
				   		$("#idPaciente").text(dados.paciente.id);
				   		$('label[class="nomePaciente"]').text(dados.paciente.nome);
				   		$('label[class="dataNascimento"]').text(dados.paciente.dataNasc);
				   		$('input[class="idPaciente"]').text(dados.paciente.id);
				   		
				   		if (registro.status == "Confirmado"){
				   			MYAPP.agendamento.desabilita("#btnConfirmar");
				   		}
				   		if (registro.status == "A Confirmar"){
				   			MYAPP.agendamento.desabilita("#btnIniciar");
				   			MYAPP.agendamento.desabilita("#btnNotificar");
				   		}
				   		
				   	    $("#dialog-detalhes").dialog({
				   	    	width: 570,
				   	    	resizable: false,
				   	    	closeOnEscape: false,
				   	    	modal: true
				   	    });
				   	    
				   	    MYAPP.agendamento.desabilita("#novaConsulta");
				   	} else {
				   		if (registro.status == "Livre"){
				   			MYAPP.agendamento.habilita("#novaConsulta");
				   		} else {
				   			MYAPP.agendamento.desabilita("#novaConsulta");
				   		}
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
			
			$("#agendamentos").recarregaGrid({
				postData: {idMedico: $("#medicos").val(), data: $("#calendario").attr("value")},
				caption : showCaption()
			});
			
			}
		});		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', beforeShowDay: $.datepicker.noWeekends, minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
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
				$("#dialog-detalhes").dialog( "close" );
			},
			"Cancelar": function() {
				$( "#dialog:ui-dialog" ).dialog( "destroy" );
				$( "#dialog-confirm" ).dialog({
					resizable: false,
					height:140,
					modal: true,
					buttons: {
						"Sair": function() {
							$( this ).dialog( "close" );
							$( "#dialog-form" ).dialog( "close" );
						},
						"Cancelar": function() {
							$( this ).dialog( "close" );
						}
					}
				});
			}
		},
		close: function() {
			$( this ).dialog( "close" );
		}
	});
	
	$( "#emit-receita" ).button().click(function() {
		novoDocumento("receita", "Nova Receita");
	});
	
	$( "#emit-atestado" ).button().click(function() {
		novoDocumento("atestado", "Novo Atestado");
	});
	
	$( "#solic-exame" ).button().click(function() {
		novoDocumento("exame", "Novo Exame");
	});
	
	$( ".data").datepicker({
		dateFormat:'dd/mm/yy',
		showOn: "button",
		buttonImage: "img/calendar.png",
		buttonImageOnly: true});
	
	var dates = $( "#dataInicial, #dataFinal" ).datepicker({
		dateFormat: 'dd/mm/yy',
		maxDate: 0,
		defaultDate: "-1m",
		changeMonth: true,
		numberOfMonths: 1,
		onSelect: function( selectedDate ) {
			var option = this.id == "dataInicial" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
			dates.not( this ).datepicker( "option", option, date );
		}
	});
	
	$("#lista-resultados").hide();

	$( "#historico" ).button().click(function() {
		
		console.log(MYAPP.agendamento.paciente.id);
		$("#idPaciente").text(MYAPP.agendamento.paciente.id);
		$( "#historico-busca" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 370,
			width: 600,
			modal: true,
			buttons: {
				"Fechar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$("#nomeDoPaciente").attr("value",MYAPP.agendamento.paciente.nome);
		
		$( "#historico-busca").dialog("open");
	});
	
	$("#btn-consultaHist").click(function(){
		
		jQuery("#lista-resultados").jqGrid({
		   	url: MYAPP.historico.url,
		   	mtype: 'POST',
			postData: MYAPP.historico.getPostData(),
			datatype: "json",
		   	colNames:['Data','Médico'],
		   	colModel:[
				{name:'data',index:'data',width:60,sortable:false},
		   		{name:'nomeMedico',index:'nomeMedico',width:50,sortable:false, align:"right"},
		   	],
		   	onSelectRow: function(id){
				alert("selecionou");		
		   	},
		   	rowNum:10,
		   	rowList:[10],
		    viewrecords: true,
		    width: 200,
		    height: 200,
		    caption: MYAPP.historico.showCaption(),
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

		$("#lista-resultados").recarregaGrid({
			postData: MYAPP.historico.getPostData(),
			caption : MYAPP.historico.showCaption()
		});
	});
	
	function debug(param){
		
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
	
	function showDocumentos(){
		MYAPP.consulta.showDocumentos();
	}
	
	function novoDocumento(tipo,titulo){
		
		$("#documento-info").attr("value","[Modelo]");
		$( "#documento-form" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			title: titulo,
			height: 400,
			width: 600,
			modal: true,
			buttons: {
				"Salvar": function() {
					var documento = $("#documento-info").val();
					MYAPP.consulta.addDocumento(documento,tipo);
					$( this ).dialog( "close" );
				},
				"Imprimir": function() {
					var linhas = $("#documento-info").val().split("\n");
					$("#impressao").empty();
					for(var i in linhas){
						$("#impressao").append(linhas[i]).append("<br />");
					}	
					$("#impressao").printArea();
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$( "#documento-form").dialog("open");
	}
	
	function exibeMsgSucesso(config){
		$("#mensagem").empty().append(config.mensagem);
		$("#dialog-msg-sucesso").dialog({
			modal:true,
			resizable: false,
			title: config.titulo,
			width: 250,
			height: 170,
			buttons: {
				Ok: function(){
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
	function postAjax(config){
		
		if (typeof config.postData === 'undefined'){
			config.postData = {};
		}
		
//		console.log(config.postData.idAgendamento);
		
		$.ajax({
			url: config.url,
			type: config.type,
			data: config.postData,
			dataType: 'json',
			success: config.success(),
			error: function(data){
				alert('ERRO!');
			} 
		});	
	}
	
	
});
