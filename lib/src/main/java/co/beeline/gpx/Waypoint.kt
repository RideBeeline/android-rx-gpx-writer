package co.beeline.gpx

import co.beeline.gpx.xml.XmlWritable
import co.beeline.gpx.xml.XmlWrite
import io.reactivex.Observable

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
                optionalTagWithText("ele", ele?.toString()),
                optionalTagWithText("name", name)
        )
}
