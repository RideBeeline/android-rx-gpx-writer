package co.beeline.gpx

import co.beeline.gpx.xml.XmlWritable
import co.beeline.gpx.xml.XmlWrite
import io.reactivex.Observable

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
