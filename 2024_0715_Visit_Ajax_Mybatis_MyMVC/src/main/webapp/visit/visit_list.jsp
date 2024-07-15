<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- JSTL Library  -->    
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--  Bootstrap  3.x  -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<%-- 현재 컨텍스트 경로 : ${ pageContext.request.contextPath } --%>
<link rel="stylesheet"  href="${ pageContext.request.contextPath }/css/visit.css">
<!-- <link rel="stylesheet"  href="../css/visit.css"> -->

<script type="text/javascript">
	
	function del(f){
		
		let c_pwd = f.c_pwd.value.trim(); //내가 입력한 비밀번호
		let	idx	  = f.idx.value;
		
		let no	  = f.no.value;
		
		if(c_pwd==''){
			alert("삭제할 게시물의 비밀번호를 입력하세요");
			f.c_pwd.value="";
			f.c_pwd.focus();
			return;
		}
		
		// jQuery Ajax:비밀번호 체크
		$.ajax({
			url		:	"check_pwd.do",					//VisitCheckPwdAction
			data	:	{"idx":idx, "c_pwd":c_pwd },	//parameter : check_pwd.do?idx=5&c_pwd=1234
			
			dataType:	"json",
			success	:	function(res_data){
				//					비번일치		비번불일치
				//res_data = {"result":true} or {"result":false}
				if(res_data.result==false){
					alert("삭제할 게시물의 비밀번호가 틀렸습니다")
					return;
				}
				
				//삭제확인
				if(confirm("정말 삭제하시겠습니까?")==false) return;
				
				//삭제처리  JSP EL 과 JS 변수처리 충돌 : 해결방법 '\${ }''
				//location.href=`delete.do?idx=\${ idx }&no=\${ no }`; //``javascript변수
				
				location.href="delete.do?idx=" + idx + "&no=" + no;
				
			},
			error	:	function(err){
				alert(err.responseText);
			}
			
			
		});
		
		
	}//end:del()
	
	//수정폼 띄우기
	function modify_form(f){
		
		let c_pwd = f.c_pwd.value.trim(); //내가 입력한 비밀번호
		let	idx	  = f.idx.value;
		
		let no	  = f.no.value;
		
		if(c_pwd==''){
			alert("수정할 게시물의 비밀번호를 입력하세요");
			f.c_pwd.value="";
			f.c_pwd.focus();
			return;
		}
		
		// jQuery Ajax:비밀번호 체크
		$.ajax({
			url		:	"check_pwd.do",					//VisitCheckPwdAction
			data	:	{"idx":idx, "c_pwd":c_pwd },	//parameter : check_pwd.do?idx=5&c_pwd=1234
			
			dataType:	"json",
			success	:	function(res_data){
				//					비번일치		비번불일치
				//res_data = {"result":true} or {"result":false}
				if(res_data.result==false){
					alert("수정할 게시물의 비밀번호가 틀렸습니다")
					return;
				}
				
				//수정처리  JSP EL 과 JS 변수처리 충돌 : 해결방법 '\${ }''
				//location.href=`delete.do?idx=\${ idx }&no=\${ no }`; //``javascript변수
				
				//location.href="modify_form.do?idx=" + idx + "&no=" + no;
				//	JSP내에서 back-tic사용시 자바스크립트 변수 표현 : \${ 자바스크립트 변수 }
				location.href = `modify_form.do?idx=\${ idx }&page=${ empty param.page ? 1 : param.page }&search=${ empty param.search ? 'all' : param.search}&search_text=${ param.search_text}`;
				
				
			},
			error	:	function(err){
				alert(err.responseText);
			}
			
			
		});
		
	}//end:modify_form()
	
	
</script>

<script type="text/javascript">
	
	function find(){
		
		let search		= $("#search").val();
		let search_text	= $("#search_text").val().trim();
		
		//전체검색이 아닌데 검색어가 비어있으면
		if(search != "all" && search_text==""){
			alert("검색어를 입력하세요");
			$("#search_text").val("");
			$("#search_text").focus();
			return;
		}
		
		//자바스크립트 이용해서 호출
		location.href="list.do?search=" + search + "&search_text=" + 
										  //자바스크립트 인코딩방식
										  encodeURIComponent(search_text, "utf-8");
		
	}//end:find()

</script>


<script type="text/javascript">
<!-- 초기화 -->
	$(document).ready(function(){
		
		if("${ not empty param.search }" == "true"){
			$("#search").val("${ param.search }");
		}
		
		// 전체보기면 입력창 지우기
		if("${ param.search eq 'all' }" == "true"){
			$("#search_text").val("");
		}
		
	});
</script>


</head>
<body>
   
   <div id="box">
       <h1 id="title">:::: 방명록 ::::</h1>
   
       <div style="margin-bottom: 10px;">
          <input  class="btn btn-primary"   type="button" value="글쓰기"
          		   onclick="location.href='insert_form.do'">
          		   <!-- http://192.168.219.170:8080/2024_0620_Visit/visit/insert_form.do -->
       </div>
       
       <!-- 검색메뉴 -->
       <div style="text-align: right; margin-bottom: 10px;">
       		<form class="form-inline">
	       		<select id="search" class="form-control">
	       			<option value="all">전체보기</option>
	       			<option value="name">이름</option>
	       			<option value="content">내용</option>
	       			<option value="name_content">이름+내용</option>
	       		</select>
	       		
	       		<input id="search_text" class="form-control" value="${ param.search_text }">
	       		<input type="button" class="btn btn-info" value="검색" onclick="find();">
       		</form>
       </div>

		<!-- 페이지 메뉴 -->
		<!-- <div>
			<ul class="pagination">
				<li><a href="list.do?page=1">1</a></li>
				<li><a href="list.do?page=2">2</a></li>
				<li><a href="list.do?page=3">3</a></li>
				<li><a href="list.do?page=4">4</a></li>
				<li><a href="list.do?page=5">5</a></li>
				<li><a href="list.do?page=6">6</a></li>
				<li><a href="list.do?page=7">7</a></li>
				<li><a href="list.do?page=8">8</a></li>
				<li><a href="list.do?page=9">9</a></li>
				<li><a href="list.do?page=10">10</a></li>
			</ul>
		</div> -->
		
              
       <!-- 내용이 없을경우 -->
       <c:if test="${ empty requestScope.list }">
         <div id="empty_msg">
            등록된 게시물이 없습니다
         </div>
       </c:if>
       
       <!-- 내용 -->
       <!--   for(VisitVo vo : list) 동일  -->
       <c:forEach var="vo"  items="${ requestScope.list }">
			<form class="form-inline">
			
			<input type="hidden" name="idx" value="${ vo.idx }">
			<input type="hidden" name="no"  value="${ vo.no }">
			
				<div class="panel panel-primary" id="p_${ vo.no }">
					<div class="panel-heading"><h4><b>${ vo.name }</b>님의 글:(${ vo.ip })</h4></div>
					<div class="panel-body">
						<div class="mycommon content">${ vo.content }</div>
						<div class="mycommon regdate">작성일자 : ${ fn:substring(vo.regdate,0,19) }</div>
						<div class="mycommon pwd">
							비밀번호(${ vo.pwd }) : <input class="form-control"   type="password" name="c_pwd">
									   <input class="btn btn-info" 	  type="button" value="수정"
									   		   onclick="modify_form(this.form);">
									   <input class="btn btn-danger" type="button" value="삭제"
									   		   onclick="del(this.form);">
						</div>
					</div>
				</div>
			</form>
		</c:forEach>
		
       <!-- Page Menu -->
		<div style="text-align: center;">
            
            ${ pageMenu }
       </div>
   
   </div>  


</body>
</html>