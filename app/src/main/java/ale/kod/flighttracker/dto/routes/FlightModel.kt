package ale.kod.flighttracker.dto.routes

//flight route
data class FlightModel(
    val airlineIata: String?,
    val airlineIcao: String?,
    val arrivalIata: String?,
    val arrivalIcao: String?,
    val arrivalTerminal: String?,
    val arrivalTime: String?,
    val codeshares: List<Codeshare?>?,
    val departureIata: String?,
    val departureIcao: String?,
    val departureTerminal: String?,
    val departureTime: String?,
    val flightNumber: String?,
    val regNumber: List<String?>?
)