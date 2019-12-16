package co.beeline.gpx

import co.beeline.gpx.xml.XmlWritable
import co.beeline.gpx.xml.XmlWrite
import io.reactivex.Observable

data class TrackSegment(
        val points: Observable<TrackPoint>
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("trkseg", points.concatMap { it.writeOperations })
}