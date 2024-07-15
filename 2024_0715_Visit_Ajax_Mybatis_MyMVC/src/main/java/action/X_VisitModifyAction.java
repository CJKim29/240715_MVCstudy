package action;

import java.io.IOException;
import java.net.URLEncoder;

import dao.VisitDao;
import db.vo.VisitVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VisitModifyAction
 */
//@WebServlet("/visit/modify.do")
public class X_VisitModifyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// /visit/modify.do?idx=23&no=4&name=홍길동&content=123&pwd=123
		// /visit/modify_form.do?idx=48&page=1&search=name&search_text=dd
		
		//0.수신 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
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

		response.sendRedirect(redirect_page);
		
		

	}

}