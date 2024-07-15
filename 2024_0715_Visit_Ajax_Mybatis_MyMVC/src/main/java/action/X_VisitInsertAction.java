package action;

import java.io.IOException;

import dao.VisitDao;
import db.vo.VisitVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VisitInsertAction
 */
//@WebServlet("/visit/insert.do")
public class X_VisitInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(
			HttpServletRequest request,		//client->server로 들어오는 정보 처리
			HttpServletResponse response	//server->client로 응답처리
			)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		///visit/insert.do?name=홍길동&content=123&pwd=123
		
		//0.수신 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
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
		
		//5.목록보기 이동
		response.sendRedirect("list.do");
	}
}