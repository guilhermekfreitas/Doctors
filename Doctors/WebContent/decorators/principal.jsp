<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="sitemesh-decorator" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<link type="text/css" rel="stylesheet" href="css/frame.css"/>
		<link type="text/css" rel="stylesheet" href="css/style.css"/> <!-- Somente para exemplo. -->
		<title><sitemesh-decorator:title default="Projeto Doctors"/></title>
		<sitemesh-decorator:head/>
	</head>
	<body id="wrap">
		<div id="geral2"> <!-- 0 -->
    	  	<div id="topo">
    	  	 	<%@ include file="/WEB-INF/jsp/header.jsp" %>
			</div>      		
      		<div id="main">
	      		<div id="content">
	      			<sitemesh-decorator:body/>
	      			<div class="clear"></div>
	      		</div> <!-- 4 -->
			</div>

	      	<div id="rodape">
	      		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	      	</div> <!-- 6 -->
    	</div>
	</body>
</html>