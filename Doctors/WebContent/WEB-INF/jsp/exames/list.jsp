<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Exames</title>
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
</head>
<body>
	<h1>Lista de Exames</h1>

	<table>
		<thead>
			<tr>
				<th>Descrição</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${exames}" var="exame">
				<tr>
					<td>${exame.descricao}</td>
					<td><a href="<c:url value="/exames/${exame.id}"/>">Alterar</a></td>
					<td><a href="<c:url value="/exames/remover/${exame.id}"/>">Remover</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="<c:url value="/exames/novo"/>">Adicionar novo Exame</a><br />
	<a href="<c:url value="./"/>">Voltar à página inicial</a><br />
</body>
</html>