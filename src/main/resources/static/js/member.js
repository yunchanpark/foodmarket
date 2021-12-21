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

// 비밀번호 클릭 문구표시
$("[name='pw']").focusin(function() {
	$("#pw_icon").show();
	$("#pw_msg").show();
	$("#pw_icon_two").show();
	$("#pw_msg_two").show();
});

// 비밀번호 확인 클릭 문구표시
$("[name='pw_ck']").focusin(function() {
	$("#pw_ck_icon").show();
	$("#pw_ck_msg").show();
});

// 비밀번호 찾기 확인 클릭 문구표시
$("[name='find_pw_new_ck']").focusin(function() {
	$("#pw_ck_icon").show();
	$("#pw_ck_msg").show();
});

// 비밀번호 찾기 클릭 문구표시
$("[name='find_pw_new']").focusin(function() {
	$("#pw_icon").show();
	$("#pw_msg").show();
	$("#pw_icon_two").show();
	$("#pw_msg_two").show();
});


// 회원가입 유효성 검사
$(document).ready(function() {
	var getidCheck = RegExp(/^[a-zA-z][0-9A-Za-z]{5,}$/); // 아이디 정규화식
	var getpwCheck = RegExp(/^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{4,25}$/); // 비밀번호 정규화식
	var getnameCheck = RegExp(/^[가-힣]{2,8}$/); // 이름 정규화식
	var getemailCheck = RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/);

	var idchk = false;
	var emailck = false;

	// 아이디 중복체크
	$("#id_check").click(function() {
		var id = $("[name = 'id']").val();

		if (id == "") {
			alert("아이디는 필수입력사항입니다.")
			$("[name='id']").focus();
			return;
		}

		if (!getidCheck.test(id)) {
			alert("아이디 형식에 맞게 입력해주세요.")
			$("[name='id']").focus();
			return;
		}

		if (getidCheck.test(id)) {
			$.ajax({
				url: "Check.ajax",
				type: "get",
				cache: false,
				success: function(data, status) {
					if (status == "success")
						parseJSON(data);
				}
			});

			function parseJSON(jsonObj) {
				var data = jsonObj.data;
				var dbid = "";
				var cnt = 0;

				for (var i = 0; i < data.length; i++) {
					dbid += data[i].id;
					if (i < data.length - 1) dbid += ",";
				}

				var checkid = dbid.split(",");

				for (var i = 0; i < checkid.length; i++)
					if (checkid[i] == id) cnt = 1;

				if (cnt == 1) {
					alert("이미 사용중인 아이디입니다.");
					$("[name='id']").focus();
				} else {
					idchk = true;
					alert("사용가능한 아이디입니다.")
					$("[name='id']").attr("readonly", true);
					$("[name='id']").css("background-color", "rgb(182, 176, 176)");
				}
			}
		}
	});

	// 비밀번호 유효성 검사
	$("[name='pw']").on({
		'input': function() {
			var pw = $("[name='pw']").val();

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
	$("[name='pw_ck']").on({
		'input': function() {
			var pw = $("[name='pw']").val();
			var pw2 = $("[name='pw_ck']").val();

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
	$("#email_check").click(function() {
		var email = $("[name='email']").val();

		if (email == "") {
			alert("이메일은 필수입력사항입니다.")
			$("[name='email']").focus();
			return;
		}

		if (!getemailCheck.test(email)) {
			alert("이메일 형식에 맞게 입력해주세요.")
			$("[name='email']").focus();
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
					$("[name='email']").focus();
				} else {
					alert("해당 이메일로 인증번호가 발송되었습니다.")
					$("[name='email']").attr("readonly", true);
					$("[name='email']").css("background-color", "rgb(182, 176, 176)");
					$("#email_check_auth").show();
					$("#email_check_auth_btn").show();
					$.ajax({
						url: "email_auth.ajax",
						type: "get",
						data: {
							"email": email
						}, success: function() {
							$("#email_check_auth").focus();
						}
					});
				}
			}
		}
	});

	// 이메일 인증하기 버튼
	$("#email_check_auth_btn").click(function() {
		var email_hash = $("[name='email_hash']").val();
		var email_hidden = $("[name='email_hidden']").val();

		if (email_hash == email_hidden) {
			emailck = true;
			alert("인증번호가 확인되셨습니다.")
			$("[name='email_hash']").attr("readonly", true);
			$("[name='email_hash']").css("background-color", "rgb(182, 176, 176)");
		} else {
			$("[name='email_hash']").focus();
			alert("인증번호가 다릅니다.")
		}
	});

	$("#register_input").on("click", function() {
		// 입력받은 회원가입 내용들
		var id = $("[name = 'id']").val();
		var pw = $("[name = 'pw']").val();
		var pw_ck = $("[name='pw_ck']").val();
		var name = $("[name='name']").val();
		var email = $("[name='email']").val();
		var addr_detail = $("[name='addr_detail']").val();

		// 아이디 체크
		if (id == "") {
			alert("아이디는 필수입력사항입니다.");
			$("[name = 'id']").focus();
			return;
		}

		if (!getidCheck.test(id)) {
			alert("아이디 형식에 맞게 입력해주세요.")
			$("[name='id']").focus();
			return;
		}

		if (!idchk) {
			alert("아이디 중복확인을 해야합니다.")
			$("[name='id']").focus();
			return;
		}

		// 비밀번호 체크
		if (pw == "") {
			alert("비밀번호는 필수입력사항입니다.");
			$("[name = 'pw']").focus();
			return;
		}

		if (!getpwCheck.test(pw)) {
			alert("비밀번호를 다시 한번 확인해주세요.");
			$("[name = 'pw']").focus();
			return;
		}

		if (pw.length < 10) {
			alert("비밀번호를 다시 한번 확인해주세요.");
			$("[name = 'pw']").focus();
			return;
		}

		if (pw != pw_ck) {
			alert("비밀번호가 일치하지 않습니다.");
			$("[name = 'pw_ck']").focus();
			return;
		}

		// 이름 체크
		if (name == "") {
			alert("이름은 필수입력사항입니다.");
			$("[name = 'name']").focus();
			return;
		}

		if (!getnameCheck.test(name)) {
			alert("이름 형식에 맞게 입력해주세요.");
			$("[name = 'name']").focus();
			return;
		}

		// 이메일 체크
		if (email == "") {
			alert("이메일은 필수입력사항입니다.");
			$("[name = 'email']").focus();
			return;
		}

		if (!emailck) {
			alert("이메일 인증을 해야합니다.");
			$("[name = 'email']").focus();
			return;
		}

		// 상세주소 체크
		if (addr_detail == "") {
			alert("주소는 필수입력사항입니다.");
			$("[name = 'addr_detail']").focus();
			return;
		}

		// 약관동의 체크
		const checked = document.querySelectorAll('input[id="terms_second"]:checked');
		const checked_two = document.querySelectorAll('input[id="terms_third"]:checked');

		if (checked.length == 0) {
			alert("이용약관에 동의해주세요.");
			return;
		}

		if (checked_two.length == 0) {
			alert("개인정보 수집·이용 동의해주세요.");
			return;
		}

		$("form").submit();
	});

	// 비밀번호 찾기 인증번호
	$("#find_auth").on("click", function() {
		var find_pw_auth = $("[name='find_pw_auth']").val();
		var find_pw_hidden = $("[name='find_pw_hidden']").val();
		console.log(find_pw_auth)
		console.log(find_pw_hidden)

		if (find_pw_auth == find_pw_hidden) {
			alert("인증번호가 확인되셨습니다.")
			$("form").submit();
		} else {
			$("[name='find_pw_auth']").focus();
			alert("인증번호가 다릅니다.")
		}
	});

	// 비밀번호 찾기 유효성 검사
	$("[name='find_pw_new']").on({
		'input': function() {
			var pw = $("[name='find_pw_new']").val();

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

	// 비밀번호 찾기 확인 유효성 검사
	$("[name='find_pw_new_ck']").on({
		'input': function() {
			var pw = $("[name='find_pw_new']").val();
			var pw2 = $("[name='find_pw_new_ck']").val();

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

	// 비밀번호 변경 
	$("#pw_change").on("click", function() {
		var pw = $("[name='find_pw_new']").val();
		var pw_ck = $("[name='find_pw_new_ck']").val();

		// 비밀번호 체크
		if (pw == "") {
			alert("비밀번호는 필수입력사항입니다.");
			$("[name = 'find_pw_new']").focus();
			return;
		}

		if (!getpwCheck.test(pw)) {
			alert("비밀번호를 다시 한번 확인해주세요.");
			$("[name = 'find_pw_new']").focus();
			return;
		}

		if (pw.length < 10) {
			alert("비밀번호를 다시 한번 확인해주세요.");
			$("[name = 'find_pw_new']").focus();
			return;
		}

		if (pw != pw_ck) {
			alert("비밀번호가 일치하지 않습니다.");
			$("[name = 'find_pw_new_ck']").focus();
			return;
		}

		$("form").submit();
	});
});

// 이용약관 보기
function terms_one() {
    window.open("terms_one.do","이용약관 동의","width=585,height=375,left=700,top=270");
}

function terms_two() {
    window.open("terms_two.do","개인정보 수집·이용 동의","width=585,height=375,left=700,top=270");
}

function terms_three() {
    window.open("terms_three.do","개인정보 수집·이용 동의","width=585,height=375,left=700,top=270");
}

// 약관동의 체크박스 
function checkSelectAll() {
	const checkboxes = document.querySelectorAll('input[name="terms"]');
	const checked = document.querySelectorAll('input[name="terms"]:checked');
	const selectAll = document.querySelector('input[name="terms_king"]');
	if (checkboxes.length == checked.length) {
		selectAll.checked = true;
	} else {
		selectAll.checked = false;
	}
}

function selectAll(selectAll) {
	const checkboxes = document.getElementsByName('terms');
	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked
	})
}
