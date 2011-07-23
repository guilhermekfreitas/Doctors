<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Convênios</title>
</head>
<body>
	<c:import url="../header.jsp"></c:import>

	<h1>Lista de Convênios</h1>

	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Alterar</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${convenios}" var="convenio">
				<tr>
					<td>${convenio.nome}</td>
					<td>
						<a href="edit?id=${convenio.id}">Alterar</a>
					</td>
					<td>
						<form action="remover">
							<input type="hidden" name="id" value="${convenio.id}" />
							<button id="Remover" type="submit">Remover</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="../convenios/">Voltar página inicial do módulo</a>
	<br />
</body>
</html>