// 주소검색 api
function sample4_execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			var roadAddr = data.roadAddress; // 도로명 주소 변수
			document.getElementById("addr_kakao").value = roadAddr;
			$(".addr1").hide();
			$(".addr2").show();
			$("#addr_detail").focus();
		}
	}).open();
}

// 회원탈퇴 버튼
function delete_member() {
	var delConfirm = confirm('회원탈퇴를 하시겠습니까?');
	if (delConfirm) {
		location.href = "MemberDeleteOk.do"
	}
};

// 회원수정 버튼
function update_member() {
	var updateConfirm = confirm('회원정보를 수정하시겠습니까?');
	if (updateConfirm) {
		member_update();
	}
};

// 이메일 변경하지 않으려면
function update_email() {
	var update_email_Confirm = confirm('이메일을 그대로 사용하시겠습니까?');
	if (update_email_Confirm) {
		$("[name='update_email']").attr("readonly", true);
	}
};

// 회원정보수정 비밀번호 클릭 문구표시
$("[name='update_pw']").focusin(function() {
	$("#pw_icon").show();
	$("#pw_msg").show();
	$("#pw_icon_two").show();
	$("#pw_msg_two").show();
});

// 회원정보수정 비밀번호 확인 클릭 문구표시
$("[name='update_pw_ck']").focusin(function() {
	$("#pw_ck_icon").show();
	$("#pw_ck_msg").show();
});

var update_email_ck = true;

// 회원정보 수정
$(document).ready(function() {
	var getpwCheck = RegExp(/^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{4,25}$/); // 비밀번호 정규화식
	var getemailCheck = RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/);

	// 비밀번호 유효성 검사
	$("[name='update_pw']").on({
		'input': function() {
			var pw = $("[name='update_pw']").val();

			if (getpwCheck.test(pw)) {
				$("#pw_icon_two").text('✅');
				$("#pw_msg_two").css("color", "green");
			} else {
				$("#pw_icon_two").text('✖️');
				$("#pw_msg_two").css("color", "red");
			}

			if (pw.length >= 10) {
				$("#pw_icon").text('✅');
				$("#pw_msg").css("color", "green");
			} else {
				$("#pw_icon").text('✖️');
				$("#pw_msg").css("color", "red");
			}
		}
	})

	// 비밀번호 확인 유효성 검사
	$("[name='update_pw_ck']").on({
		'input': function() {
			var pw = $("[name='update_pw']").val();
			var pw2 = $("[name='update_pw_ck']").val();

			if (pw == pw2) {
				$("#pw_ck_icon").text('✅');
				$("#pw_ck_msg").text('비밀번호가 같습니다.')
				$("#pw_ck_msg").css("color", "green");
			} else {
				$("#pw_ck_icon").text('✖️');
				$("#pw_ck_msg").text('비밀번호가 다릅니다.')
				$("#pw_ck_msg").css("color", "red");
			}
		}
	})

	// 이메일 인증
	$("#update_email_check").click(function() {
		var email = $("[name='update_email']").val();
		var email_hidden = $("[name='update_email_hidden']").val();
		update_email_ck = false;

		if (email == email_hidden) {
			update_email();
			return;
		}
		if (email == "") {
			alert("변경할 이메일을 입력해주세요!.")
			$("[name='update_email']").focus();
			return;
		}

		if (!getemailCheck.test(email)) {
			alert("이메일 형식에 맞게 입력해주세요.")
			$("[name='update_email']").focus();
			return;
		}

		if (getemailCheck.test(email)) {
			$.ajax({
				url: "Check.ajax",
				type: "GET",
				cache: false,
				success: function(data, status) {
					if (status == "success")
						parseJSON(data);
				}
			});

			function parseJSON(jsonObj) {
				var data = jsonObj.data;
				var dbemail = "";
				var cnt = 0;

				for (var i = 0; i < data.length; i++) {
					dbemail += data[i].email;
					if (i < data.length - 1) dbemail += ",";
				}

				var checkemail = dbemail.split(",");

				for (var i = 0; i < checkemail.length; i++)
					if (checkemail[i] == email) cnt = 1;

				if (cnt == 1) {
					alert("이미 사용중인 이메일입니다.");
					$("[name='update_email']").focus();
				} else {
					alert("해당 이메일로 인증번호가 발송되었습니다.")
					$("[name='update_email']").attr("readonly", true);
					$("[name='update_email']").css("background-color", "rgb(182, 176, 176)");
					$("#update_email_auth").show();
					$("#update_email_auth_btn").show();
					$.ajax({
						url: "email_auth.ajax",
						type: "get",
						data: {
							"email": email
						}, success: function() {
							$("#update_email_auth_btn").focus();
						}
					});
				}
			}
		}
	});

	// 이메일 인증하기 버튼
	$("#update_email_auth_btn").click(function() {
		var email_hash = $("[name='update_email_auth']").val();
		var email_hidden = $("[name='update_email_auth_hidden']").val();

		if (email_hash == email_hidden) {
			update_email_ck = true;
			alert("인증번호가 확인되셨습니다.")
			$("[name='update_email_auth']").attr("readonly", true);
			$("[name='update_email_auth']").css("background-color", "rgb(182, 176, 176)");
		} else {
			$("[name='update_email_auth']").focus();
			alert("인증번호가 다릅니다.")
		}
	});
});

function member_update() {
	var getnameCheck = RegExp(/^[가-힣]{2,8}$/); // 이름 정규화식
	var getpwCheck = RegExp(/^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{4,25}$/); // 비밀번호 정규화식
	var pw = $("[name = 'update_pw']").val();
	var pw_ck = $("[name='update_pw_ck']").val();
	var name = $("[name='update_name']").val();
	var email = $("[name='update_email']").val();
	var email_hidden = $("[name='update_email_hidden']").val();
	var addr_detail = $("[name='update_addr_detail']").val();

	// 비밀번호 체크
	if (pw == "") {
		alert("변경할 비밀번호를 입력해주세요!");
		$("[name = 'update_pw']").focus();
		return;
	}

	if (!getpwCheck.test(pw)) {
		alert("비밀번호를 다시 한번 확인해주세요.");
		$("[name = 'update_pw']").focus();
		return;
	}

	if (pw.length < 10) {
		alert("비밀번호를 다시 한번 확인해주세요.");
		$("[name = 'update_pw']").focus();
		return;
	}

	if (pw != pw_ck) {
		alert("비밀번호가 일치하지 않습니다.");
		$("[name = 'update_pw_ck']").focus();
		return;
	}

	// 이름 체크
	if (!getnameCheck.test(name)) {
		alert("이름 형식에 맞게 입력해주세요.");
		$("[name = 'update_name']").focus();
		return;
	}

	// 이메일 체크
	if (email == "") {
		alert("변경할 이메일을 입력해주세요!");
		$("[name = 'update_email']").focus();
		return;
	}
	
	if(email == email_hidden){
		update_email_ck = true;
	}

	if (!update_email_ck) {
		alert("이메일을 변경하시려면 이메일 인증을 해야합니다.")
		$("[name = 'update_email']").focus();
		return;
	}

	// 상세주소 체크
	if (addr_detail == "") {
		alert("변경할 주소를 입력해주세요!");
		$("[name = 'update_addr_detail']").focus();
		return;
	}

	$("form").submit();
};