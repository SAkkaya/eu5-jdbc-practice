package POJOtest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlacesItem{

    @SerializedName("place name")
    @Expose
    private String placesName;
    @Expose
    private String longtitude;
    @Expose
    private String state;
    @SerializedName("state abbreviation")
    @Expose
    private String stateAbbreviation;
    @Expose
    private String latitude;


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