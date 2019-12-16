package co.beeline.gpx.xml

import android.util.Xml
import io.reactivex.Observable
import io.reactivex.Single
import org.xmlpull.v1.XmlSerializer
import java.io.Writer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

typealias XmlWrite = (XmlSerializer) -> Unit

fun Observable<XmlWrite>.writeTo(writer: Writer, charset: Charset = StandardCharsets.UTF_8): Single<Writer> {
    return startWith { xml ->
        xml.setOutput(writer)
        xml.startDocument(charset.name(), null)
    }
            .concatWith(Observable.just { xml ->
                xml.endDocument()
                writer.close()
            })
            .reduce(
                    Xml.newSerializer().apply { setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true) },
                    { xml, write -> xml.apply { write(this) } }
            )
            .map { writer }
}
