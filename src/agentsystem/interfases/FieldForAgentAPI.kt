package agentsystem.interfases

/**
 * Created by Koppa on 26.09.2017.
 */
interface FieldForAgentAPI {

    fun getFieldXY(x: Int, y: Int) = StaticFieldForAPI.grid.field.getXY(x,y)

    fun setFieldXY(x: Int, y: Int, value: String){
        StaticFieldForAPI.grid.field.setXY(x,y, value)
    }

    fun isItGold(x: Int, y: Int): Boolean{
        for (each in StaticFieldForAPI.grid.goldsPlaces){
            if ((x == each.x) and (y == each.y))
                if (each.quantity > 0)
                    return true
        }
        return false
    }

    fun isItTrack(x: Int, y: Int): Boolean{
        val value = getFieldXY(x,y)
        if (value == "*")
            return true
        return false
    }

    fun wantToTakeGold(x: Int, y: Int): Int{
        for (each in StaticFieldForAPI.grid.goldsPlaces){
            if ((x == each.x) and (y == each.y)){
                if (each.quantity > 0){
                    each.quantity = each.quantity - 1
                    return 1
                }
            }
        }
        return 0
    }
}