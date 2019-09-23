package com.ds.bbs.model.board.dto;

public class FileDTO {
	public static final String SAVE_FILE_PATH = "C:\\Users\\Daumsoft\\git\\Daumsoft\\Daumsoft_bbs\\src\\main\\webapp\\WEB-INF\\upload\\";
	private int fileNo;
	private int bno;
	private String saveName;
	private String fileName;
	private String filePath;
	private long fileSize;
	private String fileDelYn;
	private String fileUserName;
	
	public FileDTO() {}

	public FileDTO(String saveName, String fileName, long fileSize,
			String fileUserName) {
		this.saveName = saveName;
		this.fileName = fileName;
		this.filePath = SAVE_FILE_PATH;
		this.fileSize = fileSize;
		this.fileUserName = fileUserName;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDelYn() {
		return fileDelYn;
	}

	public void setFileDelYn(String fileDelYn) {
		this.fileDelYn = fileDelYn;
	}

	public String getFileUserName() {
		return fileUserName;
	}

	public void setFileUserName(String fileUserName) {
		this.fileUserName = fileUserName;
	}

	@Override
	public String toString() {
		return "FileDTO [fileNo=" + fileNo + ", bno=" + bno + ", saveName=" + saveName + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", fileSize=" + fileSize + ", fileDelYn=" + fileDelYn + ", fileUserName="
				+ fileUserName + "]";
	}
}
