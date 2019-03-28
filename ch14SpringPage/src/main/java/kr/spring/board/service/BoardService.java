package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import kr.spring.board.domain.BoardCommand;
import kr.spring.board.domain.BoardReplyCommand;

public interface BoardService {
	//ºÎ¸ð±Û
	public List<BoardCommand> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insert(BoardCommand board);
	public BoardCommand selectBoard(Integer num);
	public void updateHit(Integer num);
	public void update(BoardCommand board);
	public void delete(Integer num);

	//´ñ±Û
	public List<BoardReplyCommand> selectListReply(
			Map<String,Object> map);
	public int selectRowCountReply(Map<String,Object> map);
	public void insertReply(BoardReplyCommand boardReply);
	public void updateReply(BoardReplyCommand boardReply);
	public void deleteReply(Integer re_num);

}



