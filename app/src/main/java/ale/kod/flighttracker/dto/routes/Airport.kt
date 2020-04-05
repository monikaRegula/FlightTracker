package ale.kod.flighttracker.dto.routes

data class Airport (val airportId: Int, val nameAirport: String, val codeIataAirport: String, val codeIcaoAirport: String,
val latitudeAirport: String, val longitudeAirport: String, val geonameId: String?,val timezone: String,val GMT: String,val phone: String,
val nameCountry: String, val codeIso2Country: String, val codeIataCity: String){

}