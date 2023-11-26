package main.main

import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val window = JFrame()

        /**
         * ウィンドウを閉じることができます。
         */

        window.setDefaultCloseOperation(EXIT_ON_CLOSE)

        /**
         * ウィンドウサイズが固定されます。
         */

        window.setResizable(false)

        /**
         * ゲームタイトル
         */

        window.setTitle("2D ARPG")

        val gamePanel = GamePanel()
        window.add(gamePanel)
        window.pack()

        /**
         * ウィンドウがスクリーンの中央に固定されます。
         */

        window.setLocationRelativeTo(null)

        /**
         * ウィンドウが出現します。
         */

        window.isVisible = true

        /**
         * ゲームスレッドを開始する前に呼び出す
         */

        gamePanel.setupGame()

        gamePanel.startGameThread()
    }
}