package com.deemsys.project.PoliceAgency;


/**
 * 
 * @author Deemsys
 * 
 */
public class PoliceAgencyForm {

	private Integer id;
	private Integer countyId;
	private Integer agencyId;
	private String name;
	private Integer mapId;
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public PoliceAgencyForm(Integer id, Integer countyId, Integer agencyId,
			String name, Integer mapId, Integer status) {
		super();
		this.id = id;
		this.countyId = countyId;
		this.agencyId = agencyId;
		this.name = name;
		this.mapId = mapId;
		this.status = status;
	}

	public PoliceAgencyForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
