package com.deemsys.project.RatingReviews;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.RatingReviews;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * RatingReviews 	 - Entity
 * ratingReviews 	 - Entity Object
 * ratingReviewss 	 - Entity List
 * ratingReviewsDAO   - Entity DAO
 * ratingReviewsForms - EntityForm List
 * RatingReviewsForm  - EntityForm
 *
 */
@Service
@Transactional
public class RatingReviewsService {

	@Autowired
	RatingReviewsDAO ratingReviewsDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	LoginService loginService;
	
	//Get All Entries
	public List<RatingReviewsForm> getRatingReviewsList()
	{
		List<RatingReviewsForm> ratingReviewsForms=new ArrayList<RatingReviewsForm>();
		
		List<RatingReviews> ratingReviewss=new ArrayList<RatingReviews>();
		
		ratingReviewss=ratingReviewsDAO.getAll();
		
		for (RatingReviews ratingReviews : ratingReviewss) {
			//TODO: Fill the List
			RatingReviewsForm ratingReviewsForm = new RatingReviewsForm(ratingReviews.getRatingReviewId(), ratingReviews.getUsers().getUserId(), InjuryConstants.getRoleAsText(ratingReviews.getUsers().getRoles().getRole()), ratingReviews.getRatingQ1(), ratingReviews.getRatingQ2(), ratingReviews.getRatingQ3(), 
					ratingReviews.getRatingQ4(), ratingReviews.getRatingQ5(), ratingReviews.getReviewQ1(), ratingReviews.getReviewQ2(), ratingReviews.getReviewQ3(), ratingReviews.getReviewQ4(), ratingReviews.getOverallRating(), InjuryConstants.convertUSAFormatWithTime(ratingReviews.getReviewDateTime()), ratingReviews.getStatus());
			ratingReviewsForms.add(ratingReviewsForm);
		}
		
		return ratingReviewsForms;
	}
	
	//Get Particular Entry
	public RatingReviewsForm getRatingReviewsyUserId()
	{
		RatingReviews ratingReviews=new RatingReviews();
		
		ratingReviews=ratingReviewsDAO.getRatingReviewsByUserId(loginService.getCurrentUserID());
		
		//TODO: Convert Entity to Form
		//Start
		RatingReviewsForm ratingReviewsForm=new RatingReviewsForm();
		if(ratingReviews!=null){
			ratingReviewsForm=new RatingReviewsForm(ratingReviews.getRatingReviewId(), ratingReviews.getUsers().getUserId(), InjuryConstants.getRoleAsText(ratingReviews.getUsers().getRoles().getRole()),
					ratingReviews.getRatingQ1(), ratingReviews.getRatingQ2(), ratingReviews.getRatingQ3(), ratingReviews.getRatingQ4(), ratingReviews.getRatingQ5(), 
					ratingReviews.getReviewQ1(), ratingReviews.getReviewQ2(), ratingReviews.getReviewQ3(), ratingReviews.getReviewQ4(), ratingReviews.getOverallRating(), InjuryConstants.convertUSAFormatWithTime(ratingReviews.getReviewDateTime()), ratingReviews.getStatus());
		}
		
		//End
		
		return ratingReviewsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeRatingReviews(RatingReviewsForm ratingReviewsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		RatingReviews ratingReviews=new RatingReviews();
		
		//Logic Ends
		
		
		ratingReviewsDAO.merge(ratingReviews);
		return 1;
	}
	
	//Save an Entry
	public int saveRatingReviews(RatingReviewsForm ratingReviewsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Users users = usersDAO.get(loginService.getCurrentUserID());
		
		RatingReviews ratingReviews=new RatingReviews(users, ratingReviewsForm.getRatingQ1(), ratingReviewsForm.getRatingQ2(), 
				ratingReviewsForm.getRatingQ3(), ratingReviewsForm.getRatingQ4(), ratingReviewsForm.getRatingQ5(), ratingReviewsForm.getReviewQ1(), ratingReviewsForm.getReviewQ2(), 
				ratingReviewsForm.getReviewQ3(), ratingReviewsForm.getReviewQ4(), ratingReviewsForm.getOverallRating(), new Date(), 1);
		
		//Logic Ends
		
		ratingReviewsDAO.save(ratingReviews);
		return 1;
	}
	
	//Update an Entry
	public int updateRatingReviews(RatingReviewsForm ratingReviewsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		RatingReviews ratingReviews=new RatingReviews();
		
		//Logic Ends
		
		ratingReviewsDAO.update(ratingReviews);
		return 1;
	}
	
	//Delete an Entry
	public int deleteRatingReviews(Integer id)
	{
		ratingReviewsDAO.delete(id);
		return 1;
	}
	
	public RatingViewForm calculateOverAllRating(){
		RatingViewForm ratingViewForm = new RatingViewForm();
		// Average Rating
		Double callerAdminAverageRating=0.00;
		Double lawyerAdminAverageRating=0.00;
		
		BigDecimal callerAdminRating=new BigDecimal(0);
		BigDecimal lawyerAdminRating=new BigDecimal(0);
		BigDecimal dealerManagerRating=new BigDecimal(0);
		// Total Rating Count
		Integer callerAdminReviewCount=0;
		Integer lawyerAdminReviewCount=0;
		Integer dealerManagerReviewCount=0;
		List<RatingReviews> ratingReviewss=new ArrayList<RatingReviews>();
		ratingReviewss=ratingReviewsDAO.getAll();
		for (RatingReviews ratingReviews : ratingReviewss) {
			Integer roleId=ratingReviews.getUsers().getRoles().getRoleId();
			if(roleId.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID)){
				callerAdminRating=callerAdminRating.add(ratingReviews.getOverallRating());
				callerAdminReviewCount++;
			}else if(roleId.equals(InjuryConstants.INJURY_CALLER_ROLE_ID)){
				callerAdminRating=callerAdminRating.add(ratingReviews.getOverallRating());
				callerAdminReviewCount++;
			}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID)){
				lawyerAdminRating=lawyerAdminRating.add(ratingReviews.getOverallRating());
				lawyerAdminReviewCount++;
			}else if(roleId.equals(InjuryConstants.INJURY_LAWYER_ROLE_ID)){
				lawyerAdminRating=lawyerAdminRating.add(ratingReviews.getOverallRating());
				lawyerAdminReviewCount++;
			}if(roleId.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID)){
				dealerManagerRating=dealerManagerRating.add(ratingReviews.getOverallRating());
				dealerManagerReviewCount++;
			}else if(roleId.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE_ID)){
				dealerManagerRating=dealerManagerRating.add(ratingReviews.getOverallRating());
				dealerManagerReviewCount++;
			}
		}
		
		if(callerAdminReviewCount>0){
			callerAdminAverageRating=callerAdminRating.doubleValue()/callerAdminReviewCount.doubleValue();
		}
		if(lawyerAdminReviewCount>0){
			lawyerAdminAverageRating=lawyerAdminRating.doubleValue()/lawyerAdminReviewCount.doubleValue();	
		}
	
		ratingViewForm=new RatingViewForm(String.format( "%.1f", callerAdminAverageRating), String.format( "%.1f", lawyerAdminAverageRating), callerAdminReviewCount, lawyerAdminReviewCount, (callerAdminReviewCount+lawyerAdminReviewCount));
		return ratingViewForm;
	}

	public RatingReviewsForm getRatingReviewsByPassingUserId(Integer userId) {
		// TODO Auto-generated method stub
		RatingReviews ratingReviews=new RatingReviews();
		
		ratingReviews=ratingReviewsDAO.getRatingReviewsByUserId(userId);
		
		//TODO: Convert Entity to Form
		//Start
		RatingReviewsForm ratingReviewsForm=new RatingReviewsForm();
		if(ratingReviews!=null){
			ratingReviewsForm=new RatingReviewsForm(ratingReviews.getRatingReviewId(), ratingReviews.getUsers().getUserId(), InjuryConstants.getRoleAsText(ratingReviews.getUsers().getRoles().getRole()),
					ratingReviews.getRatingQ1(), ratingReviews.getRatingQ2(), ratingReviews.getRatingQ3(), ratingReviews.getRatingQ4(), ratingReviews.getRatingQ5(), 
					ratingReviews.getReviewQ1(), ratingReviews.getReviewQ2(), ratingReviews.getReviewQ3(), ratingReviews.getReviewQ4(), ratingReviews.getOverallRating(), InjuryConstants.convertUSAFormatWithTime(ratingReviews.getReviewDateTime()), ratingReviews.getStatus());
		}
		
		//End
		
		return ratingReviewsForm;
	};
}
