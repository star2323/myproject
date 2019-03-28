<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#content').summernote({
		height:200,
		fontNames:['맑은 고딕','Arial','Arial Black','Comic Sans MS','Courier New'],
		focus:true,
		callbacks:{
			onImageUpload:function(files,editor,welEditable){
				for(var i=files.length-1;i>=0;i--){
					sendFile(files[i],this);
				}
			}
		}
	});
	function sendFile(file,el){
		var form_data = new FormData();
		form_data.append('file',file);
		$.ajax({
			data:form_data,
			type:'post',
			url:'./imageUploader.do',
			cache:false,
			contentType:false,
			enctype:'multipart/form-data',
			processData:false,
			success:function(img_name){
				$(el).summernote('editor.insertImage',img_name);
			}
		});
	}
});
</script>
<!-- 중앙 컨텐츠 시작 -->
<div class="container">
	<div class="row">
		<h1>글쓰기</h1>
		<form:form commandName="command" 
		   action="write.do"
		   enctype="multipart/form-data"
		   id="register_form">
			<form:hidden path="id"/>
			<form:errors element="div" cssClass="error-color"/> 
			<ul>
				<li>
					<label for="title">제목</label>
					<form:input path="title"/>
					<form:errors path="title" cssClass="error-color"/>
				</li>
				<li>
					<label for="content">내용</label>
					<form:textarea path="content"/>
					<form:errors path="content" cssClass="error-color"/>
				</li>
				<li>
					<label for="upload">파일업로드</label>
					<input type="file" name="upload" id="upload"/>
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="전송">
				<input type="button" value="목록"
				    onclick="location.href='list.do'">
			</div> 
		</form:form>
	</div>
</div>





