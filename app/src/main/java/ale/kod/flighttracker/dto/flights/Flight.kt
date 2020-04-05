package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("iataNumber")
    val iataNumber: String?,
    @SerializedName("icaoNumber")
    val icaoNumber: String?,
    @SerializedName("number")
    val number: String?
)