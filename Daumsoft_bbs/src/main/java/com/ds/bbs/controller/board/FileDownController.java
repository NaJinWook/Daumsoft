package com.ds.bbs.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ds.bbs.model.board.dto.FileDTO;
import com.ds.bbs.service.board.BoardService;

@Controller
public class FileDownController {
	@Inject
	BoardService boardService;
	
	@RequestMapping(value = "fileDownload.do")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int fileNo = Integer.parseInt(request.getParameter("fileNo"));
		FileDTO f_dto = null;
		
		String fileName = null;
		String originalName = null;
		String realFileName = "";
		String filePath = null;
		
		System.out.println("fileNo : " + fileNo);
		
		f_dto = boardService.selectFile(fileNo);
		
		originalName = f_dto.getFileName();
		fileName = f_dto.getSaveName();
		filePath = f_dto.getFilePath();

		System.out.println("fileName : " + fileName);
		System.out.println("filePath : " + filePath);
		
//		try {
//			String browser = request.getHeader("User-Agent");
//			// 파일 인코딩
//			if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
//				fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+","%20");
//			} else {
//				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//			}
//		} catch (UnsupportedEncodingException e) {
//			System.out.println("UnsupportedEncodingException");
//		}
		realFileName = filePath +fileName;
		System.out.println(realFileName);
		File file = new File(realFileName);
		if(!file.exists()) {
			return ;
		}
		
		// 파일명 지정
		response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(originalName, "UTF-8")+"\"");
        
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(realFileName);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (Exception e) {
            System.out.println("FileNotFoundException : " + e);
        }
	}
}
