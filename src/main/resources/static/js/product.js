$(document).ready(function() {
	/* 기간 할인 설정 최소 값 */
	const offset = new Date().getTimezoneOffset() * 60000;
	const today = new Date(Date.now() - offset).toISOString().slice(0, 16);
	const calendar = $(".discount-calendar")
	calendar.attr("min", today);

	/* 실시간 할인가 */
	(live_money = function() {
		var price = Number($(".price").val());
		var discount = Number($(".discount").val());
		var exchangeRate = $("#exchangeRate").val();
		var discount_price = $(".discount_price");
		var total = $(".total");

		var temp;
		var temp2;

		if (exchangeRate == "percent") {
			if (discount > 100) {
				$(".discount").val(100);
				discount = $(".discount").val();
			}
			temp = Math.ceil(price * ((100 - discount) / 100));
			temp2 = Math.floor(price * (discount / 100));
		} else if (exchangeRate == "won") {
			if (discount > price) {
				discount = $(".discount").val();
				$(".discount").val(price);
				discount = price;
			}
			temp = price - discount;
			temp2 = discount;
		} else {
			temp = price;
		}

		discount_price.text(temp2);
		total.text(temp + "원");
	})();

	$(".money").on({
		'input': function() {
			live_money();
		}
	});

	$("#exchangeRate").on("change", function() {
		live_money();
	});

	/* 	숫자만 입력 */
	$(".num").on({
		'input': function() {
			var num_regex = /\D/;
			var temp = $(this).val().replace(num_regex, "");
			$(this).val(temp);
		}
	});

	/* 상품이미지 업로드 */
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
});