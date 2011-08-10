<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
</head>
<body>
				<table id="lista-convenios" >
					<tr>Lista de convênios</tr>
					<tr></tr>
			    	<div class="row"><td> 
			    		<input type="hidden" name="conveniosId[0]" value="teste"/>
			    		<input type="text" readonly="readonly" name="convenioNome[0]" value="teste"/>
			    	</td>
			    	</div>
			    	<td>
			    		<img src="<c:url value='/img/remover.gif'/>" class="btn-remover-convenio"/>
			    	</td>
			    	<div class="row"><td> 
			    		<input type="hidden" name="conveniosId[1]" value="teste"/>
			    		<input type="text" readonly="readonly" id="convenioNome[1]" name="convenioNome[1]" value="teste2"/>
			    	</td>
			    	<td>
			    		<img src="<c:url value='/img/remover.gif'/>" class="btn-remover-convenio"/>
			    	</td>
			    	</div>
				</table>
				
				<input id="botao" name="meuinput" value="Acionar" type="button" />
				<input id="meuinput" name="meuinput" value="meu input" type="text" />
				
				<table id="hello">
				<div class="row">
					Hey Hey
				</div>
				</table>
				
		<script type="text/javascript">
			$('#botao').click(function(){
				$("#meuinput").attr("value", "hey hey");
				
				//$('input').attr("value", "mudei todos?").remove();
				
				$("#convenioNome[1]").remove();
				$("#lista-convenios").remove("#convenioNome[1]");
			});
			
			$('.btn-remover-convenio').click(function(){
				$(this).hide();
				$('.row').remove();
				$("input[name='convenioNome[1]']").remove();
			});
		</script>		
				
				
					
</body>
</html>