<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<html>
<body>
	<h2>장바구니 삭제 페이지</h2>
	<c:if test="${insertCnt == 0}">
		<script type="text/javascript">
			alert("환불목록 담기 실패 하였습니다.");
			window.history.back();
		</script>
	</c:if>
	<c:if test="${insertCnt != 0}">
		<script type="text/javascript">		
				alert("환불목록 담기 처리되었습니다.");
				window.location = "orderlist";
		</script>
	</c:if>
</body>
</html>