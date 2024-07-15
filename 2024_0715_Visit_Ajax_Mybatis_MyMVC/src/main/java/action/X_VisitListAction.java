package action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.VisitDao;
import db.vo.VisitVo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MyCommon;
import util.Paging;

/**
 * Servlet implementation class VisitListAction
 */
//@WebServlet("/visit/list.do")
public class X_VisitListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// visit/list.do
		// visit/list.do?search=all&search_text=
		// visit/list.do?search=name&search_text=길동
		// visit/list.do?search=content&search_text=내용
		// visit/list.do?search=name_content&search_text=길동&page=1
		
		//0.수신인코딩 설정
		request.setCharacterEncoding("utf-8");
		
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
		
		
		//Dispatcher형식으로 호출
		String forward_page = "visit_list.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(forward_page);
		disp.forward(request, response);

	}

}