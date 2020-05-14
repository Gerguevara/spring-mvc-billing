
// inicializador de toadas las funciones
$(document).ready(function() {

// MDB inicializaciones	
inicializerMDB();	
	
//Auto complete
	$("#buscar_producto").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : "/factura/cargar-productos/" + request.term,
				dataType : "json",
				data : {
					term : request.term
				},
				success : function(data) {
					response($.map(data, function(item) {
						return {
							value : item.id,
							label : item.nombre,
							precio : item.precio,
						};
					}));
				},
			});
		},
		select : function(event, ui) {
			
			//$("#buscar_producto").val(ui.item.label);			
			// verifica si el producto existe
			
			if(itemsHelper.hasProducto(ui.item.value)){
				itemsHelper.incrementaCantidad(ui.item.value, ui.item.precio);
				return false;
			}			
			var linea = $("#plantillaItemsFactura").html();
			
			linea = linea.replace(/{ID}/g, ui.item.value);
			linea = linea.replace(/{NOMBRE}/g, ui.item.label);
			linea = linea.replace(/{PRECIO}/g, ui.item.precio);
			
			// calcula el imposte de la cantidad
			$("#cargarItemProductos tbody").append(linea);
			itemsHelper.calcularImporte(ui.item.value,ui.item.precio,1);

			return false;
		}
	});	//Auto complete FIN
	
	$("form").submit(function() {
		$("#plantillaItemsFactura").remove();
		return;
	})
});



//declaraciones de helpers para auto complete
var itemsHelper = {
		// calcula el importe producto x cantidad y es llamado luego
		calcularImporte: function(id, precio, cantidad){
			$("#total_importe_" + id).html(parseInt(precio) * parseInt(cantidad));	
			
				this.calcularGranTotal();
		},
		// verifica por medio del id si el producto ya existe
		hasProducto: function(id){			
			var resultado = false;			
			$('input[name="item_id[]"]').each(function(){
				if(parseInt(id) == parseInt($(this).val()) ){
					resultado = true;
				}
			});			
			return resultado;
		},   // incrementa la cantidad
		incrementaCantidad: function(id, precio){
			var cantidad = $("#cantidad_" + id).val() ? parseInt($("#cantidad_" + id).val()) : 0;
			$("#cantidad_" + id).val(++cantidad);
			this.calcularImporte(id, precio, cantidad);
		},
		// elimina producto
		eliminarLineaFactura: function(id){
			$("#row_" + id).remove();
			this.calcularGranTotal();
		},
		// calcula el total general
		calcularGranTotal: function(){
			var total = 0;
			
			$('span[id^="total_importe_"]').each(function(){
				total += parseInt($(this).html());
			});
			
			$('#gran_total').html(total);
		}
		
}



// inicializaciones de mdb
function inicializerMDB(){
	$('.toast').toast('show');
}


// Imagen Render Form
function readURL(input) {
	  if (input.files && input.files[0]) {

	    var reader = new FileReader();

	    reader.onload = function(e) {
	      $('.image-upload-wrap').hide();
	      $('.file-upload-image').attr('src', e.target.result);
	      $('.file-upload-content').show();
	      $('.image-title').html(input.files[0].name);
	    };

	    reader.readAsDataURL(input.files[0]);

	  } else {
	    removeUpload();
	  }
	}

	function removeUpload() {
	  $('.file-upload-input').replaceWith($('.file-upload-input').clone());
	  $('.file-upload-content').hide();
	  $('.image-upload-wrap').show();
	}
	$('.image-upload-wrap').bind('dragover', function () {
			$('.image-upload-wrap').addClass('image-dropping');
		});
		$('.image-upload-wrap').bind('dragleave', function () {
			$('.image-upload-wrap').removeClass('image-dropping');
	});	