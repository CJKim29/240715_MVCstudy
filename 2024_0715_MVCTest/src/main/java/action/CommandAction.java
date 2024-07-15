package action;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// POJO(Plain Old Java Object) : 순수자바객체
public class CommandAction {

	//목록요청 처리
	public String list(HttpServletRequest request, HttpServletResponse response) {
		
		List<String> list = new ArrayList<String>();
		
		list.add("Java");
		list.add("Oracle");
		list.add("Html");
		list.add("CSS");
		list.add("Javascript");
		list.add("Spring");
		
		//request binding
		request.setAttribute("list", list);
		
		return "list.jsp";
	}

	public String view(HttpServletRequest request, HttpServletResponse response) {
		
		// /view.do?book=Java
		
		String book = request.getParameter("book");
		String description = "???";
		
		switch(book.toUpperCase()) {
		
			case "JAVA"			: description="제임스 고슬링이 만든 언어/전자제품 제어용 언어로 만들었음."; break;
			case "ORACLE"		: description="현존하는 DBMS중에서 독보적인 성능을 갖는 데이터베이스 프로그램";break;
			case "HTML"			: description="Hyper Text Markup Language로 브라우저에서 사용되는 언어";break;
			case "CSS"			: description="Cascading Style Sheet로 모양을 지정하는 언어";break;
			case "JAVASCRIPT"	: description="브라우저 제어용 언어";break;
			case "SPRING"		: description="봄? 자바의 개발 플랫폼이다";break;
		}
		
		request.setAttribute("book", book);
		request.setAttribute("description", description);
		
		return "view.jsp";
	}
}