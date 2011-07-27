<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Convênios</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/scripts.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>  
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/meucss.css"/>"/>
</head>
<body>
	<c:import url="../header.jsp"></c:import>
	
	<div id="lista-regs" class="ui-widget">
	<h1>Lista de Convênios</h1>
	<table id="regList" class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header">
				<th>Nome</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${convenios}" var="convenio">
				<tr>
					<td>${convenio.nome}</td>
					<td><a href="<c:url value="/convenios/${convenio.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/convenios/remover/${convenio.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<a id="submit-button" href="<c:url value="/convenios/novo"/>">Adicionar novo Convênio</a>
	<a id="cancel-operation" href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>