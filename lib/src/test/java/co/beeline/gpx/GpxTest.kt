package co.beeline.gpx

import org.apache.tools.ant.filters.StringInputStream
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import org.xmlunit.validation.Languages
import org.xmlunit.validation.Validator
import rx.Observable
import java.io.File
import java.io.StringWriter
import javax.xml.transform.stream.StreamSource

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GpxTest {

    @Test fun empty() = assertGpxEquals(fixture("empty.gpx"), Gpx(creator = "empty"))

    @Test fun waypoints() = assertGpxEquals(fixture("waypoints.gpx"), Gpx(
            creator = "RouteConverter",
            metadata = Metadata(name = "Test file by Patrick"),
            waypoints = list(
                    Waypoint(54.9328621088893, 9.860624216140083, name = "Position 1", ele = 0.0),
                    Waypoint(54.93293237320851, 9.86092208681491, name = "Position 2", ele = 0.0),
                    Waypoint(54.93327743521187, 9.86187816543752, name = "Position 3", ele = 0.0),
                    Waypoint(54.93342326167919, 9.862439849679859, name = "Position 4", ele = 0.0)
            )
    ))

    @Test fun track() = assertGpxEquals(fixture("track.gpx"), Gpx(
            creator = "RouteConverter",
            metadata = Metadata(name = "Test file by Patrick"),
            tracks = list(Track(
                    name = "Patrick's Track",
                    segments = list(TrackSegment(list(
                            TrackPoint(54.9328621088893, 9.860624216140083, name = "Position 1", ele = 0.0, time = 1505900660000),
                            TrackPoint(54.93293237320851, 9.86092208681491, name = "Position 2", ele = 0.0, time = 1505900661000),
                            TrackPoint(54.93327743521187, 9.86187816543752, name = "Position 3", ele = 0.0, time = 1505900662000),
                            TrackPoint(54.93342326167919, 9.862439849679859, name = "Position 4", ele = 0.0, time = 1505900663000)
                    )))
            ))
    ))

    @Test fun route() = assertGpxEquals(fixture("route.gpx"), Gpx(
            creator = "RouteConverter",
            metadata = Metadata(name = "Test file by Patrick"),
            routes = list(Route(
                    name = "Patrick's Route",
                    points = list(
                            RoutePoint(54.9328621088893, 9.860624216140083, name = "Position 1", ele = 0.0),
                            RoutePoint(54.93293237320851, 9.86092208681491, name = "Position 2", ele = 0.0),
                            RoutePoint(54.93327743521187, 9.86187816543752, name = "Position 3", ele = 0.0),
                            RoutePoint(54.93342326167919, 9.862439849679859, name = "Position 4", ele = 0.0)
                    )
            ))
    ))

    private fun <T> list(vararg items: T): Observable<T> = Observable.from(listOf(*items))

    private fun fixture(filename: String): File = File("fixtures", filename)

    private fun Gpx.xmlString(): String =
        writeTo(StringWriter())
                .toBlocking()
                .value()
                .toString()

    private fun assertValidGpx(xml: String) {
        Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI)
                .apply { setSchemaSource(StreamSource(fixture("GPXv1.1.xsd"))) }
                .validateInstance(StreamSource(StringInputStream(xml)))
                .let { assertTrue(it.problems.joinToString("\n") + "\n---\n" + xml, it.isValid) }
    }

    private fun assertGpxEquals(expectedFile: File, actual: Gpx) {
        DiffBuilder.compare(Input.fromFile(expectedFile))
                .ignoreComments()
                .ignoreWhitespace()
                .withTest(Input.from(actual.xmlString()))
                .build()
                .let { assertFalse(it.toString(), it.hasDifferences()) }
        assertValidGpx(actual.xmlString())
    }

}
