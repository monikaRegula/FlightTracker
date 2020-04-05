package pwr.edu.flighttracking

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class HttpGetRequest: AsyncTask<String, Void, String>() {
    companion object{
        val REQUEST_METHOD = "GET"
        val READ_TIMEOUT= 30000
        val CONNECTION_TIMEOUT = 30000
    }

    override fun doInBackground(vararg params: String?): String? {
        var result: String? = ""
        try{
            var stringUrl:String = params[0]!!
            var myURL = URL(stringUrl)
            myURL.openConnection()
            var connection: HttpURLConnection= myURL.openConnection() as HttpURLConnection
            connection.requestMethod = REQUEST_METHOD
            connection.readTimeout = READ_TIMEOUT
            connection.connectTimeout = CONNECTION_TIMEOUT

            connection.connect()

            var streamReader = InputStreamReader(connection.inputStream)
            var reader = BufferedReader(streamReader)
            var stringBuilder = StringBuilder()
            var inputLine = ""

            do {
                val inputLine  = reader.readLine()
                stringBuilder.append(inputLine)
            } while(inputLine != null)

            reader.close()
            streamReader.close()
            result = stringBuilder.toString()

        }catch (e: IOException){
            e.printStackTrace()
            result = null
        }
        return result
    }

}

