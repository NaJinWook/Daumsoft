package com.ds.bbs.controller.board;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.model.board.dto.Pager;
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
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "search_option", required = false, defaultValue = "all") String search_option,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, ModelAndView mav)
			throws Exception {
		// 게시물 총 갯수
		int count = boardService.count(search_option, keyword);
		Pager pager = new Pager(count, curPage);
		// 게시물 총 갯수 / 한페이지 출력 갯수 = 하단에 표현될 페이지 번호 개수
		// int pageNum = (int) Math.ceil((double) count / (double) postNum);
		// 출력할 게시물
		int start = pager.getPageBegin();
		int postNum = pager.getPageEnd();

		List<BoardDTO> list = null;
		list = boardService.list(start, postNum, search_option, keyword);
		Map<String, Object> map = new HashMap<>();
		mav.setViewName("board/list");
		map.put("list", list);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("pager", pager);
		// map.put("pageNum", pageNum);
		map.put("curPage", curPage);
		mav.addObject("map", map);
		return mav;
	}

	// 게시글 쓰기 페이지 이동
	@RequestMapping(value = "write", method = RequestMethod.GET)
	public String write() throws Exception {
		return "board/write";
	}

	// 게시글 등록
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(BoardDTO dto, HttpServletRequest request) throws Exception {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String writer = request.getParameter("writer");
		dto.setTitle(encoding(title));
		dto.setContents(encoding(contents));
		dto.setWriter(encoding(writer));
		boardService.write(dto);
		return "redirect:/board/list";
	}

	// 게시글 읽기
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public ModelAndView read(@RequestParam(value = "bno") int bno,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "search_option", required = false, defaultValue = "all") String search_option,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, ModelAndView mav)
			throws Exception {
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
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam(value = "bno") int bno, ModelAndView mav) throws Exception {
		BoardDTO dto = null;
		dto = boardService.read(bno);
		dto.setTitle(decoding(dto.getTitle()));
		dto.setContents(decoding(dto.getContents()));
		dto.setWriter(decoding(dto.getWriter()));
		mav.setViewName("board/edit");
		mav.addObject("dto", dto);
		return mav;
	}

	// 게시글 수정
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(BoardDTO dto, HttpServletRequest request) throws Exception {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String writer = request.getParameter("writer");
		dto.setTitle(encoding(title));
		dto.setContents(encoding(contents));
		dto.setWriter(encoding(writer));
		boardService.update(dto);
		return "redirect:/board/read?bno=" + dto.getBno();
	}

	// 게시글 삭제
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam(value = "bno") int bno,
			@RequestParam(value = "curPage") int curPage,
			@RequestParam(value = "search_option") String search_option,
			@RequestParam(value = "keyword") String keyword) throws Exception {
		String enc_keyword = URLEncoder.encode(keyword, "UTF-8"); // 컨트롤러에서 컨트롤러로 전송 간에 한글 깨짐 방지
		boardService.delete(bno);
		return "redirect:/board/list?curPage="+curPage+"&search_option="+search_option+"&keyword="+enc_keyword;
	}

	public String encoding(String str) {
		if (str != null && str.length() > 0) {
			return str.replace("<", "&lt").replace(">", "&gt").replace("\r\n", "<br/>");
		}
		return str;
	}

	public String decoding(String str) {
		if (str != null && str.length() > 0) {
			return str.replace("<br/>", "\r\n").replace("&gt", ">").replace("&lt", "<");
		}
		return str;
	}
}
