# RxGPXWriter for Android

[![Release](https://jitpack.io/v/RideBeeline/android-rx-gpx-writer.svg)]
(https://jitpack.io/#RideBeeline/android-rx-gpx-writer)

Create GPX v1.1 files from streams of data on Android.

## Installation

Add JitPack your root `build.gradle` at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```gradle
	dependencies {
	        compile 'com.github.RideBeeline:android-rx-gpx-writer:TAG'
	}
```

## Usage

```kotlin
val gpx = Gpx(
    creator = "RideBeeline",
    metadata = Metadata(name = "Example"),
    routes = Observable.from(listOf(Route(
            points = Observable.from(listOf(
                    RoutePoint(54.9328621088893, 9.860624216140083, name = "Position 1", ele = 0.0),
                    RoutePoint(54.93293237320851, 9.86092208681491, name = "Position 2", ele = 0.0),
                    RoutePoint(54.93327743521187, 9.86187816543752, name = "Position 3", ele = 0.0),
                    RoutePoint(54.93342326167919, 9.862439849679859, name = "Position 4", ele = 0.0)
            ))
    )))
)
val writer: Single<Writer> = gpx.writeTo(writer)
writer.subscribe {
  // write complete
}
```

You can also include `tracks` and `waypoints`.
