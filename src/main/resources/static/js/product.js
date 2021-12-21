$(document).ready(function() {
	/* 상품상세설명 ckeditor*/
    CKEDITOR.replace("detailContent");

	/* 유효성 검사 */
	$(".submit_btn").on("click", function() {
		/* 데이터 */
		var categoryNo = $(".categoryNo").val();
		var productName = $(".productName").val();
		var description = $(".description").val();
		var price = $(".price").val();
		var purchase_price = $(".purchase_price").val();
		var product_image_orgin_name = $(".product_image_orgin_name").val();
		var stock = $(".stock").val();
		
		var num_regex = /\D/;
		
		if(categoryNo == "") {
			alert("카테고리를 지정해주세요");
			return;
		}
		
		if(productName == "" || productName.trim().length == 0) {
			alert("상품명을 작성해 주세요");
			return;
		}
		
		if(price == "" || price.trim().length == 0) {
			alert("상품가격을 입력해주세요");
			return;
		} else if(price == 0) {
			alert("상품가격은 0일 수 없습니다");
			return;
		} else if(price < purchase_price) {
			alert("상품가격은 매입가보다 작을 수 없습니다");
			return;
		} else if(num_regex.test(price)) {
			alert("상품가격은 숫자로 입력해야 됩니다");
			return;
		}
		
		if(purchase_price == "" || purchase_price.trim().length == 0) {
			alert("매입가 입력해주세요");
			return;
		} else if(price == 0) {
			alert("매입가는 0일 수 없습니다");
			return;
		} else if(num_regex.test(purchase_price)) {
			alert("상품가격은 숫자로 입력해야 됩니다");
			return;
		}
		
		if(product_image_orgin_name == "" || product_image_orgin_name.trim().length == 0) {
			alert("상품이미지를 등록해주세요");
			return;
		}
		
		if(stock == "" || stock == 0) {
			alert("재고를 입력해주세요");
			return;
		} else if(num_regex.test(stock)) {
			alert("상품가격은 숫자로 입력해야 됩니다");
			return;
		}
	});
	
	/* 	숫자만 입력 */
	$(".num").on({'input' : function(){
		var num_regex = /\D/;
		var temp = $(this).val().replace(num_regex,"");
		$(this).val(temp);
	}})

	/* 기간설정 시간, 분 옵션 */
	op_hour_min();
	function op_hour_min() {
		var hour = $(".calendar-hour");
		var min = $(".calendar-min");
		var hour_temp = "";
		var min_temp = "";

		for (var i = 0; i < 24; i++) {
			hour_temp += `<option value="${i}">${i}시</option>`
		}

		for (var i = 0; i <= 6; i++) {
			if (i == 6) {
				min_temp += `<option value="${(i - 1) * 10}">${(i - 1) * 10}분</option>`
			}
			else {
				min_temp += `<option value="${i * 10}">${i * 10}분</option>`
			}
		}
		hour.append(hour_temp);
		min.append(min_temp);
	}

	/* 상품이미지 업로드 */
	let fileTarget = $(".product_image_orgin_name");
	fileTarget.on("change", function() {
		var filename = "";

		if(window.FileReader){
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
	$('input[name="discount-ck"]').on('click', function() {
		var val = $(this).val();
		var dis = $('.prdSale');
		if (val === 'Y') {
			dis.show();
		} else {
			dis.hide();
		}
	});

	/* 기간 할인 설정 토글 */
	$('input[name="during-check"]').on('click', function() {
		var val = $(this).is(':checked');
		var dis = $('.sale-datetime');
		if (val) {
			dis.show();
		} else {
			dis.hide();
		}
	});

	/* 재고 */
	$('input[name="stock_ck"]').on('click', function() {
		var val = $(this).val();
		var stock = $('.stock');
		if (val === 'Y') {
			stock.attr("disabled", false);
			stock.attr("readonly", false);
			stock.css("background", "white");
			stock.focus();
		} else {
			stock.attr("disabled", true);
			stock.attr("readonly", true);
			stock.css("background", "#d8d8d8");
			stock.val("");
		}
	});
});