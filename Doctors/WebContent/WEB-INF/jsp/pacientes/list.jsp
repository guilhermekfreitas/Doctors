<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Pacientes</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.tablesorter.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/blue-tablesorter/style.css"/>"/>
</head>
<body>
	<div id="lista-regs" class="ui-widget">
	<h1>Lista de Pacientes</h1>
	<table class="tablesorter">
		<thead>
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Telefone</th>
				<th>Data de Nascimento</th>
				<th>Login</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pacientes}" var="paciente">
				<tr>
					<td>${paciente.nome}</td>
					<td>${paciente.email}</td>
					<td>${paciente.telefone}</td>
					<td>${paciente.dataNascimento}</td>
					<td>${paciente.perfil.login}</td>
					<td><a class="btn-layout" href="<c:url value="/pacientes/${paciente.id}"/>">Alterar</a></td>
					<td><a type="button" class="btn-layout" onclick="return apagarPaciente(${paciente.id});">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<a class="btn-layout" href="<c:url value="/pacientes/novo"/>">Adicionar novo Paciente</a>
	<a class="btn-layout" href="<c:url value="./"/>">Voltar à página inicial</a><br />
	
	<a id="teste" href="<c:url value="./"/>">Teste</a><br />
	
	<div id="tes">
		Clique aqui para mudar o botao
	</div>
	
	<div id="btn-remove">Remover
	</div>
	
	<div style="display:none;" id="dialog-confirm" title="Deletar paciente?">
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			Este paciente será apagado permanentemente. Tem certeza?
		</p>
	</div>
	
	
	<p>Teste</p>
	
	<div id="div-convenios"></div>
	
	<script type="text/javascript">
	
		var numConv = -1;
	
		$(document).ready(function() 
			    { 
			        $(".tablesorter").tablesorter( {sortList: [[0,0]]} );
			        $("dialog-confirm").dialog("destroy");
			    } 
		);
		
		function apagarPaciente(idPaciente){
	        $( "#dialog-confirm" ).dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"Apagar": function() {
						$( this ).dialog( "close" );
						location.href = location.href + "/remover/"+idPaciente
						return true;
					},
					Cancel: function() {
						$( this ).dialog( "close" );
						return false;
					}
				}
			});
        }
		
		$('#tes').click(function(){
			numConv++;
			$('<input>', {
				name: 'convenios[' + numConv + ']',
				value: 'Unimedd',
			}).appendTo($('#div-convenios'));
			alert('value=' + $('input[name="convenios[' + numConv + ']"]').attr('value'));
			//$(this).slideUp();
			//$('.conv').remove();
			//$('input[name="convenios[0]"]').remove();
		});
		
		$("#btn-remove").click(function(){
				$('input[name="convenios[' + numConv + ']"]').remove();
				numConv--;
		});
		
		$('<input>', {
			id: 'convenios[10]',
			'class': 'conv',
			name: 'convenios[10]',
			value: 'Cassii',
		}).appendTo($('#div-convenios'));
		$('<button>',{
			value: '+',
			type: 'button'
		}).appendTo($('#div-convenios'));
		$('<br>').appendTo($('#div-convenios'));
	</script>
</body>
</html>