package id.daprin.iaknewsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ApiResponse{

	@SerializedName("sortBy")
	private String sortBy;

	@SerializedName("source")
	private String source;

	@SerializedName("articles")
	private List<ArticlesItem> articles;

	@SerializedName("status")
	private String status;
}