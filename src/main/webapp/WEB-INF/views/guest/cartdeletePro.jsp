<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../setting.jsp"%>
<html>
<body>
	<h2>장바구니 삭제 페이지</h2>
	<c:if test="${deleteCnt == 0}">
		<script type="text/javascript">
			alert("삭제처리 실패하였습니다.");
		</script>
	</c:if>
	<c:if test="${deleteCnt != 0}">
		<script type="text/javascript">		
				alert("삭제처리되었습니다.");
				window.location = "cartlist";
		</script>
	</c:if>
</body>
</html>