package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class Speed(
    @SerializedName("horizontal")
    val horizontal: Double?,
    @SerializedName("isGround")
    val isGround: Double?,
    @SerializedName("vspeed")
    val vspeed: Double?
)