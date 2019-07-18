package co.beeline.gpx

import co.beeline.gpx.xml.XmlWritable
import co.beeline.gpx.xml.XmlWrite
import io.reactivex.Observable

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