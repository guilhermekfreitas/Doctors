<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Agendamentos</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
<style type="text/css">
	#agenda{
	}
	#calendario{
		float:left;
		margin-right: 10px;
	}
	#lista-regs{
		
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
	
	<table id="list2"></table>
	<div id="pager2"></div>
	
	<div id="agenda"  >
		Agenda do M�dico:
		
		<div class="calendario" id="calendario">
		</div>
		
		<div id="lista-regs">
			<table class="tablesorter">
				<thead>
					<tr>
						<th>Horario</th>
						<th>Nome do Paciente</th>
						<th>Status</th>
						<th>Ver detalhes</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
	</div>
	
	<script type="text/javascript">

		$("#agenda").hide();
		
		
		$("#medicos").change(function(){
			var idMedico = $("#medicos").val();
			if (idMedico == 0){
				$("#agenda").hide();
			} else {
				$("#agenda").show();
				$.getJSON('<c:url value="/agenda/carregaAgenda/' + idMedico + '"/>', function(json){
				
				if (json.datas.length != 0){
					array = [];
					
					// popula array que cont�m as datas, de cada dia do calend�rio.
					for (var i in json.datas){
						
						var data = json.datas[i];
						
						// adiciona dia no array
						var dia = new Dia(data.data, data.horarios);
						array.push(dia);
						
					}
					
					//minDate = $("#calendario").datepicker( "option", "minDate" );
					//$( "#calendario" ).val = minDate;
					
					$("#lista-regs tr:gt(0)").remove();
					var dataAtual = $( "#calendario").val();
					for (var i=0;i<array.length;i++)
					{
						// acertou o dia
						if (array[i].data == dataAtual){
							var horarios = array[i].horarios;
							for (var j=0;j<horarios.length;j++)	{
								
								addNovaLinha(horarios[j]);
								
							}
						}
					}
				}
			});
			}
		});		
		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				//$("#dataAgendamento").attr("value",dateText);
				
				// quando alterar aqui, deve apagar hor�rios
				// da tabela
				$("#lista-regs tr:gt(0)").remove();
				
				// posso acessar por aqui
				for (var i=0;i<array.length;i++)
				{
					// acertou o dia
					if (array[i].data == dateText){
						var horarios = array[i].horarios;
						for (var j=0;j<horarios.length;j++)	{
							addNovaLinha(horarios[j]);
						}
					}
				}
				
			}});	
		
		function Dia(data,horarios) {
			this.data = data;
			this.horarios = horarios;
		}
		
		function addNovaLinha(horario,index){
			$("#lista-regs").find('tbody')
		    .append($('<tr>')
		        .append($('<td>').append(horario.horario))
		        .append($('<td>').append(horario.nomePaciente))
		       	.append($('<td>').append(horario.status))
		        .append($('<td>').append(criaLinkConfirmacao(horario)))
		        .append($('<td>').append(criaBotao(index)))
		    );
		}
		
		function criaBotao(index){
			// cria botao verDetalhes
			return '<button type="button" onclick="verDetalhes(' + index + ')">Ver Detalhes</button>';
		}
		
		function criaLinkConfirmacao(horario){
			
			return '<a href="<c:url value="/agenda/confirmaAgendamento/' + horario.id + '"/>">Confirmar Agendamento</a>';
			
		}
		
		
		jQuery("#list2").jqGrid({
		   	url:'server.php?q=2',
			datatype: "json",
		   	colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
		   	colModel:[
		   		{name:'id',index:'id', width:55},
		   		{name:'invdate',index:'invdate', width:90},
		   		{name:'name',index:'name asc, invdate', width:100},
		   		{name:'amount',index:'amount', width:80, align:"right"},
		   		{name:'tax',index:'tax', width:80, align:"right"},		
		   		{name:'total',index:'total', width:80,align:"right"},		
		   		{name:'note',index:'note', width:150, sortable:false}		
		   	],
		   	rowNum:10,
		   	rowList:[10,20,30],
		   	pager: '#pager2',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"JSON Example"
		});
		jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
		
		
		
	</script>	
</body>
</html>