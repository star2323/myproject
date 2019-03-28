package kr.spring.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.domain.BoardReplyCommand;
import kr.spring.board.service.BoardService;
import kr.spring.util.PagingUtil;

@Controller
public class BoardReplyAjaxController {
	private Logger log = Logger.getLogger(this.getClass());
	private int rowCount = 10;
	private int pageCount = 10;
	
	@Resource
	private BoardService boardService;
	
	//��� ���
	@RequestMapping("/board/writeReply.do")
	@ResponseBody
	public Map<String,String> writeReply(
			    BoardReplyCommand boardReplyCommand,
			    HttpSession session,
			    HttpServletRequest request){
		
		if(log.isDebugEnabled()) {
			log.debug("<<boardReplyCommand>> : " 
		                              + boardReplyCommand);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		
		String user_id = (String)session.getAttribute("user_id");
		if(user_id==null) {
			//�α��� �� ��
			map.put("result", "logout");
		}else {
			//ip���
			boardReplyCommand.setRe_ip(request.getRemoteAddr());
			//��� ���
			boardService.insertReply(boardReplyCommand);
			map.put("result", "success");
		}
		
		return map;
	}
	
	//��� ���
	@RequestMapping("/board/listReply.do")
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage,
			@RequestParam("num") int num){
		
		if(log.isDebugEnabled()) {
			log.debug("<<currentPage>> : " + currentPage);
			log.debug("<<num>> : " + num);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("num", num);
		
		//�� ���� ����
		int count = boardService.selectRowCountReply(map);
		
		PagingUtil page = new PagingUtil(currentPage,count,
				        rowCount,pageCount,null);
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<BoardReplyCommand> list = null;
		if(count > 0) {
			list = boardService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = 
				               new HashMap<String,Object>();
		mapJson.put("count", count);
		mapJson.put("rowCount", rowCount);
		mapJson.put("list", list);
		
		return mapJson;
	}
	
	//��� ����
	@RequestMapping("/board/deleteReply.do")
	@ResponseBody
	public Map<String,String> deleteReply(
			@RequestParam("re_num") int re_num,
			@RequestParam("id") String id,
			HttpSession session){
		
		if(log.isDebugEnabled()) {
			log.debug("<<re_num>> : " + re_num);
			log.debug("<<id>> : " + id);
		}
		
		Map<String,String> map = 
				new HashMap<String,String>();
		
		String user_id = (String)session.getAttribute("user_id");
		if(user_id==null) {
			//�α����� �Ǿ����� ����
			map.put("result", "logout");
		}else if(user_id!=null && user_id.equals(id)) {
			//�α����� �Ǿ� �ְ� �α����� ���̵�� �ۼ��� ���̵� ��ġ
			boardService.deleteReply(re_num);
			map.put("result", "success");
		}else {
			//�α��� ���̵�� �ۼ��� ���̵� ����ġ
			map.put("result", "wrongAccess");
		}	
		return map;
	}
	
	//��� ����
	@RequestMapping("/board/updateReply.do")
	@ResponseBody
	public Map<String,String> modifyReply(
			           BoardReplyCommand boardReplyCommand,
			           HttpSession session,
			           HttpServletRequest request){
		
		if(log.isDebugEnabled()) {
			log.debug("<<boardReplyCommand>> : " + 
		                         boardReplyCommand);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		
		String user_id = (String)session.getAttribute("user_id");
		if(user_id==null) {
			//�α����� �� �Ǿ��ִ� ���
			map.put("result", "logout");
		}else if(user_id!=null && 
				user_id.equals(boardReplyCommand.getId())) {
			//�α��� ���̵�� �ۼ��� ���̵� ��ġ
			
			//ip ���
			boardReplyCommand.setRe_ip(request.getRemoteAddr());
			
			//��� ����
			boardService.updateReply(boardReplyCommand);
			map.put("result", "success");
		}else {
			//�α��� ���̵�� �ۼ��� ���̵� ����ġ
			map.put("result", "wrongAccess");
		}
		
		return map;
	}
	
}








