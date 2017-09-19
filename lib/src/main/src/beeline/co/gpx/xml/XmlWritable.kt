package beeline.co.gpx.xml

import rx.Observable

interface XmlWritable {

    val writeOperations: Observable<XmlWrite>

    fun withAttribute(name: String, value: String): Observable<XmlWrite> = Observable.just { xml -> xml.attribute(null, name, value) }

    fun withText(text: String): Observable<XmlWrite> = Observable.just { xml -> xml.text(text) }

    fun newTag(
            name: String,
            vararg content: Observable<XmlWrite>
    ): Observable<XmlWrite> = Observable.concat(
            Observable.just { xml -> xml.startTag(null, name) },
            Observable.concat(Observable.from(content)),
            Observable.just { xml -> xml.endTag(null, name) }
    )

}
