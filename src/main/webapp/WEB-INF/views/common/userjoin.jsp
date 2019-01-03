<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../setting.jsp"%>
<link type="text/css" rel="stylesheet" href="${project}conid.css">
<script src="${project}script.js">
</script>
<html>
<body>
	<c:if test="${insertCnt ==0}">
		<script type="text/javascript">
		alert("오류");
		</script>
		</c:if>
	<c:if test="${insertCnt != 0}">
		<script type="text/javascript">
		alert("회원가입을 축하드립니다.\n 로그인해주세요");
		window.location="main";
		</script>
	</c:if>
</body>
</html>