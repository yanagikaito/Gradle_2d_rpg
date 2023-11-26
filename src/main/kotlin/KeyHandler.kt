package main

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyHandler(private var gamePanel: GamePanel) : KeyListener {

    var upPressed = false
    var downPressed = false
    var leftPressed = false
    var rightPressed = false

    /**
     * デバッグ
     */

    var checkDrawTime = false

    var showDebugText = false

    init {

        this.gamePanel = gamePanel
    }

    override fun keyTyped(e: KeyEvent) {

    }

    /**
     * キーを押したとき
     */

    override fun keyPressed(e: KeyEvent) {

        val code = e.keyCode

        if (code == KeyEvent.VK_W) {
            upPressed = true
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true
        }
        if (code == KeyEvent.VK_P) {
            if (gamePanel.getGameState() == gamePanel.getPlayState()) {
                gamePanel.getGameState() == gamePanel.getPauseState()
            } else if (gamePanel.getGameState() == gamePanel.getPauseState()) {
                gamePanel.getGameState() == gamePanel.getPlayState()
            }
        }

        /**
         * デバッグ
         */

        if (code == KeyEvent.VK_T) {

            if (!showDebugText) {
                showDebugText = true

            } else if (showDebugText) {
                showDebugText = false

            }
        }
    }

    /**
     * キーを離した時
     */
    override fun keyReleased(e: KeyEvent) {

        val code = e.keyCode

        if (code == KeyEvent.VK_W) {
            upPressed = false
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false
        }
    }
}
