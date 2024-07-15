package action.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import annotation.RequestMapping;
import annotation.ResponseBody;
import dao.VisitDao;
import db.vo.VisitVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MyCommon;
import util.Paging;

public class VisitController {

	@RequestMapping("/visit/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		
		
		//1.parameter받기
		String search		=	request.getParameter("search");
		String search_text	=	request.getParameter("search_text");
		
		if(search==null)search="all";
		
		//검색조건을 담을 맵
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 이름 + 내용
		if(search.equals("name_content")) {
			
			map.put("name", search_text);
			map.put("content", search_text);
			
		}else if(search.equals("name")) {  // search=="name" (X)
			
			//이름
			map.put("name", search_text);
			
		}else if(search.equals("content")) {  
			
			//내용
			map.put("content", search_text);
		}
						
		//-------[ Begin :  Page Menu ]------------------------		
				
		int nowPage = 1;
		
		try {
			nowPage= Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//start/end
		int start = (nowPage-1) * MyCommon.Visit.BLOCK_LIST + 1;
		int end   = start+MyCommon.Visit.BLOCK_LIST - 1;
		
		
		map.put("start", start);
		map.put("end", end);
		
		// 총게시물수
		int rowTotal = VisitDao.getInstance().selectRowTotal(map);
		
		
		//검색정보 filter : search_filter = "search=name&search_text=길동"
		String search_filter = String.format("search=%s&search_text=%s", search,search_text);
		
		//pageMenu만들기
		String pageMenu = Paging.getPaging("list.do",
				                           search_filter, 
				                           nowPage, 
				                           rowTotal, 
				                           MyCommon.Visit.BLOCK_LIST , 
				                           MyCommon.Visit.BLOCK_PAGE);
		
		if(rowTotal==0) {
			pageMenu = Paging.getPaging("list.do");
		}
		
				
		//-------[ End :  Page Menu ]------------------------
			

				
				
		//방명록 데이터 가져오기
		List<VisitVo> list = VisitDao.getInstance().selectList(map);

		//request binding ..
		request.setAttribute("list", list);
		request.setAttribute("pageMenu", pageMenu);
		
		return "visit_list.jsp";
		
	}//end:list()
	
	//입력폼 띄우기
	@RequestMapping("/visit/insert_form.do")
	public String insert_form(HttpServletRequest request, HttpServletResponse response) {
		
		return "visit_insert_form.jsp";
	}//end:insert_form()
	
	
	//입력(등록)하기
	@RequestMapping("/visit/insert.do")
	public String insert(HttpServletRequest request, HttpServletResponse response) {
		
		//1.parameter(전달인자) 받기
		String name		= request.getParameter("name");
		String content	= request.getParameter("content").replaceAll("\n", "<br>");
		String pwd		= request.getParameter("pwd");
		
		//2.ip정보 얻어온다
		String ip		= request.getRemoteAddr();
		
		//3.VisitVo 포장
		VisitVo vo = new VisitVo(name, content, pwd, ip);
		
		//4.DB insert
		int res = VisitDao.getInstance().insert(vo);
		
		// FrontController에게 반환
		return "redirect:list.do";
	}//end:insert()
	
	@RequestMapping(value = "/visit/check_pwd.do", produces="application/json; charset=utf-8")
	@ResponseBody
	public String check_pwd(HttpServletRequest request, HttpServletResponse response) {
		
		//1.parameter
		int	   idx   = Integer.parseInt(request.getParameter("idx"));
		String c_pwd = request.getParameter("c_pwd");
		
		//2.idx에 해당되는 게시물 1건을 얻어온다
		VisitVo vo = VisitDao.getInstance().selectOne(idx);
		
		//3.비밀번호 비교
		boolean bResult = vo.getPwd().equals(c_pwd);
		
		//JSON Data생성 전송 : {"result":true}
		String json = String.format("{\"result\":%b}", bResult);
				
		
		return json;
	}//end:check_pwd()
	
	@RequestMapping("/visit/delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		
		// 1.삭제할 idx 수신
		int idx = Integer.parseInt(request.getParameter("idx"));
		String no = request.getParameter("no"); //삭제할 글의 순서
		
		// 2.DB delete
		int res = VisitDao.getInstance().delete(idx);
		
		// FrontController에게 반환
		return "redirect:list.do#p_" + no;
	}//end:delete()
	
	@RequestMapping("/visit/modify_form.do")
	public String modify_form(HttpServletRequest request, HttpServletResponse response) {
		
		//1.수정할 게시물의 idx받는다
		int idx = Integer.parseInt(request.getParameter("idx"));
		//2. idx에 해당되는 게시물 1건 얻어오기
		VisitVo vo = VisitDao.getInstance().selectOne(idx);
		
		//textarea \n기능 처리 : <br> -> \n
		String content = vo.getContent().replaceAll("<br>", "\n");
		vo.setContent(content);

		//3. request binding
		request.setAttribute("vo", vo);
		
		return "visit_modify_form.jsp";
	}//end:modify_form()
	
	@RequestMapping("/visit/modify.do")
	public String modify(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		//1.parameter 받기
		int		idx			= Integer.parseInt(request.getParameter("idx"));

		String	name		= request.getParameter("name");
		String	content		= request.getParameter("content").replaceAll("\n", "<br>");
		String	pwd			= request.getParameter("pwd");
		
		String	page		= request.getParameter("page");
		String	search		= request.getParameter("search");
		String 	search_text	= request.getParameter("search_text");
		
		//2.ip주소 얻어온다
		//				  request.getParameter("id"); 하면 안 됨
		String	ip			= request.getRemoteAddr();
		
		//3.VisitVo포장
		VisitVo vo = new VisitVo(idx, name, content, pwd, ip);
		
		//4.DB update
		int res	= VisitDao.getInstance().update(vo);
		
		//5.목록보기로 이동
		
		String redirect_page = String.format("list.do?page=%s&search=%s&search_text=%s",	page,search,  URLEncoder.encode(search_text, "utf-8"));
		
		return "redirect:" + redirect_page;

	}
	
}