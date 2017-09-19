package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable
import java.text.SimpleDateFormat
import java.util.*

/**
 * @param date: Timestamp in milliseconds since epoch
 */
data class Time(val date: Long?) : XmlWritable {

    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    override val writeOperations: Observable<XmlWrite>
        get() = if (date != null) newTag("time", withText(dateFormat.format(Date(date))))
                else Observable.empty()

}
