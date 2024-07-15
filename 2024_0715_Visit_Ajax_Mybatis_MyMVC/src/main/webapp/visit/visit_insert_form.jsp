<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>visit_insert_form</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
#box{
	width: 600px;
	margin: auto;
	margin-top: 100px;
}
.panel-heading{
	font-size: 17px;
	font-weight: bold;
}
textarea {
	resize: none;
}
th{
	width: 15%;
	vertical-align: middle !important;
	text-align: center;
	font-size: 15px;
}

</style>

<script type="text/javascript">
	function send(f){
		
		//입력값 검증
		let name	= f.name.value.trim();
		let content	= f.content.value.trim();
		let pwd		= f.pwd.value.trim();
		
		if(name==''){
			alert("작성자명을 입력하세요");
			f.name.value="";	//지우기
			f.name.focus();		//포커스
			return;
		}
		
		if(content==''){
			alert("내용을 입력하세요");
			f.content.value="";	//지우기
			f.content.focus();		//포커스
			return;
		}
		
		if(pwd==''){
			alert("비밀번호를 입력하세요");
			f.pwd.value="";	//지우기
			f.pwd.focus();		//포커스
			return;
		}
		
		//f.method = "POST";
		f.action = "insert.do";	//전송대상(VisitInsertAction)
		f.submit();	//전송
	}
	
</script>

</head>
<body>
	
	<form>
		<div id="box">
				<div class="panel panel-info">
				<div class="panel-heading">글쓰기</div>
				<div class="panel-body">
				
					<table class="table">
						<tr>
							<th>작성자</th>
							<td><input class="form-control" name="name"></td>
						</tr>
						
						<tr>
							<th>내용</th>
							<td>
								<textarea class="form-control" rows="6" name="content"></textarea>
							</td>
						</tr>
						
						<tr>
							<th>비밀번호</th>
							<td><input class="form-control" type="password" name="pwd"></td>
						</tr>
						
						<tr>
							<td colspan="2" align="center">
								<input class="btn btn-primary" type="button" value="글올리기"
										onclick="send(this.form);">
								<input class="btn btn-danger" type="button" value="목록보기"
										onclick="location.href='list.do'">
							</td>
						</tr>
						
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>