package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class FlightInfoItem(
    @SerializedName("aircraft")
    val aircraft: Aircraft,
    @SerializedName("airline")
    val airline: Airline,
    @SerializedName("arrival")
    val arrival: Arrival,
    @SerializedName("departure")
    val departure: Departure,
    @SerializedName("flight")
    val flight: Flight,
    @SerializedName("geography")
    val geography: Geography,
    @SerializedName("speed")
    val speed: Speed,
    @SerializedName("status")
    val status: String,
    @SerializedName("system")
    val system: System
)