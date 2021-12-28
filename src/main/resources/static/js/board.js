/**
 * 
 */
function checkSelectAll() {
	const checkboxes = document.querySelectorAll('input[name="notice_checkbox"]');
	const checked = document.querySelectorAll('input[name="notice_checkbox"]:checked');
	const selectAll = document.querySelector('input[name="notice_allcheckbox"]');
	if (checkboxes.length == checked.length) {
		selectAll.checked = true;
	} else {
		selectAll.checked = false;
	}
}

function selectAll(selectAll) {
	const checkboxes = document.getElementsByName('notice_checkbox');
	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked
	})
}



//체크박스 삭제
/*function boardDelete(){

	var boardIdxArray = [];

	$("input:checkbox[name=notice_checkbox]:checked").each(function(){
		boardIdxArray.push($(this).val());
	});

	console.log(boardIdxArray);

	if(boardIdxArray == ""){
		alert("삭제할 항목을 선택해주세요.");
		return false;
	}

	var confirmAlert = confirm('정말로 삭제하시겠습니까?');
	if(confirmAlert){

		$.ajax({
			type : 'POST'
		   ,url : "notice_deleteOk"
		   ,dataType : 'json'
		   ,data : JSON.stringify(boardIdxArray)
		   ,contentType: 'application/json'
		   ,success : function(result) {
				alert("해당글이 정상적으로 삭제되었습니다.");
				location.href="notice_list";
		   },
		   error: function(request, status, error) {

		   }
	   })
	}
}
*/



	
function boardDelete() {

	$("#checkbox").click(function() {
		if (this.checked) {
			$("input[name=''notice_checkbox']").prop("checked", true);
		} else {
			$("input[name=''notice_checkbox']").prop("checked", false);
		}
	});

	$("input[name='notice_checkbox']").click(function() {
		let dellnpLen = $("input[name='notice_checkbox']").length;
		let dellnpChkLen = $("input[name='notice_checkbox']:checked").length;
		if (dellnpLen == dellnpChkLen) {
			$("#checkbox").prop("checked", true);
		} else {
			$("#checkbox").prop("checked", false);
		}
	});

	let dellnpChkLen = $("input[name='notice_checkbox']:checked").length;
	if (dellnpChkLen > 0) {
		if (confirm("삭제하시겠습니까?")) {
			let frm = $("#frm");
			frm.attr("action", "notice_deleteOk");
			frm.attr("method", "post");
			frm.submit();
		}
	} else {
		alert("Not selected");
	}
}



// 문의 이미지 업로드
	let fileTarget = $(".product_image_orgin_name");
	fileTarget.on("change", function() {
		var filename = "";

		if (window.FileReader) {
			filename = $(this)[0].files[0].name;
		} else {
			filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$(this).siblings(".product_image_value").val(filename);
	});
