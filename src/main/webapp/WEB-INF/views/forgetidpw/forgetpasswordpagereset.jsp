<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>비밀번호찾기</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<link rel="stylesheet" href="/resources/css/forgetpage.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
<script src="http://code.jquery.com/jquery-1.11.1.min.js" type="text/javascript"></script>
<style>
</style>
</head>
<body class="member">

	<%@include file="../mainheader.jsp"%>

	<div class="containerNew">
		<div class="contents member">
			<div class="cont-area">
				<h1 class="tit-h1 line">비밀번호 재설정</h1>
				<div class="id-result">
					<div class="find-desc h1-top-m">
						<strong class="tit">비밀번호 재설정</strong>
						<p class="txt">새로운 비밀번호를 등록해 주세요.</p>
					</div>
					<div class="form-table pass-reset">
					<form action="/updatePassword" method="post" id="frm" name="frm">
						<table>
							<tbody>
								<tr>
									<th scope="row"><label for="custId">아이디</label></th>
									<td>
										<span class="input-style-case02">${findUser.id}</span>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="password">새 비밀번호</label></th>
									<td>
										<span class="input-style-case02">
											<input type="password" id="password" name="password" class="pw"
											placeholder="새로운 비밀번호를 입력해주세요." maxlength="13">
										</span>	 
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="passwordAgain">새 비밀번호 확인</label></th>
									<td>
										<span class="input-style-case02">
											<input type="password" id="passwordAgain" name="passwordAgain" class="pw" 
												placeholder="새로운 비밀번호를 한 번 더 입력해주세요." style="margin-bottom: 13px;"
												maxlength="13">
										</span>
										<div style="text-align: center;">
											<div class="alert alert-success" id="alert-success">비밀번호가 일치합니다.</div> 
											<div class="alert alert-danger" id="alert-danger">비밀번호가 일치하지 않습니다.</div>
											<div class="alert alert-danger" id="alert-length">최소 8자 , 최대 13자로 입력해주세요.</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="bt-area">  
		
							<button type="submit" class="btn-b fantasy" id="show-answer" onclick="formSubmit();">확인</button>
						</div>						
						<!-- <button id="submit" type="submit" id="show-answer" class="btn-t fantasy">비밀번호 저장</button> -->
					</form>
				</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../mainfooter2.jsp"%>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script> 
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"> 
	$(function(){ 
		$("#alert-success").hide(); 
		$("#alert-danger").hide();
		$("#alert-length").hide();
		$("input").keyup(function(){ 
			var pwd1=$("#password").val(); 
			var pwd2=$("#passwordAgain").val(); 
			if(pwd1 != "" || pwd2 != ""){ 
				if(pwd1 != pwd2){
					$("#alert-success").hide(); 
					$("#alert-danger").show();
					$("#alert-length").hide();
					$("#submit").attr("disabled", "disabled");$("#submit").attr("style", "opacity:20%"); 
				}else if($('#password').val().length < 8 || $('#password').val().length > 13){ 
					$("#alert-success").hide(); 
					$("#alert-danger").hide();
					$("#alert-length").show();
					$("#submit").attr("disabled", "disabled");$("#submit").attr("style", "opacity:20%");  	
				}else if(pwd1 == pwd2){ 
					$("#alert-success").show(); 
					$("#alert-danger").hide();
					$("#alert-length").hide();
					$("#submit").removeAttr("disabled");$("#submit").removeAttr("style");  	
				}
			}
		}); 
	});
	</script>
</body>
</html>
