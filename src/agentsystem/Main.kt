package agentsystem

import agentsystem.draw.Grid

/**
 * Created by Koppa on 25.09.2017.
 */
object Main {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        showGUI()
    }

    fun showGUI() {
        val bd = Grid(15, 15)
        bd.createBase(0,0)
        bd.createStupidAgents(5)
        bd.createRandomResourses(3)
        bd.startTheGame()
        println("Finish")
    }
}