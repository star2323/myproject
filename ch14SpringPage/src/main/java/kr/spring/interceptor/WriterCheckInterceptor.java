package kr.spring.interceptor;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.board.domain.BoardCommand;
import kr.spring.board.service.BoardService;

public class WriterCheckInterceptor extends
                                 HandlerInterceptorAdapter{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BoardService boardService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			                 HttpServletResponse response,
			                 Object handler)throws Exception{
		
		if(log.isDebugEnabled()) {
			log.debug("====WriterCheckInterceptor진입====");
		}
		
		int num = Integer.parseInt(request.getParameter("num"));
		BoardCommand board = boardService.selectBoard(num);
				
		//로그인 아이디 구하기
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		if(log.isDebugEnabled()) {
			log.debug("<<user_id>> : " + user_id);
			log.debug("<<작성자 id>> : " + board.getId());
		}
		
		//로그인 아이디와 작성자 아이디 일치 여부 체크
		if(user_id==null || !user_id.equals(board.getId())) {
			if(log.isDebugEnabled()) {
				log.debug("<<로그인 아이디와 작성자 아이디 불일치>>");
			}
			
			request.setAttribute("accessMsg", 
					       "로그인 아이디와 작성자 아이디 불일치");
			request.setAttribute("accessUrl", 
					request.getContextPath()+"/board/list.do");
			
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(
							"/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		
		if(log.isDebugEnabled()) {
			log.debug("<<로그인 아이디와 작성자 아이디 일치>>");
		}
		return true;
	}
	
}




