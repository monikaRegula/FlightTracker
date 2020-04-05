package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("iataCode")
    val iataCode: String?,
    @SerializedName("icaoCode")
    val icaoCode: String?
)