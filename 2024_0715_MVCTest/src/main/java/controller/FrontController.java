package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import action.CommandAction;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CommandAction action = new CommandAction();

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//URL		: http://localhost:8080/2024_0715_MVCTest/list.do
		//host주소	: http://localhost:8080
		//URI		: /2024_0715_MVCTest/list.do
		
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		
		//System.out.println("url = " + url);
		//System.out.println("uri = " + uri);
		
		//        01234567890123456789012345
		// uri = "/2024_0715_MVCTest/list.do"
		int		index = uri.lastIndexOf("/"); //뒤에서부터 검색
		String	cmd   = uri.substring(index+1).replaceAll(".do", "");
		
		System.out.println("cmd = " + cmd);
		
		if(cmd.equals("list")) {
			
			String forward_page = action.list(request, response);
			
			//결과정보(forwad시킬 view정보) forward
			request.getRequestDispatcher(forward_page).forward(request, response);
		}else if(cmd.equals("view")) {
			
			String forward_page = action.view(request, response);
			
			//결과정보(forwad시킬 view정보) forward
			request.getRequestDispatcher(forward_page).forward(request, response);
		}
		
		//System.out.println("--2.FrontController:service() : utf-8--");
	}

}