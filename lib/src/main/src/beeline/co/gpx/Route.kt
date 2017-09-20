package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Route(
        val name: String? = null,
        val points: Observable<RoutePoint>
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("rte",
                optionalTagWithText("name", name),
                points.concatMap { it.writeOperations }
        )

}