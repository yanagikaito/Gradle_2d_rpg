package entity

import main.main.GamePanel
import main.main.KeyHandler
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

class Player(override var gamePanel: GamePanel, private var keyHandler: KeyHandler) : Entity(gamePanel) {


    /**
     *  screenXとscreenYは画面上のどこにプレイヤーを描画するかを示します。

     * プレイヤーのキャラクターを画面の中央に配置し,動きに合わせて背景をスクロールします。

     */

    private var screenCenterX = 0

    private var screenCenterY = 0

    init {

        (gamePanel)

        this.gamePanel = gamePanel

        this.keyHandler = keyHandler

        /**
         * screenCenterX 360 screenCenterY 264 座標
         */

        screenCenterX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2)
        screenCenterY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2)

        setPlayerSolidArea(Rectangle(8, 16, 32, 32))
        setSolidAreaDefaultX(getPlayerSolidArea().x)
        setSolidAreaDefaultY(getPlayerSolidArea().y)
        setDefaultValues()
        playerImage()
    }

    private fun setDefaultValues() {

        /**
         * プレーヤーのスタート地点になります。
         */

        setStartPlayerPointX(gamePanel.getTileSize() * 23)
        setStartPlayerPointY(gamePanel.getTileSize() * 21)
        setDirection("down")
        setPlayerSpeed(4)

    }

    /**
     * 関数やメソッドが何も返さない場合、その戻り値型として Unit を指定
     */

    private fun playerImage() {
            try {

                up1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-up-1.gif"))
                up2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-up-2.gif"))
                down1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-down-1.gif"))
                down2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-down-2.gif"))
                left1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-left-1.gif"))
                left2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-left-2.gif"))
                right1 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-right-1.gif"))
                right2 = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/player/image-right-2.gif"))

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    /**
     * updateメソッドは1秒間に60回呼ばれます
     *
     * ゲームループの中にあるので,spritCounterが1つ増えます。
     *
     */
    override fun update() {

        // spriteCounterはキーが押されているときだけ増えます。

        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed) {
                setDirection("up")

            } else if (keyHandler.downPressed) {
                setDirection("down")

            } else if (keyHandler.leftPressed) {
                setDirection("left")

            } else if (keyHandler.rightPressed) {
                setDirection("right")
            }

            setCollisionOn(false)

            /**
             * collisionCheckerはplayerクラスをentityとして使えます。
             */

            gamePanel.getCollisionChecker().playerCheckTile(this)

            val objectIndex = gamePanel.getCollisionChecker().checkObject(this, true)

            pickUpObject(objectIndex)

            val npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNPC())

            interact(npcIndex)


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

            /**
             * spriteCounterはキーが押されているときだけ増えます。
             *
             * 10フレームごとにプレイヤーの画像が変わります。
             */

            spritCounter++
            if (spritCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2
                } else if (spriteNum == 2) {
                    spriteNum = 1
                }
                spritCounter = 0
            }
        }
    }

    fun pickUpObject(i: Int) {

        if (i != 999) {

        }
    }

    fun interact(i: Int) {

        if (i != 999) {

            gamePanel.setGameState(gamePanel.getDialogueState())
        }
    }

    fun draw(graphics2D: Graphics2D) {

        var image: BufferedImage? = null

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
        graphics2D.drawImage(
            image,
            screenCenterX,
            screenCenterY,
            gamePanel.getTileSize(),
            gamePanel.getTileSize(),
            null
        )

        //   デバッグ
        graphics2D.color = Color.WHITE
        graphics2D.drawRect(screenCenterX, screenCenterY, gamePanel.getTileSize(), gamePanel.getTileSize())


        graphics2D.color = Color.red
        graphics2D.drawRect(
            screenCenterX + getPlayerSolidArea().x, screenCenterY +
                    getPlayerSolidArea().y, getPlayerSolidArea().width, getPlayerSolidArea().height
        )
    }

    fun getScreenCenterX() = screenCenterX

    fun getScreenCenterY() = screenCenterY
}



