<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../setting.jsp"%>
<link href="${css}conid.css" type="text/css" rel="stylesheet">
<script src="${project}script.js">
</script>
<html>
<body>
	<h2>이메일 발송 페이지</h2>
	<c:if test="${cnt == 0}">
		<script type="text/javascript">
			alert("인증번호 발송실패하였습니다.\n이메일을 확인해주세요!!");
		</script>
	</c:if>
	<c:if test="${cnt != 0}">
		<script type="text/javascript">		
			alert("메일을 통해.\n인증번호 발송되었습니다.");
			self.close();
		</script>
	</c:if>
</body>
</html>