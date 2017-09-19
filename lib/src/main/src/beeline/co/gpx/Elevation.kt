package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Elevation(val elevation: Int?) : XmlWritable {

    override val writeOperations: Observable<XmlWrite>
        get() = if (elevation != null) newTag("ele", withText(elevation.toString()))
                else Observable.empty()

}
