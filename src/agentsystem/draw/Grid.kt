package agentsystem.draw

import agentsystem.agents.MyStupidAgent
import agentsystem.interfases.StaticFieldForAPI
import agentsystem.objects.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.util.*
import javax.swing.JButton
import javax.swing.JFrame

/**
 * Created by Koppa on 25.09.2017.
 */
class Grid(val maxX: Int, val maxY: Int, val sleepTime: Int = 100){

    val MAXTIME = 100000
    val agents: ArrayList<MyStupidAgent> = ArrayList()
    val goldsPlaces: ArrayList<Gold> = ArrayList()

    lateinit var base: Base
    var time = 0
    var endOfTheGame: Boolean = false
    var field: MyField  = MyField(maxX, maxY)
    var frame = JFrame()
    var grid: Array<Array<JButton>>
    var MAXBASEQUENTITY = 0

    init {

        StaticFieldForAPI.grid = this

        frame.layout = GridLayout(maxX, maxY)
        frame.minimumSize = Dimension(500, 500)
        grid = Array(maxX) { Array(maxY){JButton()} }
        for (y in 0..maxY - 1) {
            for (x in 0..maxX - 1) {
                val button = JButton(field.getXY(x, y))
                button.setFont(Font("Verdana", Font.PLAIN, 9));
                grid[x][y] = button
                frame.add(grid[x][y])
            }
        }
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack() //устанавливает соответствующий размер фрейма
        frame.isVisible = true //делает фрейм видимым
    }

    fun createBase(x: Int, y: Int) {
        base = Base(x,y)
        field.setXY(base.x, base.y, base.name)
    }

    fun createStupidAgents(agentsNumber: Int) {
        val names = "ABCDEFJKLMPONRST"
        for (i in 0..agentsNumber - 1) {
            agents.add(MyStupidAgent(names[i] + "", maxX, maxY, base))
        }
    }

    fun createRandomResourses(resoursesNumber: Int) {
        for (i in 0..resoursesNumber - 1) {
            val x = Random().nextInt(maxX)
            val y = Random().nextInt(maxY)
            goldsPlaces.add(Gold(x, y, 105))
        }

        for (each in goldsPlaces){
            field.setXY(each.x, each.y, each.toString())
            MAXBASEQUENTITY += each.quantity
        }
    }

    fun startTheGame() {
        while ((!endOfTheGame) && time < MAXTIME) {

            drawAll()

            for (eachAgent in agents) {
                eachAgent.makeYourStep()
            }

            time++
            Thread.sleep(sleepTime.toLong())

            if (MAXBASEQUENTITY == base.quantity)
                endOfTheGame = true
        }
    }

    private fun drawAll() {
        drawAllField()
        drawBase()
        drawAllResourses()
        drawAllAgents()
    }

    private fun drawAllField() {
        for (y in 0 until maxX)
            for (x in 0 until maxY)
                grid[x][y].text = field.getXY(x,y)
    }

    private fun drawBase() {
        base.updateName()
        grid[base.x][base.y].text = base.name
        grid[base.x][base.y].background = Color.WHITE
    }

    private fun drawAllResourses() {
        for (each in goldsPlaces){
            grid[each.x][each.y].background = Color.ORANGE
            grid[each.x][each.y].text = "${each.quantity}"
        }
    }

    private fun drawAllAgents() {
        for (each in agents){
            grid[each.x][each.y].text = each.toString()
            grid[each.x][each.y].background = Color.WHITE
        }
    }
}