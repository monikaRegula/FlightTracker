package ale.kod.flighttracker.adapter

import ale.kod.flighttracker.R
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.marker_map_adapter.view.*

class MarkerInfoAdapter(val context: Context) : AppCompatActivity(), GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {
        var windowView = (context as Activity).layoutInflater.inflate(R.layout.marker_map_adapter, null)
        var flightDetailsInfoWindow: String? = marker?.snippet as String?

        windowView.title.text = marker?.title.toString()
        windowView.snippet.text = flightDetailsInfoWindow?.toUpperCase()

        return windowView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

}