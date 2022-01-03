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
	let fileTarget = $('#product_image_orgin_name');
	fileTarget.on("change", function() {
		var filename = '';

		if (window.FileReader) {
			filename = $(this)[0].files[0].name;
		} else {
			filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$(this).siblings('#product_image_value').val(filename);
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
	$('.list-calendar').on('change', function() {
		var start = $('#selectStartDate').val();
		var end = $("#selectEndDate").val();

		$('.btn-date').removeClass('now');
		if (end == todayNow()) {
			if (start == todayNow()) {
				$('.today').addClass('now');
			} else if (start == lastWeek()) {
				$('.lastWeek').addClass('now');
			} else if (start == lastMonth()) {
				$('.lastMonth').addClass('now');
			}
		}
	});

	$('#selectEndDate').attr('max', todayNow());
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
		var checkedValue = [];

		$('input:checkbox[name=productarr]:checked').each(function() {
			checkedValue.push($(this).val());
		});
		$.ajax({
			url: "delete",
			type: 'POST',
			dataType: 'JSON',
			traditional: true,
			data: {
				'productNoArr': checkedValue
			}, success: function(data) {
				if (data == 1) {
					for (var i = 0; i < checkedValue.length; i++) {
						$(`#${checkedValue[i]}`).remove();
					}
				}
			}
		});
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

	/* 일괄 업로드 새창 띄우기 */
	$('#allProductUpload').on('click', function() {
		$(".pop").show();
	});

	$('.btn-close').on('click', function() {
		$(".pop").hide();
	});

	/* 일괄 업로드 */
	$('#multImage').on('change', function() {
		var fileInput = document.getElementById("multImage");
		var files = fileInput.files;

		$('#multiImageValue').val(files.length + "의 상품 선택");
		$('#multFrom').append('<input type="submit" id="imgesSubmit" name="imgesSubmit" value="엑셀 양식 받기" class="images_submit" onclick="imagesSubmit()">')
	});

	/* 엑셀 업로드 */
	$('#excelImage').on('change', function() {
		var form = $('#excel')[0];
		var formData = new FormData(form);

		$.ajax({
			url: 'excel',
			type: 'POST',
			data: formData,
			dataType: 'json',
			contentType: false,
			processData: false,
			success: function(data) {
				$("#resultTest").text(data.total)
				$("#resultSucces").text(data.succes)
				$("#resultFail").text(data.fail)
				var temp;
				$.each(data.data, function(index, item) {
					console.log(item)
					temp += `
						<tr id="${index}">
			            	<td><span>${index+1}</span></td>
			            	<td><img alt="" src="/ckUpload/productImages/product/${item.saveName}"></td>
			            	<td><span>${item.name}</span></td>
			            	<td><span>${item.description}</span></td>
			            	<td><span>${item.price}</span></td>
			            	<td><span>${item.purchasePrice}</span></td>
			            	<td><span>${item.stock}</span></td>
			            	<td><span>${item.discount}${item.exchangeRate == 'percent' ? '%' : item.exchangeRate == 'won' ? '원' : ''}</span></td>
			            	<td><span>${item.startDate}</span><br>(<span>${item.endDate}</span>)</td>
							<input type="hidden" name="productList[${index}].categoryNo" value="${item.categoryNo}"/>
							<input type="hidden" name="productList[${index}].name" value="${item.name}"/>
							<input type="hidden" name="productList[${index}].description" value="${item.description}"/>
							<input type="hidden" name="productList[${index}].price" value="${item.price}"/>
							<input type="hidden" name="productList[${index}].purchasePrice" value="${item.purchasePrice}"/>
							<input type="hidden" name="productList[${index}].stock" value="${item.stock}"/>
							<input type="hidden" name="productList[${index}].discount" value="${item.discount}"/>
							<input type="hidden" name="productList[${index}].exchangeRate" value="${item.exchangeRate}"/>
							<input type="hidden" name="productList[${index}].detailContent" value="${item.detailContent}"/>
							<input type="hidden" name="productList[${index}].startDate" value="${item.startDate}"/>
							<input type="hidden" name="productList[${index}].endDate" value="${item.endDate}"/>
							<input type="hidden" name="productList[${index}].orginName" value="${item.orginName}"/>
							<input type="hidden" name="productList[${index}].saveName" value="${item.saveName}"/>
							<input type="hidden" name="productList[${index}].discountCk" value="${item.discountCk}"/>
							<input type="hidden" name="productList[${index}].duringCheck" value="${item.duringCheck}"/>
			            </tr>
					`;
				});
				$(".writeList").append(temp);
			}
		});
	});
});












