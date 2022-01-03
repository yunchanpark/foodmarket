
$(document).ready(function() {
	const offset = new Date().getTimezoneOffset() * 60000;
	const today = new Date(Date.now() - offset).toISOString().slice(0, 16);
	const calendar = $('.discount-calendar')
	calendar.attr("min", today);

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
			$('input:checkbox[name=notice_checkbox]:checked').each(function() {
				checkedValue.push($(this).val());
			});
			console.log(checkedValue)
			$.ajax({
				url: "notice_deleteOk",
				type: 'POST',
				dataType: 'JSON',
				traditional: true,
				data: {
					'noticeNoArr': checkedValue
				}, success: function(data) {
					if (data == 1) {
					for (var i = 0; i < checkedValue.length; i++) {
						$(`#${checkedValue[i]}`).remove();
					}
				}
				}
			});
		}
	});

	/* 전체 선택 */
	let noticeCk = $('input:checkbox[name=notice_checkbox]');
	let allCk = $('input:checkbox[name=notice_allcheckbox]');

	allCk.on('change', function() {
		if (allCk.is(':checked')) {
			noticeCk.prop('checked', true);
		} else {
			if ($('input:checkbox[name=notice_checkbox]:not(:checked)').length == 0) {
				noticeCk.prop('checked', false);
			}
		}
		$('.ckCnt').text($('input:checkbox[name=notice_checkbox]:checked').length);
	});

	/* 선택 */
	noticeCk.on('change', function() {
		if ($('input:checkbox[name=notice_checkbox]:not(:checked)').length != 0) {
			allCk.prop('checked', false);
		} else {
			allCk.prop('checked', true);
		}
		$('.ckCnt').text($('input:checkbox[name=notice_checkbox]:checked').length);
	});



});


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
