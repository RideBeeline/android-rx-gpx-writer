package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Number(val value: Int?) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = if (value != null) newTag("number", withText(value.toString()))
                else Observable.empty()

}