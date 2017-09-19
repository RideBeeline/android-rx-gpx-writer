package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable
import java.text.DecimalFormat

data class TrackPoint(
        val lat: Double,
        val lon: Double,
        val time: Long? = null,
        val ele: Int? = null
) : XmlWritable {

    companion object {
        private val latLonFormat = DecimalFormat("#.00000000")
    }

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("trkpt",
                withAttribute("lat", latLonFormat.format(lat)),
                withAttribute("lon", latLonFormat.format(lon)),
                if (ele != null) Elevation(ele).writeOperations else Observable.empty(),
                if (time != null) Time(time).writeOperations else Observable.empty()
        )

}
