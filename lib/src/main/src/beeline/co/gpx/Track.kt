package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

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