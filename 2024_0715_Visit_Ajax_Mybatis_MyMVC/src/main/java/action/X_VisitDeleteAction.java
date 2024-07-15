package action;

import java.io.IOException;

import dao.VisitDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VisitDeleteAction
 */
//@WebServlet("/visit/delete.do")
public class X_VisitDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// /visit/delete.do?idx=10
		
		// 1.삭제할 idx 수신
		int idx = Integer.parseInt(request.getParameter("idx"));
		String no = request.getParameter("no"); //삭제할 글의 순서
		
		// 2.DB delete
		int res = VisitDao.getInstance().delete(idx);
		
		// 3.목록보기이동
		response.sendRedirect("list.do#p_" + no);
	}

}