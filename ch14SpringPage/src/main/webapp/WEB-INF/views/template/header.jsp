<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 상단 네비게이션 시작 -->
<nav class="navbar navbar-fixed-top custom-navbar">
	<div class="container">
		<div class="navbar-header">
			<button type="button" 
			        class="navbar-toggle custom-navbar"
			        data-toggle="collapse"
			        data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar custom-icon-bar"></span> 
				<span class="icon-bar custom-icon-bar"></span>
				<span class="icon-bar custom-icon-bar"></span>      
			</button>
			<a href="${pageContext.request.contextPath}/main/main.do" class="navbar-brand">회원제 게시판</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav nav-ex">
				<c:if test="${!empty user_id && user_auth ==1}">
				<li><a href="${pageContext.request.contextPath}/member/detail.do">회원정보</a></li>
				<li><a href="${pageContext.request.contextPath}/member/changePassword.do">비밀번호변경</a></li>
				</c:if>
				<c:if test="${!empty user_id && user_auth ==2}">
				<li><a href="${pageContext.request.contextPath}/member/admin_list.do">회원정보목록</a></li>
				</c:if>
			</ul>
			<ul class="nav navbar-nav navbar-right nav-ex">
				<li><a href="${pageContext.request.contextPath}/board/list.do">게시판</a></li>
				<c:if test="${empty user_id}">
				<li><a href="${pageContext.request.contextPath}/member/write.do">회원가입</a></li>
				<li><a href="${pageContext.request.contextPath}/member/login.do">로그인</a></li>
				</c:if>
				<c:if test="${!empty user_id && user_auth !=3}">
				<li><a href="${pageContext.request.contextPath}/member/logout.do">${user_id}님<c:if test="${!empty user_id && user_auth == 2}">(관리자)</c:if> 로그아웃</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>
<!-- 상단 네비게이션 끝 -->
<!-- 상단 라인 시작 -->
<div class="custom-header-line"></div>
<!-- 상단 라인 끝 -->






