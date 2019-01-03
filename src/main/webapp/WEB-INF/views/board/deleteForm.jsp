<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<link href="${css}join.css" rel="stylesheet" type="text/css">
<script src="${project}script.js">
</script>
<html>
<body onload="deleteFocus();">
	<header>
		<div class="gnb">
			<ul>
				<li><a href="login">로그인</a></li>
				<li><a href="join">회원가입</a>
					<li><a href="login">마이페이지</a></li>
				<li><a href="../guest/cart.html">장바구니</a></li>
				<li><a href="../guest/order.html">주문목록</a></li>
				<li><input type='text' class='input_text'>
					<button type='submit' class='sch_smit'>검색</button></li>
			</ul>
		</div>
	</header>
	<br><br>
	<h2 align="center">글 삭제 페이지</h2>
	<form action="deletePro" method="post" name="deleteform"
		onsubmit="return deleteCheck();">
		<input type="hidden" name="num" value="${num}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<table align="center">
			<tr>
				<th colspan="2">비밀번호를 입력하세요.</th>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input class="input" type="password" name="pwd"
					maxlength="10"></td>
			</tr>
			<tr>
				<th colspan="2">
				<input class="inputbutton" type="submit" value="글삭제"> 
				<input class="inputbutton" type="reset"
				value="글삭제취소" onclick="window.history.back();"></th>
			</tr>
		</table>
	</form>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
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