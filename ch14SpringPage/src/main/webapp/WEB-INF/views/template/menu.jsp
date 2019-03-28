<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<ul class="menu">
    <c:if test="${empty user_id}">
	<li><a href="index.do">main</a></li>
	</c:if>
</ul>