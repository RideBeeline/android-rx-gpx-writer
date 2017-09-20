package beeline.co.gpx

import beeline.co.gpx.xml.XmlWritable
import beeline.co.gpx.xml.XmlWrite
import rx.Observable

data class Metadata(
        val name: String? = null,
        val description: String? = null,
        val author: String? = null
) : XmlWritable {

    private fun hasMetadata(): Boolean =
            name != null || description != null || author != null

    private fun optionalTag(tag: String, value: String?) =
            if (value != null) newTag(tag, withText(value))
            else Observable.empty()

    override val writeOperations: Observable<XmlWrite>
        get() = if (hasMetadata()) newTag("metadata",
                optionalTag("name", name),
                optionalTag("desc", description),
                optionalTag("author", author)
        ) else Observable.empty()

}
