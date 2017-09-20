package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Waypoint(
        val lat: Double,
        val lon: Double,
        val ele: Double? = null,
        val name: String? = null
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("wpt",
                withAttribute("lat", lat.toString()),
                withAttribute("lon", lon.toString()),
                Elevation(ele).writeOperations,
                Name(name).writeOperations
        )

}
