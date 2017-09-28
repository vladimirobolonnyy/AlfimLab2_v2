package agentsystem.objects

/**
 * Created by Koppa on 25.09.2017.
 */

class MyField(val width: Int, val height: Int) {

    var field: Array<Array<String>>

    init {
        field = Array(width) { Array(height){""}}
    }

    fun setXY(x: Int, y: Int, value: String) {
        field[x][y] = value
    }

    fun setXY(coordinate: MyCoordinate, value: String) {
        field[coordinate.x][coordinate.y] = value
    }

    fun getXY(x: Int, y: Int) = field[x][y]

}