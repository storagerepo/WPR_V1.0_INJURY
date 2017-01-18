package com.deemsys.project.RatingReviews;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.RatingReviews;
/**
 * 
 * @author Deemsys
 *
 */
public interface RatingReviewsDAO extends IGenericDAO<RatingReviews>{
	public RatingReviews getRatingReviewsByUserId(Integer userId);
}
