package com.deemsys.project.pdfcrashreport;

import java.util.Comparator;

public class ReportUnitPageForm {
    private String unitNumber;
    private String ownerName;
    private String ownerPhoneNumber;
    private String ownerAddress;
    private String occupants;
    private String insuranceCompany;
    private String policyNumber;
    private String damageScale;
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}
	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}
	public String getOwnerAddress() {
		return ownerAddress;
	}
	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}
	public String getOccupants() {
		return occupants;
	}
	public void setOccupants(String occupants) {
		this.occupants = occupants;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getDamageScale() {
		return damageScale;
	}
	public void setDamageScale(String damageScale) {
		this.damageScale = damageScale;
	}
	public ReportUnitPageForm(String unitNumber, String ownerName,
			String ownerPhoneNumber, String ownerAddress, String occupants,
			String insuranceCompany, String policyNumber, String damageScale) {
		super();
		this.unitNumber = unitNumber;
		this.ownerName = ownerName;
		this.ownerPhoneNumber = ownerPhoneNumber;
		this.ownerAddress = ownerAddress;
		this.occupants = occupants;
		this.insuranceCompany = insuranceCompany;
		this.policyNumber = policyNumber;
		this.damageScale = damageScale;
	}
    
		public static Comparator<ReportUnitPageForm> ReportUnitPageComparitor 
			    = new Comparator<ReportUnitPageForm>() {
			
			@Override
			public int compare(ReportUnitPageForm reportUnitPage1, ReportUnitPageForm reportUnitPage2) {
				// TODO Auto-generated method stub
				Integer fruitName1 = Integer.parseInt(reportUnitPage1.getUnitNumber().trim());
				Integer fruitName2 = Integer.parseInt(reportUnitPage2.getUnitNumber().trim());
				
				//ascending order
				return fruitName1.compareTo(fruitName2);
			}

};

}
