$(document).ready(function() {
	/* 화면 bottom_gnb 상단 고정 */
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

	/* 검색 커서 나왔을 때 */
	$("#sword").on("blur", function() {
		$(".search_del").css("background", "#F7F7F7");
	});

	/* 화면 이동 버튼 */
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
	
	/* 카트 개수 */
	(cartCnt = function(){
		$.ajax({
			url: "/layout/cart",
			type: "get",
			success: function(data) {
				$(".cart_count").text(data.cnt);
			}
		});
	})();

	/* 카트 담기 */
	$("#basket").on("click", function() {
		var product_no = $("input[name='productNo']").val();
		var product_cnt = $("input[name='cnt']").val();
		$.ajax({
			url: "/layout/user/product/cart",
			type: "post",
			data: {
				"productNo": product_no,
				"cnt": product_cnt
			}, success: function() {
				cartCnt();
				$(".cart_title").animate({
					opacity: "0.7"
				}, 2000).animate({
					opacity: "0"
				}, 2000);
				$(".cloud").animate({
					opacity: "1"
				}, 2000).animate({
					opacity: "0"
				}, 2000);
			}
		});
	});
});



