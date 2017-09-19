package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable
import java.text.DecimalFormat

data class Waypoint(
        val lat: Double,
        val lon: Double,
        val ele: Int? = null,
        val name: String? = null
) : XmlWritable {

    companion object {
        private val latLonFormat = DecimalFormat("#.00000000")
    }

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("wpt",
                withAttribute("lat", latLonFormat.format(lat)),
                withAttribute("lon", latLonFormat.format(lon)),
                Elevation(ele).writeOperations,
                Name(name).writeOperations
        )

}