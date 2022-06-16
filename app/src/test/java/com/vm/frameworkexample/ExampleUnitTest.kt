package com.vm.frameworkexample

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import kotlin.math.roundToInt
import kotlin.math.roundToLong

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun whenStubASpy_thenStubbed() {
        val list: List<String> = ArrayList()
        val spyList = Mockito.spy(list)
        assertEquals(0, spyList.size)
        Mockito.doReturn(100).`when`(spyList).size
        assertEquals(100, spyList.size)
    }


    @Test
    fun ff() {

        val totalJourneys = 3
        val minJourneysOnScreen = 3
        val completedJourney = 1

        val heightMeasureSpec = 10f
        val finalHeight = (heightMeasureSpec / minJourneysOnScreen) * totalJourneys

        val canvasHeight = finalHeight.coerceAtLeast(heightMeasureSpec)
        val canvasWidth = 6f


        val fraction = totalJourneys * 2
        val ogHeight =
            (canvasHeight / (totalJourneys.toFloat() / minJourneysOnScreen.toFloat())).coerceAtMost(
                canvasHeight
            )
        val yChunk = (ogHeight / (minJourneysOnScreen * 2))
        val centerX = canvasWidth / 2f

        println("Width=$canvasWidth CanvasHeight=$canvasHeight OgHeight=$ogHeight ChunkSize=$yChunk JourneySize=${yChunk * 2} fraction=$fraction")
        //starting from bottom direction
        var remH = canvasHeight// - yChunk

        println()
        var xIndex = 0
        val xPoints = floatArrayOf(centerX, 0f, centerX, canvasWidth)
        for (i in 0..totalJourneys*2) {
            val x = xPoints[xIndex]
            val point = floatArrayOf(x, remH)
            println("i=$i x=$x y=$remH")
//            println(point.joinToString { it.toString() })
            remH = (remH - yChunk).coerceAtLeast(0f)
            xIndex = if (xIndex + 1 > 3) {
                0
            } else {
                xIndex + 1
            }
        }
    }
}
