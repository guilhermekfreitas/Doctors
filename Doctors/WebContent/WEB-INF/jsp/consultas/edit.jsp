<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Consulta</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>

</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>
	<form action="<c:url value="/consultas/${consulta.id}"/>" method="post">
		<fieldset>
			<legend>Editar Consulta:</legend>

			<input type="hidden" name="consulta.agendamento.id" value="${consulta.agendamento.id}"/>

			<label for="paciente">Paciente:</label> <input id="paciente" type="text"
				 value="${consulta.agendamento.paciente.nome}" disabled="disabled" size="40"/><br /> 

			<label for="medico">Médico:</label> <input id="medico" type="text"
				 value="${consulta.agendamento.medico.nome}" disabled="disabled" size="40"/><br /> 			

			<label for="queixaPrincipal">Queixa Principal:</label><br /> 
			<textarea id="queixaPrincipal" rows="4" cols="30" name="consulta.queixaPrincipal">${consulta.queixaPrincipal}</textarea><br /> 

			<label for="observacoes">Observações:</label><br /> 
			<textarea id="observacoes" rows="4" cols="30" name="consulta.observacoes">${consulta.observacoes}</textarea><br /> 

			<fieldset id="exame">
				<div id="novoExame">Novo Exame</div>
			</fieldset>
			
			<label
				for="novoExame">Solicitar Exame:</label>	
				<input type="text" id="novoExame" name="novoExame" />
				<img src="<c:url value='/img/adicionar.gif'/>" onclick="adicionarExame();"/>
				
			<fieldset id="lista-exames">
				Exames solicitados: <br />
		    	<c:forEach items="${consulta.exames}" var="exame" varStatus="status">
            	<div data-index="${status.index}" class="exame-item">
            		Descrição:
                	<input type="text" readonly="readonly" name="exames[${status.index}]" value="${exame.descricao}"/>
                	<img src="<c:url value='/img/remover.gif'/>" class="btn-remover-exame"/>
            	</div>
	        </c:forEach>
			</fieldset>	

			<button type="submit" name="_method" value="PUT">Editar</button>
			
		</fieldset>
	</form>
	<script type="text/javascript">
			$('.btn-remover-exame').live('click', function() {
				$(this).parent().remove();
			});

			function adicionarExame() {
				var $container	= $('#lista-exames'),
					$convenios	= $container.children('.exame-item'),
					firstIndex	= $convenios.first().data('index'),
					lastIndex	= $convenios.last().data('index');

				if (firstIndex === undefined) {
					firstIndex = 0;
					lastIndex = 0;
				}

				var index = parseInt(firstIndex) + parseInt(lastIndex) + 1;
				
				var descricao = document.form.novoExame.value
				
				$('<div class="exame-item">' + 							
					'<input type="text" readonly="readonly" name="exames[' + index + ']" value="' +  descricao + '"/>' +
					'<img src=\'<c:url value="/img/remover.gif"/>\' alt="-" class="btn-remover-exame"/>' +
				'</div>')
				.data('index', index)
				.appendTo($container);
				
				document.form.novoExame.value = "";
			};
		</script>	
</body>
</html>