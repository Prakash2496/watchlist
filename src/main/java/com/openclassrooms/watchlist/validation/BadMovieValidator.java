package com.openclassrooms.watchlist.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.openclassrooms.watchlist.model.WatchlistItem;

public class BadMovieValidator implements ConstraintValidator<BadMovie, com.openclassrooms.watchlist.model.WatchlistItem>{

	@Override
	public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
		
		return !(Double.valueOf(value.getRating()) < 6 && value.getComment().length()<15);
	}
}