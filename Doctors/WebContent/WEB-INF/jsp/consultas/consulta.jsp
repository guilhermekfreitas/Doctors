<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<c:url value='/js/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.14.custom.min.js'/>"></script>
<link rel="stylesheet"  type="text/css" href="<c:url value="/css/ui-lightness/jquery-ui-1.8.14.custom.css"/>"/>
<title>Nova Consulta</title>
<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		fieldset { padding:0; border:0; margin-top:25px; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .2em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
		div#relatorios {
			float:right;
			width: 30%;
		}
		div#form {
			float:left;
			width: 70%;
		}
		div#foot{
			clear:both;
		}
		.acoes{
			margin-bottom: 4px;
		}
</style>
<script type="text/javascript">
	$(function(){
	
		$("#receita-form").hide();
		$( "input:submit, a, button", ".consulta" ).button();
		
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
		var name = $( "#name" ),
			email = $( "#email" ),
			password = $( "#password" ),
			allFields = $( [] ).add( name ).add( email ).add( password ),
			tips = $( ".validateTips" );

		function updateTips( t ) {
			tips
				.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}

		function checkLength( o, n, min, max ) {
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-state-error" );
				updateTips( "Length of " + n + " must be between " +
					min + " and " + max + "." );
				return false;
			} else {
				return true;
			}
		}

		function checkRegexp( o, regexp, n ) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips( n );
				return false;
			} else {
				return true;
			}
		}
		
		$( "#dialog-form" ).dialog({
			autoOpen: false,
			closeOnEscape: false,
			height: 500,
			width: 850,
			modal: true,
			buttons: {
				"Finalizar": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );

					bValid = bValid && checkLength( name, "username", 3, 16 );
					bValid = bValid && checkLength( email, "email", 6, 80 );
					bValid = bValid && checkLength( password, "password", 5, 16 );

					bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "Username may consist of a-z, 0-9, underscores, begin with a letter." );
					// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
					bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
					bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

					if ( bValid ) {
						$( "#users tbody" ).append( "<tr>" +
							"<td>" + name.val() + "</td>" + 
							"<td>" + email.val() + "</td>" + 
							"<td>" + password.val() + "</td>" +
						"</tr>" ); 
						$( this ).dialog( "close" );
					}
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});

		$( "#create-user" )
			.button()
			.click(function() {
				$( "#dialog-form" ).dialog( "open" );
			});
		
		$( "#emit-receita" ).button().click(function() {
			$( "#receita-form" ).dialog({
				autoOpen: false,
				closeOnEscape: false,
				height: 370,
				width: 600,
				modal: true,
				buttons: {
					"Salvar": function() {
						$( this ).dialog( "close" );
					},
					"Imprimir": function() {
						$( this ).dialog( "close" );
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			$( "#receita-form").dialog("open");
		});
	
		
	});
</script>
</head>
<body>

<div class="demo">

<div id="dialog-form" class="consulta" title="Nova Consulta">
		<div id="form">
			<form>
				<fieldset>
					<h1>Dados do paciente:</h1>
					Nome do Paciente: Guilherme Kamizake de Freitas <button id="historico">Ver Histórico</button><br/>
					Idade: 19 (07/10/1991)
				</fieldset>
				<fieldset>
					<h1>Queixa Principal</h1>
					<textarea id="queixaprinc" rows="12" cols="80">Modelo de Documento aqui?</textarea><br/>
					
					<h1>Observações</h1>
					<textarea id="observacoes" rows="5" cols="80">[Observações da Consulta]</textarea>
				</fieldset>
			</form>
		</div>
		<div id="relatorios">
			<h1>Ações Disponíveis:</h1>
			<fieldset>
				<button id="emit-receita" type="button" class="acoes">Emitir Receita</button><br/>
				<button id="emit-atestado" class="acoes">Emitir Atestado</button><br/>
				<button id="solic-exame" class="acoes">Emitir Solicitação de Exame</button><br/>
			</fieldset>
			<fieldset>
				<h1>Lista de Relatórios Emitidos:</h1>
				<table class="ui-widget ui-widget-content">
					<thead>
						<tr class="ui-widget-header ">
							<th>Descrição              </th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr><th>Atestado</th><th><button id="edit">Editar</button></th><tr>
						<tr><th>Receita Médica</th><th><button id="edit">Editar</button></th><tr>
						<tr><th>Solicitação de Exame</th><th><button id="edit">Editar</button></th><tr>
					</tbody>
				</table>
			</fieldset>
		</div>
		<div id="foot">
		</div>
		
		
</div>

	<div id="receita-form" title="Nova Receita">
			<h1>Informações sobre Receita:</h1>
			<textarea rows="15" cols="110">[Informações sobre Receita]</textarea>
	</div>
		
<div id="users-contain" class="ui-widget">
	<h1>Existing Users:</h1>
	<table id="users" class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header ">
				<th>Name</th>
				<th>Email</th>
				<th>Password</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>John Doe</td>
				<td>john.doe@example.com</td>
				<td>johndoe1</td>
			</tr>
		</tbody>
	</table>
</div>
<button id="create-user">Create new user</button>

</div><!-- End demo -->



<div class="demo-description">
<p>Use a modal dialog to require that the user enter data during a multi-step process.  Embed form markup in the content area, set the <code>modal</code> option to true, and specify primary and secondary user actions with the <code>buttons</code> option.</p>
</div><!-- End demo-description -->



</body>
</html>