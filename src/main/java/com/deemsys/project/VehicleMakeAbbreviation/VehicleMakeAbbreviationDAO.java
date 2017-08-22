package com.deemsys.project.VehicleMakeAbbreviation;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.VehicleMakeAbbreviation;
/**
 * 
 * @author Deemsys
 *
 */
public interface VehicleMakeAbbreviationDAO extends IGenericDAO<VehicleMakeAbbreviation>{
		public VehicleMakeAbbreviation getVehicleMakeAbbreviationByMake(String vehicleMake);
		public VehicleMakeAbbreviationList getVehicleMakeAbbrevationsBySearch(SearchVehicleMakeAbbrevationForm searchVehicleMakeAbbrevationForm);
		public Integer deleteVehicleMakeAbbreviationByMake(String make);
		
}
