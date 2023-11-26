package entity

import main.GamePanel
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.random.Random

class NPC_OldMan(override var gamePanel: GamePanel) : Entity(gamePanel) {

    init {

        this.gamePanel = gamePanel

        setPlayerSpeed(2)

        npcPlayerImage()
    }

    private fun npcPlayerImage() {
            try {

                up1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_up_1.png"))
                up2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_up_2.png"))
                down1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_down_1.png"))
                down2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_down_2.png"))
                left1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_left_1.png"))
                left2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_left_2.png"))
                right1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_right_1.png"))
                right2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/npc/OldMan_right_2.png"))

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    override fun setAction() {

        val directionLock = 120

        actionLockCounter++

        if (actionLockCounter == directionLock) {

            val directionUp = 25

            val directionDown = 50

            val directionLeft = 75

            val directionRight = 100

            val i = Random.nextInt(100) + 1

            if (i < directionUp) {
                setDirection("up")
            }
            if (i in (directionUp + 1)..directionDown) {
                setDirection("down")
            }
            if (i in (directionDown + 1)..directionLeft) {
                setDirection("left")
            }
            if (i in (directionLeft + 1)..directionRight) {
                setDirection("right")
            }

            actionLockCounter = 0
        }
    }
}
