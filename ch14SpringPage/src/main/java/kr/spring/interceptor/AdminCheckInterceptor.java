package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminCheckInterceptor extends
                                 HandlerInterceptorAdapter{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			                 HttpServletResponse response,
			                 Object handler)throws Exception{
		
		if(log.isDebugEnabled()) {
			log.debug("====AdminCheckInterceptor����====");
		}
		
		//�α��� ���� �˻�
		HttpSession session = request.getSession();
		if(session.getAttribute("user_id")==null) {
			//�α����� ���� ���� ����
			response.sendRedirect(
				request.getContextPath()+"/member/login.do");
			return false;
		}
		if((Integer)session.getAttribute("user_auth")!=2) {
			//������ ������ �ƴ� ��
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
							"/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}
	
}



