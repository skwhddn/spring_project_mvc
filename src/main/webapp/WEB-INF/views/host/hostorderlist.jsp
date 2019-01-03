<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<link href="${css}order.css" rel="stylesheet" type="text/css">
<html>
<body>
	<c:if test="${sessionScope.memid != null}">
		<header>
		<div class="gnb">
			<ul>
				<li><span>${sessionScope.memid}</span>님.</li>
				<li><a href="hostlogin">로그아웃</a></li>
				<li><a href="hostlist">재고목록</a>
				<li><a href="hostorderlist">주문목록</a></li>
				<li><a href="hostcancel">환불목록</a></li>
				<li><a href="sum">결산</a></li>
				<li><a href="board">관리자 게시판</a></li>
				<li><input type='text' class='input_text'>
					<button type='submit' class='sch_smit'>검색</button></li>
			</ul>
		</div>
	</header>
<br><br><br><br>
<div class="titbox">
<div class="title">    
<span class="name">HOST ORDER LIST</span>
</div>
</div>
<form action="guestorderadd" method="post" name="update">
<table style="margin-left: auto; margin-right: auto;">
	<tr style="background: #CCCCCC">
		<th>상품번호</th>
		<th>상품</th>
		<th>상품명</th>
		<th>수량</th>
		<th>색상</th>
		<th>가격</th>
		<th>주문고객</th>
		<th>선택</th>
		
	</tr>
	<c:forEach var="dto" items="${dtos}">
	<tr>
		<td>${dto.num}</td>
		<td><a href="detailpage?pageNum=${pageNum}&num=${dto.num}"><img src="${images}stocklist/${dto.img}"></a></td>
		<td>${dto.subject}</td>
		<td>${dto.age}</td>
		<td>${dto.color}</td>
		<td>${dto.price}</td>
		<td>${dto.m_id}</td>
		<td><input class="button" type="button" value="주문승인" 
		onclick="window.location='guestorderadd?pageNum=${pageNum}&num=${dto.num}&img=${dto.img}&age=${dto.age}&color=${dto.color}&price=${dto.price}&subject=${dto.subject}&memid=${sessionScope.memid}'">
		</td>
	</tr>
	</c:forEach>
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
	</c:if>
</body>
</html>