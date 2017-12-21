$(function() {
	var ctx = $("#ctx").val();
	// 换一个验证码
	$("#securityCodeImg").bind("click", function() {
		$(this).attr("src", ctx + "/verifyCodeServlet?t=" + new Date().getTime());
	});

	
	$("#login_form").ajaxForm({
		beforeSubmit : function(form_data, form, option) {
			$("#errorInfo").html("");
			var username = $("#username").val();
			if(username == '' || username == '用户名' || username == 'Username'){
				 $("#errorInfo").html($.i18n.prop("login.username.info"));
				return false;
			}
			var password = $("#password").val();
			if(password == '' || password == 'Password' || password == '密码'){
				$("#errorInfo").html($.i18n.prop("login.password.info"));
				return false;
			}
			var verifyCode = $("#verifyCode").val();
			if(verifyCode == '' || verifyCode == 'VerifyCode' || verifyCode == '验证码'){
				$("#errorInfo").html($.i18n.prop("login.verifycode.info"));
				return false;
			}
			return true;
		},
		success : function(data) {
			var json = eval('(' + data + ')');
			if(json.status == 'success'){
				var url = json.message;
				window.location.href = json.message;
			}else{
				$("#btn_login").attr('disabled',false);
				if(json.status == 'verifyCodeError'){
					$("#errorInfo").html("验证码错误");
				}else if(json.status == 'userError'){
					$("#errorInfo").html("用户名或密码不正确");
				}else{
					window.location.reload();
				}
				$("#securityCodeImg").attr("src", ctx + "/verifyCodeServlet?t=" + new Date().getTime());
			}
		},
		error : function() {
			$("#errorInfo").html($.i18n.prop("login.error.info"));
		},
		timeout : 20000
	});
	
	var autologin = $("#autologin").attr("checked");
	if(autologin == 'checked'){
		loginSubmit();
	}
	
	var language = GetCookie("lang");
	//	alert(language);
});


function loginSubmit(){
	$("#errorInfo").html("");
	var username = $("#username").val();
	if(username == '' || username == '用户名' || username == 'Username'){
		 $("#errorInfo").html($.i18n.prop("login.username.info"));
		return;
	}
	var password = $("#password").val();
	if(password == '' || password == 'Password' || password == '密码'){
		$("#errorInfo").html($.i18n.prop("login.password.info"));
		return;
	}
	$("#btn_login").attr('disabled',true);
	$("#login_form").submit();
}