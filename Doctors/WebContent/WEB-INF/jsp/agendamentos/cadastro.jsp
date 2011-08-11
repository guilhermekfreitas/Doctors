<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar novo Agendamento:</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
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
			
			<table>
			<tr><td><label for="pacientes">Paciente:</label></td> 
			<td><select id="pacientes" name="agendamento.paciente.id">
				<option value="">Selecione um paciente..</option>
				<c:forEach items="${pacientes}" var="paciente">
				<option value="${paciente.id}">${paciente.nome}</option>
				</c:forEach>
			</select></td></tr>

			<tr><td><label for="medicos">Médico:</label></td> 
			<td><select id="medicos" name="agendamento.medico.id">
				<option value="">Selecione um médico..</option>
				<c:forEach items="${medicos}" var="medico">
				<option value="${medico.id}">${medico.nome}</option>
				</c:forEach>
			</select></td></tr>

			<tr><td><label for="convenios">Convênio:</label></td>
			<td><select id="convenios" name="agendamento.convenio.id" disabled="disabled">	</select>
				<img id="loading" alt="Carregando" src="<c:url value="/img/loading.gif"/>">
			</td></tr><br />

			<tr><td><label for="horaAgendamento">Hora:</label></td> <td><input id="horaAgendamento" type="text"
				name="agendamento.horaAgendamento" value='${agendamento.horaAgendamento}'/></td></tr><br /> 
			
			<tr><td><label for="dataAgendamento">Data:</label></td> <td><input id="dataAgendamento" type="text"
				name="agendamento.dataAgendamento" value='<joda:format value="${agendamento.dataAgendamento}"/>'  /></td></tr><br /> 
			
			<tr><td><label for="calendario">Calendário:</label></td> 
				<td><div id="calendario"></div></td></tr><br /> 
			
			<tr><td><label for="horarios">Horários Disponíveis:</label></td><td><select id="horarios" size="10" type="text"
				name="horarios">
					<option value="08:30">08:30</option>
					<option value="09:00">09:00</option>
					<option value="09:30">09:30</option>
					<option value="10:00">10:00</option>
				</select></td></tr><br /> 
			
			<!--  Falta inserir:
			    - paciente;
			    - médico;
			 -->
			 </table>
			<button id="enviar" type="submit">Enviar</button>
		</fieldset>
	</form>
	
	<script type="text/javascript">
		$("#loading").hide();
		$('#loading').ajaxStart(function() {
		       $(this).show();
		   });
		$('#loading').ajaxStop(function(){
		       $(this).hide();
		   });
		
		$("#pacientes").change(function(){
			var idPaciente = $("#pacientes").val();
			$.getJSON('<c:url value="/agenda/carregaConvenios/' + idPaciente + '"/>', function(json){
				$("#convenios").find("option").remove();
				if (json.list.length != 0){
					for (var i in json.list){
						$("#convenios").attr("disabled","");
						var convenio = json.list[i];
						var novoItem = '<option value="'+ convenio.id + '">' + convenio.nome + '</option>';
						$("#convenios").append(novoItem);
					}
				} else {
					$("#convenios").append('<option value="0">Particular</option>');
					$("#convenios").attr("disabled","disabled");
				}
			});
		});
		
		$("#medicos").change(function(){
			var idMedico = $("#medicos").val();
			$.getJSON('<c:url value="/agenda/carregaHorarios/' + idMedico + '"/>', function(json){
				$("#horarios").find("option").remove();
				if (json.datas.length != 0){
					$("#horarios").attr("disabled","");
					array = [];
					for (var i in json.datas){
						
						var data = json.datas[i];
						
						// adiciona dia no array
						var dia = new Dia(data.data, data.horarios);
						array.push(dia);
						
					}
					
					var dataAtual = $( "#calendario").val();
					$("#dataAgendamento").attr("value",dataAtual);
					for (var i=0;i<array.length;i++)
					{
						// acertou o dia
						if (array[i].data == dataAtual){
							var horarios = array[i].horarios;
							for (var j=0;j<horarios.length;j++)	{
								var hora = criaOption(horarios[j]);
								$("#horarios").append(hora);
							}
						}
					}
				} else {
					$("#horarios").attr("disabled","disabled");
				}
				
			});
			//var output = {};
			// carregar todos os horários do médico, p/ cada data especifica.
			//output['testKey'].testValue = 45;  
			
		});		
		
		$("#horarios").change(function(){
			var horarioText = $(this).val();
			$("#horaAgendamento").attr("value",horarioText);
			
		});		
		
		$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 1, maxDate: "+2M", onSelect: function(dateText, inst){
				$("#dataAgendamento").attr("value",dateText);
				
				// quando alterar aqui, deve apagar horários
				// e preencher o #horarios com novos dados.
				$("#horarios").find("option").remove();
				
				// posso acessar por aqui
				for (var i=0;i<array.length;i++)
				{
					// acertou o dia
					if (array[i].data == dateText){
						var horarios = array[i].horarios;
						for (var j=0;j<horarios.length;j++)	{
							var hora = criaOption(horarios[j]);
							$("#horarios").append(hora);
						}
					}
				}
				
			}});
		
		function Dia(data,horarios) {
			this.data = data;
			this.horarios = horarios;
		}
		
		function criaOption(valor){
			return '<option value="'+ valor  + '">' + valor + '</option>';
		}
		
	</script>
</body>
</html>