$(document).ready(function(){
	
		$("#dadosHorario").hide();
		$("#agenda").hide();

		$( "#calendar").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				
				atualizaCampoData(dateText);
				//$("#dataAgendamento").attr("value", dateText);
				atualizaGrid();
			}});
				
		$("#medicos").change(function(){
			var idMedico = $("#medicos").val();
			if (idMedico == 0){
				$("#agenda").hide();
			} else {
				$("#agenda").show();
				
				atualizaCampoData($("#calendar").val());
				//$("#dataAgendamento").attr("value", $("#calendar").val());
				
				$("#horariosLivres").jqGrid({
				   	url:'carregaHorarios.json',
				   	mtype: 'POST',
				   	postData: {idMedico: $("#medicos").val(), data: $("#calendar").attr("value")},
					datatype: "json",
				   	colNames:['Horários Livres'],
				   	colModel:[
				   		{name:'horario',index:'horario', sortable:false, width:70, align:"center"}
				   	],
				   	onSelectRow: function(horario){

					   	var registro = $("#horariosLivres").jqGrid().getRowData(horario);
					   	$("#horaAgendamento").attr("value",registro.horario);
				   	},
				   	rowNum:20,
				   	rowList:[20],
				   	sortname: 'horario',
				    viewrecords: true,
				    sortorder: "asc",
				    width: 130,
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
				
				atualizaGrid();				
			
			}
		});		
		
		/*$("#enviar").click(function(){
			//alert(JSON.stringify($('#form').serializeObject()));
			//alert($("#form").serializeArray());
			
			$.ajax({
				url: 'preagendar',
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8', 
				type: 'post',
				data: {
					"agendamento.paciente.id":$("[name=agendamento.paciente.id]").val(),
					"agendamento.medico.id": $("#medicos").val(),
					"agendamento.convenio.id": $("#convenios").val(),
					"agendamento.horaAgendamento": $("#horaAgendamento").val(),
					"agendamento.dataAgendamento": $("#dataAgendamento").val()
				}, //JSON.stringify($('#form').serializeObject()),
				dataType: 'json',
				success: function(data){
					console.log(data);
				},
				error: function(data,status,errorThrown){
					console.log(data.responseText);
					
					var resposta = $.parseJSON(data.responseText);
					for (var i in resposta.errors){
						var erro = resposta.errors[i];
						console.log(erro.message + " " + erro.category);
						$("#erros").append(erro.category + " - " + erro.message + "<br />" );
						//console.log($("name='"+erro.category+"'").val());
					}
					
				} 
			});	
			
			
		}); */
						
		function showCaption(){
			return "Dia: " + $("#calendar").attr("value"); 
		}
		
		function atualizaGrid(){
			$("#horariosLivres").jqGrid('clearGridData');
			$("#horariosLivres").jqGrid().setGridParam({postData: {idMedico: $("#medicos").val(), data: $("#calendar").attr("value")}});
			$("#horariosLivres").jqGrid('setCaption',showCaption());
			$("#horariosLivres").trigger("reloadGrid");
		}
		
		function atualizaCampoData(dateText){
			$("#dataAgendamento").attr("value", dateText);
		}
});