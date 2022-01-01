$(document).ready(function() {
	// 화면 bottom_gnb 상단 고정
	$(window).scroll(function() {
		var num = $(this).scrollTop();
		if (num > 167) {
			$(".bottom_gnb").css("position", "fixed");
			$(".bottom_gnb").css("top", "0");
			$(".bottom_gnb").css("left", "50%");
			$(".bottom_gnb").css("transform", "translateX(-50%)");
		} else {
			$(".bottom_gnb").css("position", "relative");
			$(".bottom_gnb").css("top", "");
			$(".bottom_gnb").css("left", "");
			$(".bottom_gnb").css("transform", "");
		}
	});

	// 버튼 클릭시 검색 값 삭제
	$(".search_del").on("click", function() {
		$("#sword").val("");
	});


	$("#sword").on("blur", function() {
		$(".search_del").css("background", "#F7F7F7");
	});



	// 구매 수량 버튼
	let cnt = $(".view_product_cnt").val();

	$(".plus_btn").on("click", function() {
		let left_cnt = $(".product_view_stock").text();
		left_cnt = Number(left_cnt);
		cnt = $(".view_product_cnt").val();
		if (cnt == left_cnt) return;
		cnt = Number(cnt) + 1;
		$(".view_product_cnt").val(cnt);
		$(".product_value").text(change(cnt) + "원");
	});
	$(".sub_btn").on("click", function() {
		cnt = $(".view_product_cnt").val();
		if (cnt == 1) return;
		cnt = Number(cnt) - 1;
		$(".view_product_cnt").val(cnt);
		$(".product_value").text(change(cnt) + "원");
	});

	// 가격 변경
	function change(cnt) {
		$(".product_value").empty();
		let price = $(".product_view_price").text();
		cnt = Number(cnt);
		price = Number(price);
		let value = cnt * price;
		return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}

	// 화면 이동 버튼
	let btn_width = 0;
	let pos = 0;
	function display_btn() {
		if (pos == 0) {
			$(".bx-prev").css("display", "none");
		} else if (pos == Math.floor($(".product_new_item").length / 4)) {
			$(".bx-next").css("display", "none");
		} else {
			$(".bx-prev").css("display", "block");
			$(".bx-next").css("display", "block");
		}
	}
	display_btn();
	$(".bx-next").on("click", function() {
		btn_width = btn_width + $('.product_new_item').outerWidth(true) * 4;
		$(".product_new_item").animate({
			left: -btn_width
		});
		pos = pos + 1;
		display_btn();
	});

	$(".bx-prev").on("click", function() {
		btn_width = btn_width - $('.product_new_item').outerWidth(true) * 4;
		$(".product_new_item").animate({
			left: -btn_width
		});
		pos = pos - 1;
		display_btn();
	});

	// 카트 담기
	$("#basket").on("click", function() {
		var product_no = $("#product_no").val();
		var product_cnt = $("#product_cnt").val();
		$.ajax({
			url: "product_cart.do",
			type: "post",
			data: {
				"product_no": product_no,
				"product_cnt": product_cnt
			}, success: function() {
				cartList();
				$("#cart_title").animate({
					opacity: "0.7"
				}, 2000).animate({
					opacity: "0"
				}, 2000);
				$("#cloud").animate({
					opacity: "1"
				}, 2000).animate({
					opacity: "0"
				}, 2000);
			}
		});
	});

	$("#basket_no_login").on("click", function() {
		alert("로그인을 하셔야 합니다.");
	});
});



