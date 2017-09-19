package beeline.co.gpx

import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import rx.Observable
import java.io.File
import java.io.StringWriter

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GpxTest {

    @Test
    fun emptyGpx() = assertGpxEquals(fixture("empty.gpx"), Gpx())

    @Test
    fun waypointsWithTrack() = assertGpxEquals(fixture("waypointsWithTrack.gpx"), Gpx(
            name = "Example gpx",
            waypoints = list(
                    Waypoint(46.57638889, 8.89263889, name = "LAGORETICO", ele = 2372)
            ),
            tracks = list(
                    Track(name = "Example gpx", number = 1, segments = list(
                            TrackSegment(list(
                                    TrackPoint(46.57608333, 8.89241667, time = 1192356597000, ele = 2376),
                                    TrackPoint(46.57619444, 8.89252778, time = 1192356652000, ele = 2375),
                                    TrackPoint(46.57641667, 8.89266667, time = 1192356759000, ele = 2372),
                                    TrackPoint(46.57650000, 8.89280556, time = 1192356792000, ele = 2373),
                                    TrackPoint(46.57638889, 8.89302778, time = 1192356800000, ele = 2374),
                                    TrackPoint(46.57652778, 8.89322222, time = 1192356828000, ele = 2375),
                                    TrackPoint(46.57661111, 8.89344444, time = 1192356848000, ele = 2376)
                            ))
                    ))
            )
    ))

    private fun <T> list(vararg items: T): Observable<T> = Observable.from(listOf(*items))

    private fun fixture(filename: String): File = File("lib/src/test/fixtures", filename)

    private fun Gpx.xmlString(): String =
        writeTo(StringWriter())
                .toBlocking()
                .value()
                .toString()

    private fun assertGpxEquals(expectedFile: File, actual: Gpx) {
        DiffBuilder.compare(Input.fromFile(expectedFile))
                .ignoreComments()
                .ignoreWhitespace()
                .withTest(Input.from(actual.xmlString()))
                .build()
                .let { assertFalse(it.toString(), it.hasDifferences()) }
    }

}
