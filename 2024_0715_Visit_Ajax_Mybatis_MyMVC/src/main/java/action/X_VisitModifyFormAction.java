package action;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.VisitDao;
import db.vo.VisitVo;

/**
 * Servlet implementation class VisitModifyFormAction
 */
//@WebServlet("/visit/modify_form.do")
public class X_VisitModifyFormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// /visit/modify_form.do?idx=24&no=2
		
		//1.수정할 게시물의 idx받는다
		int idx = Integer.parseInt(request.getParameter("idx"));
		//2. idx에 해당되는 게시물 1건 얻어오기
		VisitVo vo = VisitDao.getInstance().selectOne(idx);
		
		//textarea \n기능 처리 : <br> -> \n
		String content = vo.getContent().replaceAll("<br>", "\n");
		vo.setContent(content);

		//3. request binding
		request.setAttribute("vo", vo);
		
		//Dispatcher형식으로 호출
		String forward_page = "visit_modify_form.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(forward_page);
		disp.forward(request, response);

	}

}