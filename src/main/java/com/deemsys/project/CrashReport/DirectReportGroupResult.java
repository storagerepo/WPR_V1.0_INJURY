package com.deemsys.project.CrashReport;

import java.util.List;

public class DirectReportGroupResult {
	private Long totalNoOfRecords;
	private List<DirectReportGroupListByArchive> directReportGroupListByArchives;
	public Long getTotalNoOfRecords() {
		return totalNoOfRecords;
	}
	public void setTotalNoOfRecords(Long totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}
	public List<DirectReportGroupListByArchive> getDirectReportGroupListByArchives() {
		return directReportGroupListByArchives;
	}
	public void setDirectReportGroupListByArchives(
			List<DirectReportGroupListByArchive> directReportGroupListByArchives) {
		this.directReportGroupListByArchives = directReportGroupListByArchives;
	}
	public DirectReportGroupResult(Long totalNoOfRecords,
			List<DirectReportGroupListByArchive> directReportGroupListByArchives) {
		super();
		this.totalNoOfRecords = totalNoOfRecords;
		this.directReportGroupListByArchives = directReportGroupListByArchives;
	}
	public DirectReportGroupResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
