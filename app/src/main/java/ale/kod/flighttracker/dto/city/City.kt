package ale.kod.flighttracker.dto.city

data class City( var cityId: Int,var nameCity : String,var  codeIataCity: String,var codeIso2Country: String, var latitudeCity: String,
        var longitudeCity: String, var timezone: String, var GMT: String, var geonameId: String){

        override fun toString(): String {
        return codeIataCity
    }
}