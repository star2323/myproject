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
			log.debug("====WriterCheckInterceptor����====");
		}
		
		int num = Integer.parseInt(request.getParameter("num"));
		BoardCommand board = boardService.selectBoard(num);
				
		//�α��� ���̵� ���ϱ�
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		if(log.isDebugEnabled()) {
			log.debug("<<user_id>> : " + user_id);
			log.debug("<<�ۼ��� id>> : " + board.getId());
		}
		
		//�α��� ���̵�� �ۼ��� ���̵� ��ġ ���� üũ
		if(user_id==null || !user_id.equals(board.getId())) {
			if(log.isDebugEnabled()) {
				log.debug("<<�α��� ���̵�� �ۼ��� ���̵� ����ġ>>");
			}
			
			request.setAttribute("accessMsg", 
					       "�α��� ���̵�� �ۼ��� ���̵� ����ġ");
			request.setAttribute("accessUrl", 
					request.getContextPath()+"/board/list.do");
			
			RequestDispatcher dispatcher =
					request.getRequestDispatcher(
							"/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		
		if(log.isDebugEnabled()) {
			log.debug("<<�α��� ���̵�� �ۼ��� ���̵� ��ġ>>");
		}
		return true;
	}
	
}




