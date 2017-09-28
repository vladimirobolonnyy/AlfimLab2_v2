package agentsystem.objects

/**
 * Created by Koppa on 25.09.2017.
 */
data class Base (val x: Int, val y: Int) {
    var quantity = 0
    var name: String = "Base(${quantity})"

    fun updateName(){
        name = "Base(${quantity})"
    }
}