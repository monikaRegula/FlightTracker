package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class Geography(
    @SerializedName("altitude")
    val altitude: Double?,
    @SerializedName("direction")
    val direction: Double?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
)