package kr.spring.board.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.board.domain.BoardCommand;
import kr.spring.board.service.BoardService;
import kr.spring.util.PagingUtil;
           
@Controller
public class BoardController {
	private Logger log = Logger.getLogger(this.getClass());
	private int rowCount = 10;
	private int pageCount = 10;
	
	@Resource
	private BoardService boardService;
	
	//======�Խ��� �� ���=======//
	//��� ��
	@RequestMapping(value="/board/write.do",
			        method=RequestMethod.GET)
	public String form(HttpSession session, Model model) {
		String id = (String)session.getAttribute("user_id");
		
		BoardCommand command = new BoardCommand();
		command.setId(id);
		
		model.addAttribute("command", command);
		
		return "boardWrite";
	}
	//���۵� ������ ó��
	@RequestMapping(value="/board/write.do",
			        method=RequestMethod.POST)
	public String submit(@ModelAttribute("command")
	                     @Valid BoardCommand boardCommand,
	                     BindingResult result,
	                     HttpServletRequest request,
	                     RedirectAttributes redirect) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<boardCommand>> : " + boardCommand);
		}
		
		//������ ��ȿ�� üũ
		if(result.hasErrors()) {
			return "boardWrite";
		}
		
		//ip����
		boardCommand.setIp(request.getRemoteAddr());
		
		//�۾���
		boardService.insert(boardCommand);
		
		//RedirectAttributes ��ü�� �����̷�Ʈ ������ �� ����
		//���Ǵ� �����͸� ����.
		//�������� �����͸� ���������� URI�󿡴� ������ �ʴ� 
		//������ �������� ���·� ����
		redirect.addFlashAttribute("result", "success");
		
		return "redirect:/board/list.do";
	}
	//======�Խ��� �� ���=======//
	@RequestMapping("/board/list.do")
	public ModelAndView process(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage,
			@RequestParam(value="keyfield",defaultValue="")
			String keyfield,
			@RequestParam(value="keyword",defaultValue="")
			String keyword) {
		
		Map<String,Object> map = 
				new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//�� ���� ���� �Ǵ� �˻��� ���� ����
		int count = boardService.selectRowCount(map);
		
		if(log.isDebugEnabled()) {
			log.debug("<<count>> : " + count);
		}
		
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,currentPage,
						count,rowCount,pageCount,"list.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<BoardCommand> list = null;
		if(count > 0) {
			list = boardService.selectList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;
	}
	//========�Խ��� �� ��=========//
	@RequestMapping("/board/detail.do")
	public ModelAndView process(
			               @RequestParam("num") int num) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<num>> : " + num);
		}
		
		//�ش� ���� ��ȸ�� ����
		boardService.updateHit(num);
		
		BoardCommand board = boardService.selectBoard(num);
				              //view name    �Ӽ���  �Ӽ���
		return new ModelAndView("boardView","board",board);
	}
	//���� �ٿ�ε�
	@RequestMapping("/board/file.do")
	public ModelAndView download(@RequestParam("num") int num) {
		
		BoardCommand board = 
				boardService.selectBoard(num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("downloadView");
		mav.addObject("downloadFile", board.getUploadfile());
		mav.addObject("filename", board.getFilename());
	
		return mav;
	}
	//�̹��� ���
	@RequestMapping("/board/imageView.do")
	public ModelAndView viewImage(
			         @RequestParam("num") int num) {
		
		BoardCommand board = 
				      boardService.selectBoard(num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile", board.getUploadfile());
		mav.addObject("filename", board.getFilename());		
		
		return mav;
	}
	
	//==========�Խ��� �� ����=========//
	//���� ��
	@RequestMapping(value="/board/update.do",
			        method=RequestMethod.GET)
	public String form(@RequestParam("num") int num,
			           Model model) {
		
		BoardCommand boardCommand = 
				boardService.selectBoard(num);
		
		model.addAttribute("command", boardCommand);
		
		return "boardModify";
	}
	//���� ������ ���۵� ������ ó��
	@RequestMapping(value="/board/update.do",
			        method=RequestMethod.POST)
	public String submit(@ModelAttribute("command")
	                     @Valid BoardCommand boardCommand,
	                     BindingResult result,
	                     HttpServletRequest request) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<boardCommand>> : " + boardCommand);
		}
		
		//������ ��ȿ�� üũ
		if(result.hasErrors()) {
			return "boardModify";
		}
		
		//ip����
		boardCommand.setIp(request.getRemoteAddr());
		
		//�� ����
		boardService.update(boardCommand);
		
		return "redirect:/board/list.do";
	}
	
	//==========�Խ��� �� ����=========//
	@RequestMapping("/board/delete.do")
	public String submit(@RequestParam("num") int num) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<num>> : " + num);
		}
		//�� ����
		boardService.delete(num);
		
		return "redirect:/board/list.do";
	}
	
	//========�۾��� �̹��� ���ε�=========//
	@RequestMapping("/board/imageUploader.do")
	public void uploadImage(MultipartFile file,
			                HttpServletRequest request,
			                HttpServletResponse response,
			                HttpSession session)
			                		     throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//���ε��� ���� ���
		String realFolder = 
				session.getServletContext().getRealPath(
						         "/resources/image_upload");
		
		//���ε��� ���� �̸�
		String org_filename = file.getOriginalFilename();
		String str_filename = System.currentTimeMillis() + org_filename;
		
		if(log.isDebugEnabled()) {
			log.debug("<<���� ���ϸ�>> : " + org_filename);
			log.debug("<<������ ���ϸ�>> : " + str_filename);
		}
		
		String filepath = realFolder + "\\" + str_filename;
		
		File f = new File(filepath);
		if(log.isDebugEnabled()) {
			log.debug("<<���� ���>> : " + filepath);
		}
		
		//������ ��ο� ������ ����
		file.transferTo(f);
		
		out.println(
				request.getContextPath()+
				"/resources/image_upload/"+str_filename);
		out.close();
	}
	
}







