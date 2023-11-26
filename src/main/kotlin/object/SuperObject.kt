package `object`

import main.main.GamePanel
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage


open class SuperObject {

    lateinit var image: BufferedImage
    lateinit var name: String
    private var collision: Boolean = false
    private var objectWorldX = 0
    private var objectWorldY = 0
    private var objectSolidArea = Rectangle(0, 0, 48, 48)
    private var objectSolidAreaDefaultX = 0
    private var objectSolidAreaDefaultY = 0

    fun objectDraw(graphics2D: Graphics2D, gamePanel: GamePanel) {

        val screenX =
            objectWorldX - gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer().getScreenCenterX()

        val screenY =
            objectWorldY - gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer().getScreenCenterY()

        if (objectWorldX + gamePanel.getTileSize() >

            gamePanel.getPlayer().getStartPlayerPointX() - gamePanel.getPlayer().getScreenCenterX() &&

            objectWorldX - gamePanel.getTileSize() <

            gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer().getScreenCenterX() &&

            objectWorldY + gamePanel.getTileSize() >

            gamePanel.getPlayer().getStartPlayerPointY() - gamePanel.getPlayer().getScreenCenterY() &&

            objectWorldY - gamePanel.getTileSize() <

            gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer().getScreenCenterY()
        ) {

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null)
        }

    }

    fun getObjectSolidArea() = objectSolidArea

    fun setObjectSolidArea(objectSolidArea: Rectangle) {

        this.objectSolidArea = objectSolidArea
    }

    fun getObjectWorldX() = objectWorldX
    fun getObjectSolidAreaDefaultX() = objectSolidAreaDefaultX
    fun getObjectSolidAreaDefaultY() = objectSolidAreaDefaultY

    fun setObjectWorldX(objectWorldX: Int) {

        this.objectWorldX = objectWorldX
    }

    fun setObjectWorldY(objectWorldY: Int) {

        this.objectWorldY = objectWorldY
    }

    fun getObjectWorldY() = objectWorldY
    fun getCollision() = collision
    fun setCollision(collision: Boolean) {

        this.collision = collision
    }
}
