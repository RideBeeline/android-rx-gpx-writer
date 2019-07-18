package co.beeline.gpx.xml

import io.reactivex.Observable


interface XmlWritable {

    val writeOperations: Observable<XmlWrite>

    fun withAttribute(name: String, value: String): Observable<XmlWrite> =
            Observable.just { xml -> xml.attribute(null, name, value) }

    fun withText(text: String): Observable<XmlWrite> =
            Observable.just { xml -> xml.text(text) }

    fun optionalTagWithText(tag: String, value: String?) =
            if (value != null) newTag(tag, withText(value))
            else Observable.empty()

    fun newTag(name: String, vararg content: Observable<XmlWrite>): Observable<XmlWrite> =
            Observable.concat(
                    Observable.just { xml -> xml.startTag(null, name) },
                    Observable.concat(Observable.fromIterable(content.asIterable())),
                    Observable.just { xml -> xml.endTag(null, name) }
            )
}
