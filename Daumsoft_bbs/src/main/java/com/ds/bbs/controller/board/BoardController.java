package com.ds.bbs.controller.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ds.bbs.cripto.CriptoClass;
import com.ds.bbs.model.board.dto.BoardDTO;
import com.ds.bbs.model.board.dto.FileDTO;
import com.ds.bbs.model.board.dto.Pager;
import com.ds.bbs.service.board.BoardService;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/board*")
public class BoardController {
	@Inject
	BoardService boardService;

	CriptoClass cripto = new CriptoClass();
	
	@Value("#{'${whiteList}'.split(',')}")
    private List<String> whiteList;
	
	// 메인 화면
	@RequestMapping("")
	public String home(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		
		// 개인키 삭제
    	session.removeAttribute(CriptoClass.RSA_WEB_KEY);

		// RSA 키 생성
    	cripto.initRsa(request);
		return "board/home";
	}

	// 게시글 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "search_option", required = false, defaultValue = "all") String search_option,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
			@RequestParam(value = "sort", required = false, defaultValue = "regDate") String sort, ModelAndView mav,
			@RequestParam(value = "postNum", required = false, defaultValue = "10") int postNum,
			HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		// 개인키 삭제
    	session.removeAttribute(CriptoClass.RSA_WEB_KEY);
		// RSA 키 생성
    	cripto.initRsa(request);
    	
		int count = boardService.count(search_option, keyword); // 총 게시물 수
		Pager pager = new Pager(count, curPage, postNum);
		int start = pager.getPageBegin(); // 시작점
		postNum = pager.getPageEnd(); // 출력할 게시물 수
		System.out.println("출력할 수 : " + postNum);

		List<BoardDTO> list = null;
		System.out.println("파라미터로 받은 sort값 : " + sort);
		list = boardService.list(start, postNum, search_option, keyword, sort);
		Map<String, Object> map = new HashMap<>();
		mav.setViewName("board/list");
		map.put("postNum", postNum);
		map.put("list", list);
		map.put("sort", sort);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("pager", pager);
		map.put("curPage", curPage);
		mav.addObject("map", map);
		return mav;
	}

	// 게시글 쓰기 페이지 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "search_option", required = false, defaultValue = "all") String search_option,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model,
			@RequestParam(value = "postNum", required = false, defaultValue = "10") int postNum)
			throws Exception {
		model.addAttribute("whiteList", whiteList);
		model.addAttribute("postNum", postNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("search_option", search_option);
		model.addAttribute("keyword", keyword);
		return "board/write";
	}

	// 게시글 등록
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(BoardDTO dto, @RequestParam("uploadFile") List<MultipartFile> files,
			HttpServletRequest request) throws Exception {

		String fileName = null;
		String saveName = null;
		long fileSize = 0;
		UUID uuid = null;
		FileDTO f_dto = null;
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String writer = request.getParameter("writer");
		dto.setTitle(encoding(title));
		dto.setContents(contents);
		dto.setWriter(encoding(writer));
		boardService.write(dto);

		for (MultipartFile file : files) {
			if (!file.getOriginalFilename().isEmpty()) {
				// System.out.println(file.getName() + "그리고 "+file.getOriginalFilename());
				uuid = UUID.randomUUID();
				fileName = file.getOriginalFilename();
				saveName = uuid + "_" + fileName;
				fileSize = file.getSize();
				file.transferTo(new File(f_dto.SAVE_FILE_PATH + saveName));
				f_dto = new FileDTO(dto.getBno(), saveName, fileName, fileSize);
				boardService.insert_fileUpload(f_dto);
			}
		}
		return "redirect:/board/list";
	}

	// 게시글 읽기
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ModelAndView read(@RequestParam(value = "bno") int bno,
			@RequestParam(value = "curPage", required = false, defaultValue = "1") int curPage,
			@RequestParam(value = "search_option", required = false, defaultValue = "all") String search_option,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, ModelAndView mav,
			@RequestParam(value = "postNum", required = false, defaultValue = "10") int postNum)
			throws Exception {
		BoardDTO dto = null;
		List<FileDTO> f_dto = null;
		dto = boardService.read(bno);
		//f_dto = boardService.getFileInfo(bno);
		f_dto = boardService.f_read(bno);
		System.out.println("꺼낸 내용 : "+dto.getContents());
		Map<String, Object> map = new HashMap<>();
		mav.setViewName("board/read");
		map.put("postNum", postNum);
		map.put("curPage", curPage);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("dto", dto);
		map.put("f_dto", f_dto);
		mav.addObject("map", map);
		return mav;
	}

	// 게시글 수정 페이지 이동
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam(value = "bno") int bno, @RequestParam(value = "curPage") int curPage,
			@RequestParam(value = "search_option") String search_option,
			@RequestParam(value = "keyword") String keyword, Model model, ModelAndView mav,
			@RequestParam(value = "postNum", required = false, defaultValue = "10") int postNum) throws Exception {
		BoardDTO dto = null;
		List<FileDTO> f_dto = null;
		f_dto = boardService.f_read(bno);
		int loop = 3 - (f_dto.size());
		dto = boardService.read(bno);
		model.addAttribute("whiteList", whiteList);
		model.addAttribute("postNum", postNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("search_option", search_option);
		model.addAttribute("keyword", keyword);
		model.addAttribute("loop", loop);
		model.addAttribute("f_dto", f_dto);
		dto.setTitle(decoding(dto.getTitle()));
		dto.setContents(decoding(dto.getContents()));
		dto.setWriter(decoding(dto.getWriter()));
		mav.setViewName("board/edit");
		mav.addObject("dto", dto);
		return mav;
	}

	// 게시글 수정
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardDTO dto, @RequestParam(value = "bno") int bno,
			@RequestParam(value = "curPage") int curPage, @RequestParam(value = "search_option") String search_option,
			@RequestParam(value = "keyword") String keyword, 
			@RequestParam(value = "fileNo", defaultValue="0") List<Integer> fileNos, 
			@RequestParam("uploadFile") List<MultipartFile> files, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName = null;
		String saveName = null;
		long fileSize = 0;
		UUID uuid = null;
		FileDTO f_dto = null;
		String enc_keyword = URLEncoder.encode(keyword, "UTF-8");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String writer = request.getParameter("writer");
		map.put("bno", bno);
		map.put("fileNos", fileNos);
		dto.setBno(bno);
		dto.setTitle(encoding(title));
		dto.setContents(contents);
		dto.setWriter(encoding(writer));
		boardService.update(dto);
		boardService.delFile(map);
		
		if(!files.isEmpty()) {
			for (MultipartFile file : files) {
				if (!file.getOriginalFilename().isEmpty()) {
					// System.out.println(file.getName() + "그리고 "+file.getOriginalFilename());
					uuid = UUID.randomUUID();
					fileName = file.getOriginalFilename();
					saveName = uuid + "_" + fileName;
					fileSize = file.getSize();
					file.transferTo(new File(f_dto.SAVE_FILE_PATH + saveName));
					f_dto = new FileDTO(dto.getBno(), saveName, fileName, fileSize);
					boardService.update_fileUpload(f_dto);
				}
			}
		}
		return "redirect:/board/read?bno=" + bno + "&curPage=" + curPage + "&search_option=" + search_option
				+ "&keyword=" + enc_keyword;
	}

	// 게시글 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "bno") int bno, @RequestParam(value = "curPage") int curPage,
			@RequestParam(value = "search_option") String search_option,
			@RequestParam(value = "keyword") String keyword) throws Exception {
		String enc_keyword = URLEncoder.encode(keyword, "UTF-8"); // 컨트롤러에서 컨트롤러로 전송 간에 한글 깨짐 방지
		boardService.delete(bno);
		boardService.delete2(bno);
		return "redirect:/board/list?curPage=" + curPage + "&search_option=" + search_option + "&keyword="
				+ enc_keyword;
	}
	
	// 게시글 삭제
	@ResponseBody
	@RequestMapping(value = "/deleteList", method = RequestMethod.POST)
	public int select_delete(@RequestParam(value="chbox[]") List<String> chArr, HttpSession session) throws Exception {
		Object loginCheck = session.getAttribute("member");
		int result = 0;
		int bno = 0;
		if(loginCheck != null) {
			for(String i : chArr) {
				bno = Integer.parseInt(i);
				boardService.delete(bno);
				boardService.delete2(bno);
			}
			result = 1;
		}
		return result;
	}
	
	// CK에디터 이미지 파일 업로드 컨트롤러
	@ResponseBody
	@SuppressWarnings("resource")
	@RequestMapping(value = "/ckUpload")
	public void ckFileUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html); charset=utf-8");

		OutputStream out = null;
		PrintWriter printWriter = null;

		String fileName = upload.getOriginalFilename(); // 첨부파일 이름
		byte[] bytes = upload.getBytes(); // 첨부파일을 바이트 배열로 저장

		// 업로드할 디렉토리 경로 + fileName //물리적 실제 저장소
		String uploadPath = UploadPath.path(request) + fileName;

		out = new FileOutputStream(new File(uploadPath));
		out.write(bytes);

		printWriter = response.getWriter();

		// URL상에서 볼 수 있는 이미지 경로
		String fileUrl = "/resources/ckupload/" + fileName;

		JsonObject json = new JsonObject();

		json.addProperty("uploaded", 1);
		json.addProperty("fileName", fileName);
		json.addProperty("url", fileUrl);

		printWriter.println(json);
		printWriter.flush();
	}

	public static class UploadPath {
		public static String attach_path = "/resources/ckupload/";

		public static String path(HttpServletRequest request) {
			String uploadPath = "/";
			try {
				String root_path = request.getSession().getServletContext().getRealPath("/resources/ckupload/");
				return root_path;
			} catch (Exception e) {
				e.printStackTrace();

				return uploadPath;
			}
		}
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
