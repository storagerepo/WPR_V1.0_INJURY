package com.deemsys.project.patients;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class PatientFileUpload {
	
	private List<MultipartFile> multipartFiles;

	public List<MultipartFile> getMultipartFiles() {
		return multipartFiles;
	}

	public void setMultipartFiles(List<MultipartFile> multipartFiles) {
		this.multipartFiles = multipartFiles;
	}
	
	
}