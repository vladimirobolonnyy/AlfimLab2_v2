package agentsystem.objects

/**
 * Created by Koppa on 25.09.2017.
 */
class Gold(var x: Int, var y: Int, quantity: Int) {

    var quantity = 0
    var coordinate: MyCoordinate

    init {
        this.quantity = quantity
        coordinate = MyCoordinate(x, y)
    }

    override fun toString(): String {
        return quantity.toString()
    }
}