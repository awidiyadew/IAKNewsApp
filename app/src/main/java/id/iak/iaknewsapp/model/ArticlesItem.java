package id.iak.iaknewsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class ArticlesItem implements Parcelable {

	public ArticlesItem() {
	}

	@SerializedName("publishedAt")
	private String publishedAt;

	@SerializedName("author")
	private String author;

	@SerializedName("urlToImage")
	private String urlToImage;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toJson(){
		return new Gson().toJson(this);
	}

	public ArticlesItem fromJson(String newsJson){
		return new Gson().fromJson(newsJson, ArticlesItem.class);
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.publishedAt);
		dest.writeString(this.author);
		dest.writeString(this.urlToImage);
		dest.writeString(this.description);
		dest.writeString(this.title);
		dest.writeString(this.url);
	}

	protected ArticlesItem(Parcel in) {
		this.publishedAt = in.readString();
		this.author = in.readString();
		this.urlToImage = in.readString();
		this.description = in.readString();
		this.title = in.readString();
		this.url = in.readString();
	}

	public static final Parcelable.Creator<ArticlesItem> CREATOR = new Parcelable.Creator<ArticlesItem>() {
		@Override
		public ArticlesItem createFromParcel(Parcel source) {
			return new ArticlesItem(source);
		}

		@Override
		public ArticlesItem[] newArray(int size) {
			return new ArticlesItem[size];
		}
	};
}