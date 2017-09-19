package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Name(val name: String?) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = if (name != null) newTag("name", withText(name))
                else Observable.empty()

}
