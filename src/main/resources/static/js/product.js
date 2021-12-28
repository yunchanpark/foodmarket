$(document).ready(function() {
	/* 기간 할인 설정 최소 값 */
	const offset = new Date().getTimezoneOffset() * 60000;
	const today = new Date(Date.now() - offset).toISOString().slice(0, 16);
	const calendar = $('.discount-calendar')
	calendar.attr("min", today);

	/* 실시간 할인가 */
	(live_money = function() {
		var price = Number($('.price').val());
		var discount = Number($('.discount').val());
		var exchangeRate = $('#exchangeRate').val();
		var discount_price = $('.discount_price');
		var total = $('.total');

		var temp;
		var temp2;

		if (exchangeRate == 'percent') {
			if (discount > 100) {
				$('.discount').val(100);
				discount = $('.discount').val();
			}
			temp = Math.ceil(price * ((100 - discount) / 100));
			temp2 = Math.floor(price * (discount / 100));
		} else if (exchangeRate == 'won') {
			if (discount > price) {
				discount = $('.discount').val();
				$('.discount').val(price);
				discount = price;
			}
			temp = price - discount;
			temp2 = discount;
		} else {
			temp = price;
		}

		discount_price.text(temp2);
		total.text(temp + '원');
	})();

	$('.money').on({
		'input': function() {
			live_money();
		}
	});

	$('#exchangeRate').on('change', function() {
		live_money();
	});

	/* 	숫자만 입력 */
	$('.num').on({
		'input': function() {
			var num_regex = /\D/;
			var temp = $(this).val().replace(num_regex, '');
			$(this).val(temp);
		}
	});

	/* 상품이미지 업로드 */
	let fileTarget = $('.product_image_orgin_name');
	fileTarget.on("change", function() {
		var filename = '';

		if (window.FileReader) {
			filename = $(this)[0].files[0].name;
		} else {
			filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$(this).siblings('.product_image_value').val(filename);
	});


	/* 컨텐츠 토글 */
	$('.btn-arrow').on('click', function() {
		if ($(this).hasClass('up')) {
			$(this).removeClass('up');
			$(this).parent('.sect-hd').next('.sect-wrap').show();
		} else {
			$(this).addClass('up');
			$(this).parent('.sect-hd').next('.sect-wrap').hide();
		}
	});

	$('.btn-arrow').each(function() {
		if ($(this).hasClass('up')) {
			$(this).removeClass('up');
			$(this).parent('.sect-hd').next('.sect-wrap').show();
		} else {
			$(this).addClass('up');
			$(this).parent('.sect-hd').next('.sect-wrap').hide();
		}
	});

	/* 판매기간 토글 */
	$('input[name="discountCk"]').on('click', function() {
		var val = $(this).val();
		var dis = $('.prdSale');
		if (val === 'Y') {
			dis.show();
		} else {
			dis.hide();
		}
	});

	/* 기간 할인 설정 토글 */
	$('input[name="duringCheck"]').on('click', function() {
		var val = $(this).is(':checked');
		var dis = $('.sale-datetime');
		if (val) {
			dis.show();
		} else {
			dis.hide();
		}
	});

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
			$('input:checkbox[name=productarr]:checked').each(function() {
				checkedValue.push($(this).val());
			});
			console.log(checkedValue)
			$.ajax({
				url: "delete",
				type:'POST',
				dataType: 'JSON',
				traditional: true,
				data: {
					'productNoArr': checkedValue
				}, success: function(){
				} 
			});
		}
	});
	
	
	/* 전체 선택 */
	let productCk = $('input:checkbox[name=productarr]');
	let allCk = $('input:checkbox[name=allCk]');
	
	allCk.on('change', function(){
		if(allCk.is(':checked')){
			productCk.prop('checked', true);
		} else {
			if($('input:checkbox[name=productarr]:not(:checked)').length == 0){
				productCk.prop('checked', false);
			}
		}
		$('.ckCnt').text($('input:checkbox[name=productarr]:checked').length);
	});
	
	/* 선택 */
	productCk.on('change', function() {
		if($('input:checkbox[name=productarr]:not(:checked)').length != 0){
				allCk.prop('checked', false);
		} else {
			allCk.prop('checked', true);
		}
		$('.ckCnt').text($('input:checkbox[name=productarr]:checked').length);
	});
});












