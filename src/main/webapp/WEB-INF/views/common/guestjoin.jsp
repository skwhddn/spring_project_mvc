<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<link href="${css}join.css" rel="stylesheet" type="text/css">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript">
function emailChkd() {
	if (!document.joinon.email1.value) {
		alert("이메일을 입력해주세요");
		document.joinon.email1.focus();
	} else {
		var url = "emailChk?email1=" + document.joinon.email1.value + "@" +document.joinon.email2.value;
		window.open(url, "confirm", "menubar=no, width=500, height=300, color=red");
	}
}
function Chkd(emailNum) {
	if (!document.joinon.email4.value) {
		alert("인증번호를 입력해주세요");
		document.joinon.email4.focus();
	} else {
		document.joinon.email4.value == emailNum;
		alert("인증완료되었습니다 \n 회원가입진행 해주세요!!");
	}
}
</script>
<html>
<title>회원가입</title>
<%
String emailNum = request.getParameter("key");      
%>
<body>
	<header>
			<div class="gnb">
			<ul>
				<li><a href="login">로그인</a></li>
				<li><a href="join">회원가입</a>
				<li><a href="login">마이페이지</a></li>
				<li><a href="cartlist">장바구니</a></li>
				<li><a href="orderlist">주문목록</a></li>
				<li><input type='text' class='input_text'>
					<button type='submit' class='sch_smit'>검색</button></li>
			</ul>
		</div>
	</header>
	<br><br>
	<img src="${images}join.PNG" 
	style="margin-left: auto; margin-right: auto; display: block;">
	<form name="joinon" method="post" action="uesrjoin"
		onsubmit="return joinCheck();">
	<input type="hidden" name="hiddenid" value="0">
	<input type="hidden" name="hiddenemail" value="0">
	<table align="center">
			<tr>
				<th colspan="2">회원정보를 입력하세요</th>
			</tr>
			<tr>
				<th> * 아이디</th>
				<td><input class="input" type="text" name="id" maxlength="20" style="width:300px">
				<input class="inputButtono" type="button" name="idChk" value="중복확인" onclick="idok();">
				</td>
			</tr>
			
			<tr>
				<th> * 비밀번호</th>
				<td>
				<input class="input" type="password" name="pwd" maxlength="10" style="width:300px">
				</td>
			</tr>
			<tr>
				<th> * 비밀번호 확인 </th>
				<td>
				<input class="input" type="password" name="repwd" maxlength="10" style="width:300px">
				</td>
			</tr>
			<tr>
				<th> * 이름 </th>
				<td>
				<input class="input" type="text" name="name" maxlength="10" style="width:300px">
				</td>
			</tr>
			<tr>
				<th> 핸드폰 번호 </th>
				<td>
				<input class="input" type="text" name="hp1" maxlength="3" style="width:60px" onkeyup="nextHp1();"> -
				<input class="input" type="text" name="hp2" maxlength="4" style="width:90px" onkeyup="nextHp2();"> -
				<input class="input" type="text" name="hp3" maxlength="4" style="width:90px" onkeyup="nextHp3();">
				</td>
			</tr>
			<tr>
				<th> * 주소 </th>
				<td>
 				 <input name="postnum" type="text" readonly="readonly" placeholder="우편번호" />
 				 <input value="주소" type="button" onclick="openAddr();"/>
     			 <input name="addr1" type="text" readonly="readonly" placeholder="주소"/>
      	    	 <input name="addr2" type="text" placeholder="상세주소" style="width:460px">
				</td>
			</tr>
			<tr>
				<th> * email </th>
				<td>
				<input class="input" type="text" name="email1" maxlength="20"style="width:100px"> @
				<input class="input" type="text" name="email2" maxlength="20"style="width:100px">
				<select class="Hi" name="email3" onchange="selcetEmailChk();">
					<option value="0">직접입력</option>
					<option value="gmail.com">구글</option>
					<option value="daum.com">다음</option>
					<option value="naver.com">네이버</option>
				</select>
				<input class="inputButtono" type="button" name="emailChk" value="인증Go" onclick="emailChkd();">
				<input class="input" type="text" name="email4" maxlength="6" style="width:130px">
				<input class="inputButtono" type="button" name="emailChk2" value="인증확인" onclick="Chkd('${emailNum}');">
				</td>
			</tr>
				<tr>
				<th> * 약관동의</th>
				<td>
				<textarea name="message" cols="65" rows="10" readonly>\인터넷 쇼핑몰 『(주)플라이데이 사이버 몰』회원 약관
제1조(목적)
이 약관은 (주)플라이데이 회사(전자상거래 사업자)가 운영하는 (주)플라이데이 사이버 몰(이하 “몰”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버 몰과 이용자의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.
  ※「PC통신, 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다.」
제2조(정의)
① “몰”이란 (주)플라이데이 회사가 재화 또는 용역(이하 “재화s 등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 재화 등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다.
② “이용자”란 “몰”에 접속하여 이 약관에 따라 “몰”이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.
③ ‘회원’이라 함은 “몰”에 회원등록을 한 자로서, 계속적으로 “몰”이 제공하는 서비스를 이용할 수 있는 자를 말합니다.
④ ‘비회원’이라 함은 회원에 가입하지 않고 “몰”이 제공하는 서비스를 이용하는 자를 말합니다.</textarea>
				</td>
			</tr>
			<tr>
				<th> * 체크</th>
					<td>
					<input type="checkbox" name="ck" class="radio" value="ok" required> 동의
					</td>
			</tr>
			<tr>
				<th colspan="2">
					<input class="inputButton" type="submit" value="회원가입">
					<input class="inputButton" type="reset" value="재작성">
					<input class="inputButton" type="button" value="가입취소" onclick="window.history.back()">
				</th>
			</tr>		
		</table>
	</form>
	<img src="${images}oo.PNG" class="oo"
		style="margin-left: auto; margin-right: auto; display: block;">
	<div class="sidebar">
		<ul>
			<li><a href="main"><img src="${images}logo.PNG"
					width="221px"></a></li>
			<li><a href="top">TOP</a></li>
			<li><a href="pants">PANTS</a></li>
			<li><a href="shoes">SHOES</a></li>
			<li></li>
			<br>
			<br>
			<div id="qn">
				<li><a href="board">Q&A</a></li>
				<li><a href="">CodyDinator</a></li>
			</div>
			<br>
			<br>
			<li>010-5586-7824</li>
			<li>MON-FRI 12AM ~ 6PM</li>
			<li>LUNCH 12AM ~ 6PM</li>
			<li>SAT.SUN.HOLIDATY OFF</li>
			<br>
			<li>국민 121-5187-1166</li>
			<li>예금주 : 나종우</li>
		</ul>
	</div>
</body>
</html>