<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<link href="${css}sum.css" rel="stylesheet" type="text/css">
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['bar']});
      google.charts.setOnLoadCallback(drawChart);
	  
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Kind', 'sum'],
            ['TOP', ${top}],
            ['PANTS', ${pants}],
            ['SHOES', ${shoes}]
          ]);

        var options = {
          chart: {
            title: '종류별 판매 누적도',
          }
        };

        var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

        chart.draw(data, google.charts.Bar.convertOptions(options));
      }
    </script>
<html>
<head>
</head>
<body>	
		<header>
		<div class="gnb">
			<ul>
				<li><span>${sessionScope.memid}</span>님.</li>
				<li><a href="hostlogin">로그아웃</a></li>
				<li><a href="hostlist">재고목록</a></li>
				<li><a href="hostorderlist">주문목록</a></li>
				<li><a href="hostcancel">환불목록</a></li>
				<li><a href="sum">결산</a></li>
				<li><a href="board">관리자 게시판</a></li>
				<li><input type='text' class='input_text'>
					<button type='submit' class='sch_smit'>검색</button></li>
			</ul>
		</div>
	</header>
		<br><br><br><br><br>
		<form action="delete" method="post" name="update">
			<input type="hidden" name="pageNum" value="${pageNum}">
			<table style="margin-left: auto; margin-right: auto;">
				<tr style="background: #000000; color:#ffffff;">
					<th class="th">결산 총 판매액 ! : ${sum}</th>
				</tr>
			</table>
			<div id="columnchart_material" style="width: 2000px; height:650px; margin-left: auto; margin-right: auto;"></div>
		</form>
		<table style="margin-left: auto; margin-right: auto;">
		<tr style="background: #CCCCCC;" class="tr">
		<th>번호</th>
		<th>상품</th>
		<th>상품명</th>
		<th>수량</th>
		<th>색상</th>
		<th>가격</th>
	</tr>
	<c:forEach var="dto" items="${dtos}">
	<tr>
		<td>${dto.num}</td>
		<td><img src="${images}stocklist/${dto.img}"></a></td>
		<td>${dto.subject}</td>
		<td>${dto.age}</td>
		<td>${dto.color}</td>
		<td>${dto.price}</td>
	</tr>
	</c:forEach>
			</table>
		<c:set var="price" value="${firstChat['price']}"></c:set>
		<c:set var="kind" value="${firstChat['kind']}"></c:set>
		<img src="${images}oo.PNG" class="oo"
			style="margin-left: auto; margin-right: auto; display: block;">
		<div class="sidebar">
			<ul>
				<li><a href="main"><img src="${images}logo.PNG"
						width="221px"></a></li>
				<li><a href="top">TOP</a></li>
				<li><a href="pantu">PANTS</a></li>
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