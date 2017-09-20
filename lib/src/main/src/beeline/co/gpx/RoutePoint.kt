package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class RoutePoint(
        val lat: Double,
        val lon: Double,
        val time: Long? = null,
        val ele: Double? = null,
        val name: String? = null
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("rtept",
                withAttribute("lat", lat.toString()),
                withAttribute("lon", lon.toString()),
                if (ele != null) Elevation(ele).writeOperations else Observable.empty(),
                if (time != null) Time(time).writeOperations else Observable.empty(),
                if (name != null) Name(name).writeOperations else Observable.empty()
        )

}