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
	#resto{
		clear: both;
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
	
	<div id="agenda"  >
		Agenda do Médico:
		
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
					<tr>
						<td>Teste</td>
						<td>teste</td>
						<td>mais um</td>
						<td>outro</td>
					</tr>
				</tbody>
			</table>
			<button type="button" onclick="teste()">Teste</button>
		</div>
		
	</div>
	
	<div id="resto">
	<h1>Lista de Agendamentos</h1>

	<table>
		<thead>
			<tr>
				<th>Data</th>
				<th>Hora</th>
				<th>Confirmado</th>
				<th>Cancelado</th>
				<th>Nome do Paciente</th>
				<th>Nome do Médico</th>
				<th>Funcionário?</th>
				<th>Consulta</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${agendamentos}" var="agendamento">
				<tr>
					<td><joda:format value="${agendamento.dataAgendamento}"/></td>
					<td><joda:format value="${agendamento.horaAgendamento}" pattern="hh:mm"/></td>
					<td>
						<c:choose>
							<c:when test="${agendamento.confirmado}">Sim</c:when>
							<c:when test="${!agendamento.confirmado}">Não</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${agendamento.cancelado}">Sim</c:when>
							<c:when test="${!agendamento.cancelado}">Não</c:when>
						</c:choose>
					</td>
					<td>${agendamento.paciente.nome}</td>
					<td>${agendamento.medico.nome}</td>
					<td>${agendamento.funcionario.nome}</td>
					<td>
						${agendamento.consulta.id}
						<c:if test="${agendamento.isConsultaDisponivel()}">
							<c:choose>
								<c:when test="${ empty agendamento.consulta}">
									<a href="<c:url value="/consultas/novo/${agendamento.id}"/>">Iniciar Consulta</a>
								</c:when>
								<c:when test="${ !empty agendamento.consulta}">Já Efetuada</c:when>
							</c:choose>
						</c:if> 
					</td>
					<td><a href="<c:url value="/agenda/${agendamento.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/agenda/remover/${agendamento.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/agenda/novo"/>">Adicionar novo Agendamento</a><br />
	<a href="<c:url value="./"/>">Voltar à página inicial</a><br />
	</div>
	<script type="text/javascript">
		$("#agenda").hide();
		
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
			if (idMedico == 0){
				$("#dadosHorario").hide();
				$("#agenda").hide();
			} else {
				$("#agenda").show();
			$.getJSON('<c:url value="/agenda/carregaAgenda/' + idMedico + '"/>', function(json){
				//$("#horarios").find("option").remove();
				
				if (json.datas.length != 0){
					//$("#horarios").attr("disabled","");
					array = [];
					
					// popula array que contém as datas, de cada dia do calendário.
					for (var i in json.datas){
						
						var data = json.datas[i];
						
						// adiciona dia no array
						var dia = new Dia(data.data, data.horarios);
						array.push(dia);
						
						alert(data.data);
						
					}
					
					var dataAtual = $( "#calendario").val();
					//$("#dataAgendamento").attr("value",dataAtual);
					for (var i=0;i<array.length;i++)
					{
						// acertou o dia
						if (array[i].data == dataAtual){
							var horarios = array[i].horarios;
							for (var j=0;j<horarios.length;j++)	{
								
								addNovaLinha(horarios[j]);
								
								//var hora = criaOption(horarios[j]);
								//$("#horarios").append(hora);
							}
						}
					}
				//} else {
				//	$("#horarios").attr("disabled","disabled");
				}
				//$("#dadosHorario").show();
			});
			}
		});		
		
		
		$( "#calendario").datepicker({disabled: true,
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
		
		function addNovaLinha(horario,index){
			alert("teste");
			$("#lista-regs").find('tbody')
		    .append($('<tr>')
		        .append($('<td>').append(horario.horario))
		        .append($('<td>').append(horario.nomePaciente))
		       	.append($('<td>').append(horario.status))
		        .append($('<td>').append(criaBotao(index)))
		    );
		}
		
		function criaBotao(index){
			// cria botao verDetalhes
			return '<button type="button" onclick="verDetalhes(' + index + ')">Ver Detalhes</button>';
		}
		
		function teste(){
			alert("teste");
			$("#lista-regs").find('tbody')
		    .append($('<tr>')
		        .append($('<td>').append('hello'))
		        .append($('<td>').append('hello'))
		       	.append($('<td>').append('hello'))
		        .append($('<td>').append('hello'))
		    );
		}
	</script>	
</body>
</html>