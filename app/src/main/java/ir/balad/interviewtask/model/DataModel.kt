package ir.balad.interviewtask.model

import android.graphics.Color
import kotlin.random.Random

data class DataModel(val title: String, val color: Int) {

    companion object {

        /** Generates a mocked list with default title and a random color. */
        fun generateMockList(): List<DataModel> {
            val list = mutableListOf<DataModel>()

            repeat(12) {
                list.add(
                    DataModel(
                        "Title",
                        Color.argb(
                            255,
                            Random.nextInt(256),
                            Random.nextInt(256),
                            Random.nextInt(256)
                        )
                    )
                )
            }

            return list
        }
    }
}
