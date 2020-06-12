$(document).ready(
alert("Ok");
				function() {

					$("#estadoJS").submit(function(event) {
						event.preventDefault();
						submeterEstado();
					});

					function submeterEstado() {
						var campo = {
							nome : $("#nomeEstado").val(),
							sigla : $("#siglaEstado").val()
						}
						$.ajax({
									type : "POST",
									contentType : "application/json",
									url : "/administrativo/estados/salvarEstado",
									dataType : "json",
									data : JSON.stringify(campo),
									success : function(result) {

										if (result.id != null) {
											$("#mensagem")
													.html(
															"<div class=\"alert alert-success\" role=\"alert\"> Cadastrado com sucesso.</div>");
											campoNovo();
										} else {
											$("#mensagem")
													.html(
															"<div class=\"alert alert-danger\" role=\"alert\"> Algo deu errado, tente novamente!</div>");
										}

									},
									error : function(e) {
										console.log(e);
										alert("ERROR: " + e);
									}

								})

					}

					function campoNovo() {
						$("#nomeEstado").val("");
						$("#siglaEstado").val("");
					}

				}

		)