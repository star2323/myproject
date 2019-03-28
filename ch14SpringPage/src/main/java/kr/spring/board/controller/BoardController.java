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
	
	//======게시판 글 등록=======//
	//등록 폼
	@RequestMapping(value="/board/write.do",
			        method=RequestMethod.GET)
	public String form(HttpSession session, Model model) {
		String id = (String)session.getAttribute("user_id");
		
		BoardCommand command = new BoardCommand();
		command.setId(id);
		
		model.addAttribute("command", command);
		
		return "boardWrite";
	}
	//전송된 데이터 처리
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
		
		//데이터 유효성 체크
		if(result.hasErrors()) {
			return "boardWrite";
		}
		
		//ip셋팅
		boardCommand.setIp(request.getRemoteAddr());
		
		//글쓰기
		boardService.insert(boardCommand);
		
		//RedirectAttributes 객체는 리다이렉트 시점에 한 번만
		//사용되는 데이터를 전송.
		//브라우저에 데이터를 전송하지만 URI상에는 보이지 않는 
		//숨겨진 데이터의 형태로 전달
		redirect.addFlashAttribute("result", "success");
		
		return "redirect:/board/list.do";
	}
	//======게시판 글 목록=======//
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
		
		//총 글의 갯수 또는 검색된 글의 갯수
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
	//========게시판 글 상세=========//
	@RequestMapping("/board/detail.do")
	public ModelAndView process(
			               @RequestParam("num") int num) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<num>> : " + num);
		}
		
		//해당 글의 조회수 증가
		boardService.updateHit(num);
		
		BoardCommand board = boardService.selectBoard(num);
				              //view name    속성명  속성값
		return new ModelAndView("boardView","board",board);
	}
	//파일 다운로드
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
	//이미지 출력
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
	
	//==========게시판 글 수정=========//
	//수정 폼
	@RequestMapping(value="/board/update.do",
			        method=RequestMethod.GET)
	public String form(@RequestParam("num") int num,
			           Model model) {
		
		BoardCommand boardCommand = 
				boardService.selectBoard(num);
		
		model.addAttribute("command", boardCommand);
		
		return "boardModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@RequestMapping(value="/board/update.do",
			        method=RequestMethod.POST)
	public String submit(@ModelAttribute("command")
	                     @Valid BoardCommand boardCommand,
	                     BindingResult result,
	                     HttpServletRequest request) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<boardCommand>> : " + boardCommand);
		}
		
		//데이터 유효성 체크
		if(result.hasErrors()) {
			return "boardModify";
		}
		
		//ip셋팅
		boardCommand.setIp(request.getRemoteAddr());
		
		//글 수정
		boardService.update(boardCommand);
		
		return "redirect:/board/list.do";
	}
	
	//==========게시판 글 삭제=========//
	@RequestMapping("/board/delete.do")
	public String submit(@RequestParam("num") int num) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<num>> : " + num);
		}
		//글 삭제
		boardService.delete(num);
		
		return "redirect:/board/list.do";
	}
	
	//========글쓰기 이미지 업로드=========//
	@RequestMapping("/board/imageUploader.do")
	public void uploadImage(MultipartFile file,
			                HttpServletRequest request,
			                HttpServletResponse response,
			                HttpSession session)
			                		     throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//업로드할 폴더 경로
		String realFolder = 
				session.getServletContext().getRealPath(
						         "/resources/image_upload");
		
		//업로드할 파일 이름
		String org_filename = file.getOriginalFilename();
		String str_filename = System.currentTimeMillis() + org_filename;
		
		if(log.isDebugEnabled()) {
			log.debug("<<원본 파일명>> : " + org_filename);
			log.debug("<<저장할 파일명>> : " + str_filename);
		}
		
		String filepath = realFolder + "\\" + str_filename;
		
		File f = new File(filepath);
		if(log.isDebugEnabled()) {
			log.debug("<<파일 경로>> : " + filepath);
		}
		
		//지정한 경로에 파일을 저장
		file.transferTo(f);
		
		out.println(
				request.getContextPath()+
				"/resources/image_upload/"+str_filename);
		out.close();
	}
	
}







