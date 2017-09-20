package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import beeline.co.gpx.xml.writeTo
import rx.Observable
import rx.Single
import java.io.Writer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

data class Gpx(
        val creator: String,
        val metadata: Metadata = Metadata(),
        val waypoints: Observable<Waypoint> = Observable.empty(),
        val tracks: Observable<Track> = Observable.empty(),
        val routes: Observable<Route> = Observable.empty()
) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = newTag("gpx",
                withAttribute("xmlns", "http://www.topografix.com/GPX/1/1"),
                withAttribute("version", "1.1"),
                withAttribute("creator", creator),
                metadata.writeOperations,
                waypoints.concatMap { it.writeOperations },
                tracks.concatMap { it.writeOperations },
                routes.concatMap { it.writeOperations }
        )

    fun writeTo(writer: Writer, charset: Charset = StandardCharsets.UTF_8): Single<Writer> =
            writeOperations.writeTo(writer, charset)

}
