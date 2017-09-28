package agentsystem.agents

import agentsystem.objects.Base
import agentsystem.interfases.FieldForAgentAPI
import agentsystem.objects.MyCoordinate
import agentsystem.objects.Actions
import java.util.*

/**
 * Created by Koppa on 25.09.2017.
 */
class MyStupidAgent(val name: String, val maxX: Int, val maxY: Int, val myBase: Base): FieldForAgentAPI  {

    val maxCapacity = 10
    val minX = 0
    val minY = 0

    var nowHaveResourse = 0
    var x = 0
    var y = 0
    var action = Actions.SEARCH
    var home: MyCoordinate = MyCoordinate(0, 0)

    init {
        home = MyCoordinate(myBase.x, myBase.y)
        x = myBase.x
        y = myBase.y
    }

    override fun toString(): String {
        return ("${name}(${nowHaveResourse})")
    }

    fun makeYourStep() {
        when (action){
            Actions.SEARCH -> chooseStep();
            Actions.GOHOME -> goHome()
            Actions.GOHOMEANDHIDE -> goHome(true)
            Actions.TAKEGOLD -> takeGold()
        }
    }

    private fun chooseStep() {
        if (isItGold(x,y)){
            action = Actions.TAKEGOLD
        }else if (isItTrack(x,y)){
            goOnTheTrack()
        } else {
            chooseRandomStep()
        }
    }

    private fun goOnTheTrack() {
        for (i in -1..1){
            for (j in -1..1){
                val newX = x + i
                val newY = y + j
                if (notOutOfBouds(newX, newY)){
                    if (isItGold(newX, newY)){
                        x = newX
                        y = newY
                        return
                    }
                }
            }
        }
        // золота не нашли, но есть дорога. Идём дальше.

        val mass = ArrayList<MyCoordinate>()

        for (i in -1..1){
            for (j in -1..1){
                val newX = x + i
                val newY = y + j
                if (notOutOfBouds(newX, newY)){
                    if (isItTrack(newX, newY)){
                        mass.add(MyCoordinate(newX, newY))
                    }
                }
            }
        }

        if (mass.size >= 2){
            var myLongestDist: MyCoordinate = MyCoordinate(0,0)
            for (each in mass){
                if (getDistanceFromBase(myLongestDist) <= getDistanceFromBase(each)){
                    myLongestDist = each
                }
            }
            x = myLongestDist.x
            y = myLongestDist.y
            return
        }
        if (mass.size == 1){
            action = Actions.GOHOMEANDHIDE
            return
        }

        chooseRandomStep()
    }

    fun takeGold(){
        if ((nowHaveResourse == maxCapacity)) {
            action = Actions.GOHOME
        }else if (!isItGold(x,y)){
          action = Actions.GOHOMEANDHIDE
        } else {
            nowHaveResourse += wantToTakeGold(x,y)
        }
    }

    private fun chooseRandomStep(): MyCoordinate {
        var resultX = -1
        var resultY = -1

        do {
            resultX = x + (Random().nextInt(3) - 1)
            resultY = y + (Random().nextInt(3) - 1)
            if (notOutOfBouds(resultX, resultY))
                break
        } while (true)

        x = resultX
        y = resultY

        return MyCoordinate(x, y)
    }


    private fun goHome(hide: Boolean = false): MyCoordinate {

        leaveTrack(hide)

        when{
            x < myBase.x -> x += 1
            x > myBase.x -> x -= 1
            x == myBase.x -> {}
            else -> {}
        }

        when{
            y < myBase.y -> y += 1
            y > myBase.y -> y -= 1
            y == myBase.y -> {}
            else -> {}
        }

        if ((x == myBase.x) and (y == myBase.y)){
            myBase.quantity += nowHaveResourse
            nowHaveResourse = 0
            action = Actions.SEARCH
        }

        return MyCoordinate(x, y)
    }

    private fun leaveTrack(hide: Boolean) {
        if (!hide)
            setFieldXY(x,y, "*")
        else
            setFieldXY(x,y, "")
    }

    private fun notOutOfBouds(x: Int, y: Int): Boolean {
        var good = false
        var veryGood = false
        if (x >= minX && x < maxX)
            good = true
        if (y >= minY && y < maxY)
            veryGood = true
        return (good and veryGood)
    }

    private fun getDistanceFromBase(x: Int, y: Int): Double{
        return Math.sqrt((x-myBase.x) * (x-myBase.x) + (y-myBase.y)*(y-myBase.y).toDouble())
    }

    private fun getDistanceFromBase(coords: MyCoordinate): Double{
        return getDistanceFromBase(coords.x, coords.y)
    }
}