package com.ds.bbs.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.service.board.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	BoardService boardService;
	
	// 메인 화면
	@RequestMapping("")
	public String home() throws Exception {
		return "board/home";
	}
	
	// 게시글 목록
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search_option,
			@RequestParam(defaultValue="") String keyword,
			ModelAndView mav) throws Exception {
		
		// 게시물 총 갯수
		int count = boardService.count(); 
		// 한 페이지 출력 갯수
		int postNum = 10; 
		// 게시물 총 갯수 / 한페이지 출력 갯수 = 하단에 표현될 페이지 번호 개수
		int pageNum = (int)Math.ceil((double)count / (double)postNum);
		// 출력할 게시물
		int startNum = (curPage - 1) * postNum;
		
		List<BoardDTO> list = null;
		list = boardService.list(startNum, postNum, search_option, keyword);
		Map<String, Object> map = new HashMap<>();
		mav.setViewName("board/list");
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("list", list);
		map.put("pageNum", pageNum);
		map.put("curPage", curPage);
		mav.addObject("map", map);
		return mav;
	}
	
	// 게시글 쓰기 페이지 이동
	@RequestMapping(value="write", method=RequestMethod.GET)
	public String write() throws Exception {
		return "board/write";
	}
	
	// 게시글 등록
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(BoardDTO dto) throws Exception {
		boardService.write(dto);
		return "redirect:/board/list";
	}
	
	// 게시글 읽기
	@RequestMapping(value="read", method=RequestMethod.GET)
	public ModelAndView read(
			@RequestParam("bno") int bno,
			@RequestParam int curPage,
			@RequestParam String search_option,
			@RequestParam String keyword,
			ModelAndView mav) throws Exception {
		BoardDTO dto = null;
		dto = boardService.read(bno);
		Map<String, Object> map = new HashMap<>();
		mav.setViewName("board/read");
		map.put("curPage", curPage);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("dto", dto);
		mav.addObject("map", map);
		return mav;
	}
	
	// 게시글 수정 페이지 이동
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public ModelAndView modify(@RequestParam("bno") int bno, ModelAndView mav) throws Exception {
		mav.setViewName("board/edit");
		mav.addObject("dto", boardService.read(bno));
		return mav;
	}
	
	// 게시글 수정
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String update(BoardDTO dto) throws Exception {
		boardService.update(dto);
		return "redirect:/board/list";
	}
	
	// 게시글 삭제
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(@RequestParam("bno") int bno) throws Exception {
		boardService.delete(bno);
		return "redirect:/board/list";
	}
}
