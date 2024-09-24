package com.example.findit.data

import android.location.Location
import com.example.findit.R


/*
Kevin Chou
Oregon State University CS 492
2024
 */

data class Clue(
    val hint: String = "", val hint2: String = "", val info: String = "", val lat: Double = 0.0, val lon: Double = 0.0
)


object DataSource {
    val clues = mapOf(
        1 to Clue(
            hint = "A large marble arch in the shape of a rectangle.",
            hint2 = "Located in a park with a water feature.",
            info = "The Washington Square Arch was designed in 1891 and commemorates the centennial of George Washington's inauguration as president.",
            lat = 40.731234977131166,
            lon = -73.99709525242635
        ),
        2 to Clue(
            hint = "A cube that spins round and round.",
            hint2 = "Located in a small plaza.",
            info = "The Astor Place cube sculpture, officially named the Alamo, weighs about 1,800 lbs. It was intended to be a temporary art installation until local residents asked to keep it permanently.",
            lat = 40.72988548971249,
            lon = -73.99102545777133
        )
    )
}
