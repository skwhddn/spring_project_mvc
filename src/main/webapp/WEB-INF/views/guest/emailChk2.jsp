<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../setting.jsp"%>
<link href="${css}conid.css" type="text/css" rel="stylesheet">
<script src="${project}script.js">
</script>
<html>
<body onload="confirmIdFocus();">
	<h2 align="center">이메일 인증번호 페이지</h2>
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			alert("인증번호가 틀렸습니다.\n 확인 후 다시 입력해주세요!!");
		</script>
	</c:if>
	<c:if test="${cnt == 1}">
		<script type="text/javascript">
			alert("인증되었습니다.\n 회원가입해주세요!!");
		</script>
	</c:if>
</body>
</html>