package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class Aircraft(
    @SerializedName("iataCode")
    val iataCode: String?,
    @SerializedName("icao24")
    val icao24: String?,
    @SerializedName("icaoCode")
    val icaoCode: String?,
    @SerializedName("regNumber")
    val regNumber: String?
)