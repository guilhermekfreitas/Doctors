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
<script type="text/javascript" src="<c:url value='/js/jqgrid/grid.locale-pt-br.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jqgrid/jquery.jqGrid.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/jqgrid/ui.jqgrid.css"/>"/>
</head>
<body>
	<div class="calendario" id="calendario"></div>
	<div id="agenda">
		<table id="list2"></table>
		<div id="pager2"></div>
	</div>
	
	<div id="selecionou">
		<p>Voce selecionou registro</p> 
	</div>
	
	<script type="text/javascript">
	$("#selecionou").hide();
	
	$( "#calendario").datepicker({
			autoSize:true,dateFormat:'dd/mm/yy', minDate: 0, maxDate: "+2M", onSelect: function(dateText, inst){
				$("#dataAgendamento").attr("value",dateText);
				jQuery("#list2").jqGrid('setGridParam',{url:"getLista/"+dateText,page:1}).trigger("reloadGrid");
			}});
	
	jQuery("#list2").jqGrid({
	   	url:'getLista',
		datatype: "json",
	   	colNames:['Inv No','Date', 'Amount','Notes'],
	   	colModel:[
	   		{name:'id',index:'id', width:60},
	   		{name:'invdate',index:'invdate', width:90},
	   		{name:'amount',index:'amount', width:80, align:"right"},
	   		{name:'note',index:'note', width:150, sortable:false}		
	   	],
	   	onSelectRow: function(id){
	   	    $("#selecionou").dialog();
	   	},
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	sortname: 'id',
	   	pager: "#pager2",
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"JSON Example",
	    jsonReader : {
     		root: "rows",
     		page: "page",
     		total: "total",
    		records: "records",
   		    repeatitems: true,
   		    cell: "cells",
   		    id: "id",
   		    userdata: "userdata",
   		    subgrid: {root:"rows", 
    		    repeatitems: true, 
    	         cell:"cells"
             }}
	});
	jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});	
	</script>
</body>
</html>