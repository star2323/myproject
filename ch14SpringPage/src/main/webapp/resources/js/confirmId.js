$(document).ready(function(){
	var checkId = 0;
	
	//아이디 중복 체크
	$('#confirmId').click(function(){
		if($('#id').val()==''){
			$('#message_id').css('color','red').text('아이디를 입력하세요.');
			return;
		}
		
		//메시지 초기화
		$('#message_id').text('');
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'confirmId.do',
			type:'post',
			data:{id:$('#id').val()},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(data){
				//로딩 이미지 감추기
				$('#loading').hide();
				
				if(data.result == 'idNotFound'){
					$('#message_id').css('color','#000').text('등록가능ID');
					checkId = 1;
				}else if(data.result == 'idDuplicated'){
					$('#message_id').css('color','red').text('중복된 ID');
					checkId = 0;
				}else{
					alert('ID중복체크 오류');
				}
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('ID중복체크시 네트워크 오류 발생');
			}
		});
	});
	
	//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
	$('#register_form #id').keydown(function(){
		checkId=0;
		$('#message_id').text('');
	});
	
	//submit 이벤트 발생시 아이디 중복 체크 여부 확인
	$('#register_form').submit(function(){
		if(checkId==0){
			$('#message_id').css('color','red')
			                .text('아이디 중복 체크 필수!');
			if($('#id').val()==''){
				$('#id').focus();
			}
			return false;
		}
	});
	
});






