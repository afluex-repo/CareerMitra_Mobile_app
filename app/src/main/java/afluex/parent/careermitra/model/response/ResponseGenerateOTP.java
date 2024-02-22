package afluex.parent.careermitra.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseGenerateOTP{

	@SerializedName("Message")
	private String message;

	@SerializedName("Body")
	private String body;

	@SerializedName("StatusCode")
	private Integer statusCode;

	public String getMessage(){
		return message;
	}

	public String getBody(){
		return body;
	}

	public Integer getStatusCode(){
		return statusCode;
	}
}