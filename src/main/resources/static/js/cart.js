$(document).ready(function() {
	/* 수량 */
	$('.cart_sub').on('click', function() {
		var productNo = $(this).val();
		var productCnt = $(`#cart_product_cnt${productNo}`).val();
		if (productCnt <= 1) return;
		$(`#cart_product_cnt${productNo}`).val(productCnt - 1);
		cartUpdate(productNo, -1);
	});

	$(".cart_plus").on("click", function() {
		var productNo = $(this).val();
		var productCnt = $(`#cart_product_cnt${productNo}`).val();
		$(`#cart_product_cnt${productNo}`).val(Number(productCnt) + 1);
		cartUpdate(productNo, 1);

	});

	/* 회원 장바구니 상품 수량 업데이트 */
	function cartUpdate(productNo, cnt) {
		$.ajax({
			url: "/layout/user/product/cartUpdate",
			type: "POST",
			data: {
				"productNo": productNo,
				"cnt": cnt
			}, success: function(data) {
				var total = change(data.cnt, data.price, data.discount, data.exchangeRate);
				$(`#cartPrice${data.cartNo}`).text(total);
				amountTotal();
			}
		});
	}

	/* 가격 설정 */
	function change(cnt, price, discount, exchangeRate) {
		var exPrice;
		var totalPrice;

		if (exchangeRate == 'percent') exPrice = Number(price * (100 - discount) * 0.01);
		else if (exchangeRate == 'won') exPrice = Number(price - discount);
		else exPrice = Number(price);

		totalPrice = exPrice * cnt
		return totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}

	$('.cart_product_no').on('click', function() {
		cartCk();
		amountTotal();
	});

	/* 장바구니 올 체크 */
	$("#all_checkbox").on("click", function() {
		var cnt = 0;
		var size = $('.cart_product_no').length;
		$('input:checkbox[name=cart_product_no]:checked').each(function() {
			cnt++;
		});
		if (cnt == size) {
			$("#all_checkbox").css("background", "url(/images/cart_check_box.svg) no-repeat 50% 50%");
			$('.cart_product_no').prop('checked', false);
		} else {
			$("#all_checkbox").css("background", "url(/images/cart_checked_box.svg) no-repeat 50% 50%");
			$('.cart_product_no').prop('checked', true);
		}
		cartCk();
		amountTotal();
	});

	/* 장바구니 체크 */
	(cartCk = function() {
		var cnt = 0;
		var size = $('.cart_product_no').length;
		$('input:checkbox[name=cart_product_no]').each(function() {
			var ck = $(this);
			var ckVal = $(this).val();

			if (ck.is(":checked") == true) {
				$(`#cart_checkbox${ckVal}`).css("background", "url(/images/cart_checked_box.svg) no-repeat 50% 50%");
				cnt++;
			} else {
				$(`#cart_checkbox${ckVal}`).css("background", "url(/images/cart_check_box.svg) no-repeat 50% 50%");
				$("#all_checkbox").css("background", "url(/images/cart_check_box.svg) no-repeat 50% 50%");
			}

		});
		if (cnt == size) {
			$("#all_checkbox").css("background", "url(/images/cart_checked_box.svg) no-repeat 50% 50%");
		}
	})();

	/* 상품 총 금액 */
	(amountTotal = function() {
		var temp = 0;
		$('input:checkbox[name=cart_product_no]:checked').each(function() {
			var price = $(`#cartPrice${$(this).val()}`).text();
			var regex = /[^0-9]/g;
			temp = temp + Number((price.toString().replace(regex, '')));
		});
		$('.amount').text(temp.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
	})();

	/* 장바구니 일괄 삭제 */
	$('#select_del').on('click', function() {
		var checkedValue = [];
		$('input:checkbox[name=cart_product_no]:checked').each(function() {
			checkedValue.push($(this).val());
		});
		$.ajax({
			url: "/layout/user/product/cartAllDelete",
			type: 'POST',
			dataType: 'JSON',
			traditional: true,
			data: {
				'cartNoArr': checkedValue
			}, success: function() {
				for (var i = 0; i < checkedValue.length; i++) {
					$(`#${checkedValue[i]}`).remove();
				}
				cartCk();
				amountTotal();
			}
		});
	});

	/* 장바구니 삭제 */
	$('.del').on('click', function() {
		var cartNo = $(this).val();
		if (confirm("삭제하시겠습니까?") == true) {
			$.ajax({
				url: "/layout/user/product/cartDelete",
				type: "POST",
				data: {
					"cartNo": cartNo
				}, success: function(data) {
					if (data == 1) {
						$(`#${cartNo}`).remove();
						cartCk();
						amountTotal();
					}
				}
			});
		} else {
			return;
		}
	});
});














