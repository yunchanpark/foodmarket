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

// 회원탈퇴 버튼
function delete_member() {
	var delConfirm = confirm('회원탈퇴를 하시겠습니까?');
	if (delConfirm) {
		location.href = "deleteOk";
		alert("회원탈퇴가 성공적으로 완료되었습니다.");
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
				url: "/layout/user/member/checkId/" + id,
				type: "get",
				cache: false,
				success: function(data, status) {
					if (status == "success") {
						if (data == true) {
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
				url: "/layout/user/member/checkEmail/" + email,
				type: "GET",
				cache: false,
				success: function(data, status) {
					if (status == "success") {
						if (data == true) {
							alert("이미 사용중인 이메일입니다.");
							$("[name='email']").focus();
						} else {
							alert("해당 이메일로 인증번호가 발송되었습니다.")
							$("[name='email']").attr("readonly", true);
							$("[name='email']").css("background-color", "rgb(182, 176, 176)");
							$("#email_check_auth").show();
							$("#email_check_auth_btn").show();
							$.ajax({
								url: "/email/auth/" + email,
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
		}
	});

	// 이메일 인증하기 버튼
	$("#email_check_auth_btn").click(function() {
		var emailhash = $("[name='email_hash']").val();

		$.ajax({
			url: "/email/authCk",
			type: "post",
			data: {
				"emailhash": emailhash
			}, success: function(data) {
				if (data.ck == 1) {
					emailck = true;
					alert("인증이 완료되었습니다.")
					$("[name='email_hash']").attr("readonly", true);
					$("[name='email_hash']").css("background-color", "rgb(182, 176, 176)");
				} else {
					$("[name='email_hash']").focus();
					alert("인증번호가 다릅니다.")
				}
			}
		});
	});

	$("#register_input").on("click", function() {
		// 입력받은 회원가입 내용들
		var pw = $("[name = 'pw']").val();
		var pw_ck = $("[name='pw_ck']").val();
		var name = $("[name='name']").val();
		var addr_detail = $("[name='addr_detail']").val();

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

		// 휴대폰 체크



		// 이메일 체크
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

		alert("회원가입을 축하합니다!")
		$("form").submit();
	});

	// 비밀번호 찾기 인증번호 받기 버튼
	$("#authBtn").on("click", function() {
		alert("입력한 이메일로 인증번호가 발송되었습니다.");
		var email = $("[name='hidden_pw_email']").val();

		console.log(email);
		$.ajax({
			url: "/email/auth/" + email,
			type: "get",
			data: {
				"email": email
			}, success: function() {
				$("#find_pw_auth").focus();
			}
		});
	});

	// 비밀번호 찾기 인증하기 버튼
	$("#find_auth").on("click", function() {
		var find_pw_auth = $("[name='find_pw_auth']").val();

		$.ajax({
			url: "/email/findAuthCk",
			type: "post",
			data: {
				"find_pw_auth": find_pw_auth
			}, success: function(data) {
				if (data.ck == 1) {
					emailck = true;
					alert("인증이 완료되었습니다.")
					$("form").submit();
				} else {
					alert("인증번호가 다릅니다.")
				}
			}
		});
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
		
		alert("비밀번호가 변경되었습니다.")
		$("form").submit();
	});
	
	// 회원정보 수정 
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
				url: "/layout/user/member/checkEmail/" + email,
				type: "GET",
				cache: false,
				success: function(data, status) {
					if (status == "success") {
						if (data == true) {
							alert("이미 사용중인 이메일입니다.");
							$("[name='update_email']").focus();
						} else {
							alert("해당 이메일로 인증번호가 발송되었습니다.")
							$("[name='update_email']").attr("readonly", true);
							$("[name='update_email']").css("background-color", "rgb(182, 176, 176)");
							$("#update_email_auth").show();
							$("#update_email_auth_btn").show();
							$.ajax({
								url: "/email/auth/" + email,
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
		}
	});

	// 이메일 인증하기 버튼
	$("#update_email_auth_btn").click(function() {
		var emailhash = $("[name='update_email_auth']").val();

		$.ajax({
			url: "/email/updateAuth",
			type: "post",
			data: {
				"emailhash": emailhash
			}, success: function(data) {
				if (data.ck == 1) {
					update_email_ck = true;
					alert("인증이 완료되었습니다.")
					$("[name='update_email_auth']").attr("readonly", true);
					$("[name='update_email_auth']").css("background-color", "rgb(182, 176, 176)");
				} else {
					$("[name='update_email_auth']").focus();
					alert("인증번호가 다릅니다.")
				}
			}
		});
	});
});

// 이용약관 보기
function terms_one() {
	window.open("terms_one.do", "이용약관 동의", "width=585,height=375,left=700,top=270");
}

function terms_two() {
	window.open("terms_two.do", "개인정보 수집·이용 동의", "width=585,height=375,left=700,top=270");
}

function terms_three() {
	window.open("terms_three.do", "개인정보 수집·이용 동의", "width=585,height=375,left=700,top=270");
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

// 휴대폰 번호입력하면 자동 '-'입력
var autoHypenPhone = function(str) {
	str = str.replace(/[^0-9]/g, '');
	var tmp = '';
	if (str.length < 4) {
		return str;
	} else if (str.length < 7) {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3);
		return tmp;
	} else if (str.length < 11) {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 3);
		tmp += '-';
		tmp += str.substr(6);
		return tmp;
	} else {
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7);
		return tmp;
	}
}

var phoneNum = document.getElementById('phoneNum');
phoneNum.onkeyup = function() {
	this.value = autoHypenPhone(this.value);
}

var phoneNum = document.getElementById('update_phoneNum');
phoneNum.onkeyup = function() {
	this.value = autoHypenPhone(this.value);
}

// 회원정보 수정 
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

	if (email == email_hidden) {
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

	alert("회원정보가 수정되었습니다. 다시 로그인 해주세요.")
	$("form").submit();
};