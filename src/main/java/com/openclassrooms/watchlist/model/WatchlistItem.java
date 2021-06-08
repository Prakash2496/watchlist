package com.openclassrooms.watchlist.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.openclassrooms.watchlist.validation.BadMovie;
import com.openclassrooms.watchlist.validation.GoodMovie;
import com.openclassrooms.watchlist.validation.Priority;
import com.openclassrooms.watchlist.validation.Rating;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@GoodMovie
@BadMovie
@Document(collection = "watchlistItems")
public class WatchlistItem {

	@Id
	private String id;

	@NotEmpty(message = "Please enter the title")
	private String title;

	@Rating
	private String rating;

	@Priority
	private String priority;

	@Size(max = 50, message = "Comment should be maximum 50 characters")
	private String comment;

	private Date dateCreated;
	private Date lastEditDate;

	private String userId;

	public static int index = 0;

	public WatchlistItem() {
		// this.id = index ++;
	}

	public WatchlistItem(@NotEmpty(message = "Please enter the title") String title, String rating, String priority,
			@Size(max = 50, message = "Comment should be maximum 50 characters") String comment, Date dateCreated,
			Date lastEditDate, String userId) {
		this.title = title;
		this.rating = rating;
		this.priority = priority;
		this.comment = comment;
		this.dateCreated = dateCreated;
		this.lastEditDate = lastEditDate;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
