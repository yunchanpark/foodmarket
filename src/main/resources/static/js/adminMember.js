$(document).ready(function() {


	/* 검색 날짜 */
	$('.btn-date').on('click', function() {
		var start = $('#selectStartDate');
		var end = $("#selectEndDate");

		$('.btn-date').removeClass('now');
		$(this).addClass('now');

		/* 검색 끝 날짜는 항상 오늘 */
		end.val(todayNow());

		if ($(this).hasClass('today')) {
			start.val(todayNow());
		} else if ($(this).hasClass('lastWeek')) {
			start.val(lastWeek());
		} else if ($(this).hasClass('lastMonth')) {
			start.val(lastMonth());
		} else if ($(this).hasClass('todayAll')) {
			start.val("");
			end.val("");
		}
	});

	/* 오늘 */
	function todayNow() {
		var today = new Date();
		return getDateStr(today);
	}

	/* 일주일 전*/
	function lastWeek() {
		var lastWeek = new Date();
		var dayOfMonth = lastWeek.getDate();
		lastWeek.setDate(dayOfMonth - 7);
		return getDateStr(lastWeek);
	}

	/* 한달 전 */
	function lastMonth() {
		var lastMonth = new Date();
		var monthOfYear = lastMonth.getMonth();
		lastMonth.setMonth(monthOfYear - 1);
		return getDateStr(lastMonth);
	}

	/* 날짜 포맷(yyyy-MM-dd) */
	function getDateStr(myDate) {
		var year = myDate.getFullYear();
		var month = (myDate.getMonth() + 1);
		var day = myDate.getDate();

		month = (month < 10) ? "0" + String(month) : month;
		day = (day < 10) ? "0" + String(day) : day;

		return (year + '-' + month + '-' + day);
	}

	/* 일괄 적용 버튼 */
	$('.apply').on('click', function() {
		var selName = $('select[name=batch]').val();
		var checkedValue = [];

		if (selName == 'batchDel') {
			$('input:checkbox[name=productarr]:checked').each(function() {
				checkedValue.push($(this).val());
			});
			console.log(checkedValue)
			$.ajax({
				url: "delete",
				type: 'POST',
				dataType: 'JSON',
				traditional: true,
				data: {
					'productNoArr': checkedValue
				}, success: function() {
				}
			});
		}
	});


	/* 전체 선택 */
	let productCk = $('input:checkbox[name=productarr]');
	let allCk = $('input:checkbox[name=allCk]');

	allCk.on('change', function() {
		if (allCk.is(':checked')) {
			productCk.prop('checked', true);
		} else {
			if ($('input:checkbox[name=productarr]:not(:checked)').length == 0) {
				productCk.prop('checked', false);
			}
		}
		$('.ckCnt').text($('input:checkbox[name=productarr]:checked').length);
	});

	/* 선택 */
	productCk.on('change', function() {
		if ($('input:checkbox[name=productarr]:not(:checked)').length != 0) {
			allCk.prop('checked', false);
		} else {
			allCk.prop('checked', true);
		}
		$('.ckCnt').text($('input:checkbox[name=productarr]:checked').length);
	});

	// 적립금 설정 
	$('.pointConditionBtn').on('click', function() {
		alert("저장되었습니다.");
		$("form").submit();
	});

	$('#point_ck').on('click', function() {
		var uid = $('input:checkbox[name=productarr]:checked').val();
		if (uid == undefined) {
			alert("선택된 회원이 없습니다.");
			return;
		}
		$('#pointUid').val(uid);
		$('.listDetailWrap-2').show();
	});

	$('#pointSavebtn').on('click', function() {
		alert("저장되었습니다.");
		$("form").submit();
	});

	$('#operatorInsert').on('click', function() {
		$('.listDetailWrap-3').show();
	});

	$('.operatorInsBtn').on('click', function() {
		var getnameCheck = RegExp(/^[가-힣]{2,8}$/);
		var id = $("[name = 'operatorId']").val();
		var name = $("[name = 'operatorName']").val();
		var pw = $("[name = 'operatorPw']").val();

		console.log(id.length)

		if (name.length == 0) {
			alert("이름을 입력해주세요");
			$("[name = 'operatorName']").focus();
			return;
		}

		if (!getnameCheck.test(name)) {
			alert("이름은 한글만 입력해주세요");
			$("[name = 'operatorName']").focus();
			return;
		}

		if (id.length == 0) {
			alert("아이디를 입력해주세요");
			$("[name = 'operatorId']").focus();
			return;
		}

		if (id.length > 6) {
			alert("아이디는 5글자 이하로 입력해주세요");
			$("[name = 'operatorId']").focus();
			return;
		}

		if (pw.length == 0) {
			alert("비밀번호를 입력해주세요");
			$("[name = 'operatorPw']").focus();
			return;
		}


		$("form").submit();
	})

	$('.operatorCorBtn').on('click', function() {
		var getnameCheck = RegExp(/^[가-힣]{2,8}$/);
		var id = $("[name = 'CorOperatorId']").val();
		var name = $("[name = 'CorOperatorName']").val();
		var pw = $("[name = 'CorOperatorPw']").val();

		if (name.length == 0) {
			alert("이름을 입력해주세요");
			$("[name = 'CorOperatorName']").focus();
			return;
		}

		if (!getnameCheck.test(name)) {
			alert("이름은 한글만 입력해주세요");
			$("[name = 'CorOperatorName']").focus();
			return;
		}

		if (id.length == 0) {
			alert("아이디를 입력해주세요");
			$("[name = 'CorOperatorId']").focus();
			return;
		}

		if (id.length > 6) {
			alert("아이디는 5글자 이하로 입력해주세요");
			$("[name = 'CorOperatorId']").focus();
			return;
		}

		if (pw.length == 0) {
			alert("비밀번호를 입력해주세요");
			$("[name = 'CorOperatorPw']").focus();
			return;
		}

		$("form").submit();
	})

});











