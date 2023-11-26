package main.main

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class UI(private var gamePanel: GamePanel) {

    lateinit var graphics2D: Graphics2D

    private var arial_40: Font

    init {

        this.gamePanel = gamePanel

        this.arial_40 = Font("アリアル", Font.PLAIN, 40)

    }

    fun uiDraw(graphics2D: Graphics2D) {

        this.graphics2D = graphics2D

        graphics2D.setFont(arial_40)

        graphics2D.color = Color.WHITE

        if (gamePanel.getGameState() == gamePanel.getPlayState()) {

        }
        if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            drawPauseScreen()
        }
        if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
            drawDialogueScreen()
        }
    }

    fun drawPauseScreen() {

        graphics2D.font = graphics2D.getFont().deriveFont(Font.PLAIN, 50f)

        val text = "PAUSED"
        val x: Int = getXforCenteredText(text)

        val y: Int = gamePanel.getScreenHeight() / 2


        graphics2D.drawString(text, x, y)
    }

    fun drawDialogueScreen() {

        val x: Int = gamePanel.getTileSize() * 2
        val y: Int = gamePanel.getTileSize() / 2
        val width: Int = gamePanel.getScreenWidth() - (gamePanel.getTileSize() * 4)
        val height: Int = gamePanel.getTileSize() * 5

        drawSubWindow(x, y, width, height)
    }

    fun drawSubWindow(x: Int, y: Int, width: Int, height: Int) {

        val c = Color(0, 0, 0)

        graphics2D.color = c

        graphics2D.fillRoundRect(x, y, width, height, 35, 35)

    }


    fun getXforCenteredText(text: String?): Int {
        val length: Int = graphics2D.fontMetrics.getStringBounds(text, graphics2D).width.toInt()
        return gamePanel.getScreenWidth() / 2 - length / 2
    }
}
