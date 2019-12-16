package co.beeline.gpx

import co.beeline.gpx.xml.XmlWritable
import co.beeline.gpx.xml.XmlWrite
import io.reactivex.Observable

data class Track(
        val segments: Observable<TrackSegment>,
        val name: String? = null,
        val number: Int? = null
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("trk",
                optionalTagWithText("name", name),
                optionalTagWithText("number", number?.toString()),
                segments.concatMap { it.writeOperations }
        )
}