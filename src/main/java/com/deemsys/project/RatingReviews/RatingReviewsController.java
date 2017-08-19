
package com.deemsys.project.RatingReviews;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Deemsys
 *
 */
@Controller
public class RatingReviewsController {
	
	@Autowired
	RatingReviewsService ratingReviewsService;

    @RequestMapping(value="/getRatingReviews",method=RequestMethod.GET)
	public String getRatingReviews(ModelMap model)
	{
    	model.addAttribute("ratingReviewsForm",ratingReviewsService.getRatingReviewsyUserId());
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
	
    // Get Ratings & Reviews By passing User Id
	@RequestMapping(value="/getRatingReviewsByPassingUserId",method=RequestMethod.GET)
	public String getRatingReviewsByPassingUserId(@RequestParam("userId") Integer userId,ModelMap model)
	{
    	model.addAttribute("ratingReviewsForm",ratingReviewsService.getRatingReviewsByPassingUserId(userId));
    	model.addAttribute("requestSuccess",true);
		return "/returnPage";
	}
    
    @RequestMapping(value="/mergeRatingReviews",method=RequestMethod.POST)
   	public String mergeRatingReviews(@RequestBody RatingReviewsForm ratingReviewsForm,ModelMap model)
   	{
    	ratingReviewsService.mergeRatingReviews(ratingReviewsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/saveUpdateRatingReviews",method=RequestMethod.POST)
   	public String saveRatingReviews(@RequestBody RatingReviewsForm ratingReviewsForm,ModelMap model)
   	{
    	ratingReviewsService.saveRatingReviews(ratingReviewsForm);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
   
    
    @RequestMapping(value="/deleteRatingReviews",method=RequestMethod.POST)
   	public String deleteRatingReviews(@RequestParam("id") Integer id,ModelMap model)
   	{
    	
    	ratingReviewsService.deleteRatingReviews(id);
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
    
    @RequestMapping(value="/getAllRatingReviewss",method=RequestMethod.GET)
   	public String getAllRatingReviewss(ModelMap model)
   	{
    	model.addAttribute("ratingReviewsForms",ratingReviewsService.getRatingReviewsList());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
	
    // Get Over All Rating
    @RequestMapping(value="/getOverAllRatingReviewss",method=RequestMethod.GET)
   	public String getOverAllRatingReviewss(ModelMap model)
   	{
    	model.addAttribute("ratingViewForm",ratingReviewsService.calculateOverAllRating());
    	model.addAttribute("requestSuccess",true);
   		return "/returnPage";
   	}
}
