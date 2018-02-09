package com.deemsys.project.activity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.Activity;
/**
 * 
 * @author Deemsys
 *
 * Activity 	 - Entity
 * activity 	 - Entity Object
 * activitys 	 - Entity List
 * activityDAO   - Entity DAO
 * activityForms - EntityForm List
 * ActivityForm  - EntityForm
 *
 */
@Service
@Transactional
public class ActivityService {

	@Autowired
	ActivityDAO activityDAO;
	
	//Get All Entries
	public List<ActivityForm> getActivityList()
	{
		List<ActivityForm> activityForms=new ArrayList<ActivityForm>();
		
		List<Activity> activitys=new ArrayList<Activity>();
		
		activitys=activityDAO.getAll();
		
		for (Activity activity : activitys) {
			//TODO: Fill the List
			ActivityForm activityForm = new ActivityForm(activity.getId(), activity.getName(), activity.getStatus());
			activityForms.add(activityForm);
		}
		
		return activityForms;
	}
	
	//Get Particular Entry
	public ActivityForm getActivity(Integer getId)
	{
		Activity activity=new Activity();
		
		activity=activityDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		ActivityForm activityForm=new ActivityForm();
		
		//End
		
		return activityForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeActivity(ActivityForm activityForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Activity activity=new Activity();
		
		//Logic Ends
		
		
		activityDAO.merge(activity);
		return 1;
	}
	
	//Save an Entry
	public int saveActivity(ActivityForm activityForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Activity activity=new Activity();
		
		//Logic Ends
		
		activityDAO.save(activity);
		return 1;
	}
	
	//Update an Entry
	public int updateActivity(ActivityForm activityForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Activity activity=new Activity();
		
		//Logic Ends
		
		activityDAO.update(activity);
		return 1;
	}
	
	//Delete an Entry
	public int deleteActivity(Integer id)
	{
		activityDAO.delete(id);
		return 1;
	}
	
	
	
}
