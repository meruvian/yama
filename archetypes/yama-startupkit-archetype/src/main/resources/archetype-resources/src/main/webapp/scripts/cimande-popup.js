$(function() {
$('#popup-dialog').on('show', function() {
	$('#popup-search').submit(function() {
		var obj = $(this).attr('object-name').split('|');
		var target = $(this).attr('field-target').split('|');
	
		jQuery.ajax({
			type : 'GET',
			dataType : 'json',
			url : $(this).attr('action') + ".json",
			data : $(this).serialize(),
			success : function(data) {
				$('#popup-data').empty();
			
				var table = $('<table class="table table-striped"></table>');
				$('#popup-data').append(table);
				
				function addRow(data1, data2) {
					var tr = $('<tr></tr>').appendTo(table);
					var td1 = $('<td style="width: 85%;"></td>').text(data1);
					tr.append(td1);
					var td2 = $('<td style="width: 15%;"></td>');
					tr.append(td2);
					var btn = $('<a title="Set" href="#" data-dismiss="modal"></a>').addClass('btn');
					btn.addClass('btn-primary');
					btn.addClass('popup-btn');
					btn.attr('data-id', data2);
					btn.attr('data-name', data1);
					btn.append('&nbsp;<i class="icon-pencil icon-white"></i>&nbsp;');
					td2.append(btn);
				}
				
				addRow('None', '');
				
				for(d in data[obj[0]].entityList) {
					d = data[obj[0]].entityList[d];
					
					addRow(d[obj[1]], d.id);
				}
				
				$('.popup-btn').click(function() {
					$('#' + target[0]).val($(this).attr('data-id'));
					$('#' + target[1]).val($(this).attr('data-name'));
				});
			},
			error : function() {
				
			}
		});
	
		return false;
	}).submit();
});

$('#popup-dialog').on('hide', function() {
	$('#popup-search').unbind('submit');
	$('.popup-btn').unbind('click');
	$('#popup-data').empty();
	$('#popup-search').removeAttr('action');
	$('#popup-search').removeAttr('object-name');
	$('#popup-search').removeAttr('field-target');
	$('#popup-header').empty();
});

$('.openpopup').click(function() {
	$('#popup-search').attr('action', $(this).attr('href'));
	$('#popup-search').attr('object-name', $(this).attr('object-name'));
	$('#popup-search').attr('field-target', $(this).attr('field-target'));
	$('#popup-header').text($(this).attr('title'));
	$('#popup-dialog').modal('show');
	
	return false;
});

$('.confirm').click(function(e) {
	var c = $(this);
	
	e.preventDefault();
	
	bootbox.confirm(c.attr('data-message'), function(result) {
		if (result) {
			var form = $('<form></form>');
			form.appendTo($(document.body));
			form.attr('action', c.attr('href'));
			form.attr('method', 'POST');
			
			form.submit();
		}
	});
	
});
});