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
                Name(name).writeOperations,
                Number(number).writeOperations,
                segments.concatMap { it.writeOperations }
        )

}