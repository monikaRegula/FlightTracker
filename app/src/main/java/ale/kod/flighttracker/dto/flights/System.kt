package ale.kod.flighttracker.dto.flights

import com.google.gson.annotations.SerializedName

data class System(
    @SerializedName("squawk")
    val squawk: String?,
    @SerializedName("updated")
    val updated: String?
)