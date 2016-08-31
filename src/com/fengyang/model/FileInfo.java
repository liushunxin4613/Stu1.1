package com.fengyang.model;

import java.io.File;

public class FileInfo {

	private File file;
	
	private String oldName;
	
	private String fileName;
	
	private boolean succeed;

	public FileInfo() {
	}
	
	/**
	 * @param file
	 * @param oldName
	 * @param fileName
	 * @param succeed
	 */
	public FileInfo(File file, String oldName, String fileName, boolean succeed) {
		super();
		this.file = file;
		this.oldName = oldName;
		this.fileName = fileName;
		this.succeed = succeed;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	
	
}
