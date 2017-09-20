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

    override val writeOperations: Observable<XmlWrite>
        get() = if (hasMetadata()) newTag("metadata",
                optionalTagWithText("name", name),
                optionalTagWithText("desc", description),
                optionalTagWithText("author", author)
        ) else Observable.empty()

}
