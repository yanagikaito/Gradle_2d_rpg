package entity

import main.GamePanel
import java.awt.Color.red
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage

open class Entity(open var gamePanel: GamePanel) {

    /**
     * startPlayerPointXとstartPlayerPointY
     *
     * プレイヤーの初期値の位置になります。
     */

    private var startPlayerPointX = 0
    private var startPlayerPointY = 0
    private var speed = 0
    var actionLockCounter = 0

    var up1: BufferedImage? = null
    var up2: BufferedImage? = null
    var down1: BufferedImage? = null
    var down2: BufferedImage? = null
    var left1: BufferedImage? = null
    var left2: BufferedImage? = null
    var right1: BufferedImage? = null
    var right2: BufferedImage? = null
    private var direction = "down"

    var spritCounter = 0
    var spriteNum = 1
    private var playerSolidArea = Rectangle(0, 0, 48, 48)
    private var solidAreaDefaultX = 0
    private var solidAreaDefaultY = 0
    private var collisionOn = false

    open fun setAction() {


    }

    open fun update() {

        setAction()

        !getCollisionOn()

        gamePanel.getCollisionChecker().playerCheckTile(this)

        gamePanel.getCollisionChecker().checkObject(this, true)

        gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPC())

        gamePanel.getCollisionChecker().checkPlayer(this)

        if (!getCollisionOn()) {

            when (getDirection()) {
                "up" -> {

                    setStartPlayerPointY(getStartPlayerPointY() - getPlayerSpeed())

                }

                "down" -> {

                    setStartPlayerPointY(getStartPlayerPointY() + getPlayerSpeed())

                }

                "left" -> {

                    setStartPlayerPointX(getStartPlayerPointX() - getPlayerSpeed())

                }

                "right" -> {

                    setStartPlayerPointX(getStartPlayerPointX() + getPlayerSpeed())

                }
            }
        }
    }

    fun npcDraw(graphics2D: Graphics2D) {

        var image: BufferedImage? = null

        val screenX =
            startPlayerPointX - gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer().getScreenCenterX()

        val screenY =
            startPlayerPointY - gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer().getScreenCenterY()

        if (startPlayerPointX + gamePanel.getTileSize() >

            gamePanel.getPlayer().getStartPlayerPointX() - gamePanel.getPlayer().getScreenCenterX() &&

            startPlayerPointX - gamePanel.getTileSize() <

            gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer().getScreenCenterX() &&

            startPlayerPointY + gamePanel.getTileSize() >

            gamePanel.getPlayer().getStartPlayerPointY() - gamePanel.getPlayer().getScreenCenterY() &&

            startPlayerPointY - gamePanel.getTileSize() <

            gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer().getScreenCenterY()
        ) {

            when (getDirection()) {
                "up" -> {
                    if (spriteNum == 1) {
                        image = up1
                    }
                    if (spriteNum == 2) {
                        image = up2
                    }
                }

                "down" -> {
                    if (spriteNum == 1) {
                        image = down1
                    }
                    if (spriteNum == 2) {
                        image = down2
                    }
                }

                "left" -> {
                    if (spriteNum == 1) {
                        image = left1
                    }
                    if (spriteNum == 2) {
                        image = left2
                    }
                }

                "right" -> {
                    if (spriteNum == 1) {
                        image = right1
                    }
                    if (spriteNum == 2) {
                        image = right2
                    }
                }
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null)
        }

        graphics2D.color = red
        graphics2D.drawRect(
            screenX + playerSolidArea.x, screenY +
                    playerSolidArea.y, playerSolidArea.width, playerSolidArea.height
        )
    }

    fun getStartPlayerPointX() = startPlayerPointX
    fun getStartPlayerPointY() = startPlayerPointY
    fun getPlayerSolidArea() = playerSolidArea
    fun setPlayerSolidArea(playerSolidArea: Rectangle) {

        this.playerSolidArea = playerSolidArea
    }

    fun setStartPlayerPointX(startPlayerPointX: Int) {

        this.startPlayerPointX = startPlayerPointX
    }

    fun setStartPlayerPointY(startPlayerPointY: Int) {

        this.startPlayerPointY = startPlayerPointY
    }

    fun getDirection() = direction

    fun setDirection(direction: String) {

        this.direction = direction
    }

    fun getPlayerSpeed() = speed

    // setPlayerSpeedをthis.speed = speedで参照するとプレイヤーNPCが動くようになりました
    fun setPlayerSpeed(speed: Int) {

        this.speed = speed
    }

    fun getSolidAreaDefaultX() = solidAreaDefaultX

    fun getSolidAreaDefaultY() = solidAreaDefaultY

    fun setSolidAreaDefaultX(solidAreaDefaultX: Int) {

        this.solidAreaDefaultX = solidAreaDefaultX
    }

    fun setSolidAreaDefaultY(solidAreaDefaultY: Int) {

        this.solidAreaDefaultY = solidAreaDefaultY
    }

    fun getCollisionOn() = collisionOn
    fun setCollisionOn(collisionOn: Boolean) {

        this.collisionOn = collisionOn
    }
}
