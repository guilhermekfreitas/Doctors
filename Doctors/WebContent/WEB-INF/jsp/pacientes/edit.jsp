<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Paciente</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>

</head>
<body>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li>${error.category} - ${error.message}</li>
		</c:forEach>
	</ul>
	<form action="<c:url value="/pacientes/${paciente.id}"/>" method="post">
		<fieldset>
			<legend>Editar Paciente:</legend>

			<input id="id" type="hidden" name="paciente.id"	value="${paciente.id}" /> 
			<input id="id2" type="hidden" name="convenios"	value="${convenios}" />
			
			<label for="nome">Nome:</label> <input
				id="nome" type="text" name="paciente.nome" value="${paciente.nome}" /><br />
				
		    <label for="telefone">Telefone:</label> <input
				id="telefone" type="text" name="paciente.telefone" value="${paciente.telefone}" /><br />

			<label for="endereco">Endereço:</label> <input id="endereco" type="text"
				name="paciente.endereco" value="${paciente.endereco}" /><br />
				
			<label for="cpf">CPF:</label> <input id="cpf" type="text"
				name="paciente.cpf" value="${paciente.cpf}" /><br />

			<label for="email">Email:</label> <input id="email" type="text"
				name="paciente.email" value="${paciente.email}" /><br />
			
			<label for="dataNasc">Data
				de Nascimento:</label> <input id="dataNasc" type="text"
				name="paciente.dataDeNascimento" value="<fmt:formatDate value="${paciente.dataDeNascimento}" pattern="dd/MM/yyyy"/>" /> <br />
				
			<label for="login">Login:</label> <input id="login" type="text"
				name="paciente.login" value="${paciente.login}" /><br />
				
			<label for="senha">Senha:</label> <input id="senha" type="text"
				name="paciente.senha" value="${paciente.senha}" /><br />

			<label for="opcaoConvenios">Opção de Convênios:</label><br />
			<input type="radio" name="opcaoConvenios" value="Particular" <c:if test="${empty paciente.convenios}"> checked</c:if> onclick="hideConveniados()"> Particular<br>
			<input type="radio" name="opcaoConvenios" value="Conveniado" <c:if test="${!empty paciente.convenios}"> checked</c:if> onclick="showConveniados()"> Conveniado<br>
			<hr>

			<div id="divConvenios">
				<label for="convenios">Adicionar:</label>  
				<select id="convenios" name="convenioAtual">
				    <option value="">Selecione..</option>
					<c:forEach items="${convenios}" var="convenio">
						<%--<option value="${convenio.id}">${convenio.nome}</option> --%>
						
						<option <c:if test="${convenioAtual == convenio.id}"> selected="selected"</c:if>   
                 		 value="${convenio.id}">${convenio.nome}</option> 
					</c:forEach>
				</select> 
				<img src="<c:url value='/img/adicionar.gif'/>" onclick="adicionarConvenio();"/><br/>
				
				<table id="lista-convenios" >
					<tr>Lista de convênios</tr>
					<tr></tr>
					
			    	<c:forEach items="${paciente.convenios}" var="convenio" varStatus="status">
	            		<div data-index="${status.index}" class="convenio-item">
	            		<td>
	    	            	<input type="hidden" name="conveniosId[${status.index}]" value="${convenio.id}"/>
		                	<input type="text" readonly="readonly" name="convenioNome[${status.index}]" value="${convenio.nome}"/>
	                	</td>
	                	<td>
	                		<img src="<c:url value='/img/remover.gif'/>" class="btn-remover-convenio"/>
	                	</td><br />
	            	</div>
		        	</c:forEach>
				</table>	
			</div>
	        
	        <button type="submit" name="_method" value="PUT">Editar</button>
	        <a href="./"><button id="cancelar" type="submit" >Cancelar</button></a>
	    </fieldset>	
	</form>
	
	<script type="text/javascript">
		$('.btn-remover-convenio').live('click', function() {
			$(this).parent().remove();
		});
	
		function hideConveniados(){
			$('#divConvenios').hide();
		}
		
		function showConveniados(){
			$('#divConvenios').show();
		}
		
		function adicionarConvenio() {
			var $container	= $('#lista-convenios'),
				$convenios	= $container.children('.convenio-item'),
				firstIndex	= $convenios.first().data('index'),
				lastIndex	= $convenios.last().data('index');
	
			if (firstIndex === undefined) {
				firstIndex = 0;
				lastIndex = 0;
			}
	
			var index = parseInt(firstIndex) + parseInt(lastIndex) + 1;
			
			var indice = document.form.convenioAtual.selectedIndex
			var descricao = document.form.convenioAtual[indice].text
			var valor = document.form.convenioAtual.value
	
			$('<div class="convenio-item">' + 
				'<input type="hidden" name="conveniosId[' + index + ']" value="' + valor + '"/>' +											
				'<input type="text" readonly="readonly" name="convenioNome[' + index + ']" value="' +  descricao + '"/>' +
				'<img src=\'<c:url value="/img/remover.gif"/>\' alt="-" class="btn-remover-convenio"/>' +
			'</div>')
			.data('index', index)
			.appendTo($container);
		};
		</script>
</body>
</html>