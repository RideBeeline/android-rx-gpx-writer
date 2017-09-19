package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class TrackSegment(
        val points: Observable<TrackPoint>
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("trkseg",
                points.concatMap { it.writeOperations }
        )

}