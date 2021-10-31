package POJOtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CountryInfo{

	@SerializedName("post code")
	@Expose
	private String postCode;

	@Expose
	private String country;

	@SerializedName("country abbreviation")
	@Expose
	private String abbreviation;

	@SerializedName("places")
	@Expose
	private List<PlacesItem> placesItems=null;



}




/*{
		"post code": "22031",
		"country": "United States",
		"country abbreviation": "US",
		"places": [
		{
		"place name": "Fairfax",
		"longitude": "-77.2649",
		"state": "Virginia",
		"state abbreviation": "VA",
		"latitude": "38.8604"
		}
		]
		}
		*/