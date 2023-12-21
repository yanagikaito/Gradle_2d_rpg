package main.main

import entity.Entity
import entity.Player
import `object`.SuperObject
import tile.TileManager
import java.awt.*
import javax.swing.JPanel

/**
 * クラス定義と同時に、クラス名に続けて書くのが基本。このようなコンストラクタをプライマリコンストラクタと呼びます
 */
class GamePanel internal constructor() : JPanel(), Runnable {

    /**
     * スクリーンの大きさの設定
     */

    private val originalTileSize = 16

    private val scale = 3

    /**
     * tileSizeは1枚のタイルの大きさ48
     */

    private var tileSize = originalTileSize * scale

    private val maxScreenCol = 16

    private val maxScreenRow = 12

    /**
     * 768 ピクセル
     */

    private val screenWidth = tileSize * maxScreenCol

    /**
     * 576 ピクセル
     */

    private val screenHeight = tileSize * maxScreenRow

    /**
     * Col 列　Row　行
     */

    private val maxWorldMapCol = 50

    private val maxWorldMapRow = 50

    /**
     * KeyHandlerをインスタンス生成
     */

    private val keyHandler = KeyHandler(this)

    private val sound = Sound()

    private val tileManager = TileManager(this)

    private val collisionChecker = CollisionChecker(this)

    private val assetSetter = AssetManager(this)

    private val uiTool = UI(this)

    private val player = Player(this, keyHandler)

    private val objects: Array<SuperObject> = Array(10) { SuperObject() }

    private val npc: Array<Entity?> = Array(10) { Entity(this) }

    private val fps = 60

    private var gameThread: Thread? = null

    private var gameState = 0

    private var playState = 1

    private var pauseState = 2

    private var dialogueState = 3

    /**
     * イニットプロセス
     *
     * 最初に動き出して、その他のプロセスたちを起動する役目を果たす
     */
    init {

        this.preferredSize = Dimension(screenWidth, screenHeight)
        setBackground(Color.BLACK)
        this.isDoubleBuffered = true
        addKeyListener(keyHandler)
        setFocusable(true)

    }

    fun setupGame() {

        assetSetter.setObjects()

        assetSetter.setNPC()

        playMusic(0)

        gameState = playState

    }

    override fun run() {

        /**
         * デルタまたは累積型方式
         *
         * 0.016秒ごとに画面の描画を行います。
         */

        val drawInterval = (1000000000 / fps).toDouble()
        var delta = 0.0
        var lastTime = System.nanoTime()
        var currentTime: Long

        /**
         * アップデートと描画が実行されるたびに,このdrawCountに1を追加します。
         */

        var timer: Long = 0
        var drawCount = 0

        while (gameThread != null) {

            /**
             * 1ループごとに
             *
             * updateとrepaintを実行し,
             *
             * 経過時間をtimerに追加していきます。
             */

            currentTime = System.nanoTime()
            delta += (currentTime - lastTime) / drawInterval
            timer += (currentTime - lastTime)
            lastTime = currentTime
            if (delta >= 1) {
                update()
                repaint()
                delta--
                drawCount++
            }

            if (timer >= 1000000000) {
                println("FPS:$drawCount")
                println("処理に要した時間 = $timer")
                println(timer / 1000000000)
                println("秒")
                // drawCountとtimerをリセットします。
                drawCount = 0
                timer = 0
            }
        }
    }

    private fun update() {

        if (gameState == playState) {
            player.update()

            for (i in npc.indices) {
                if (npc[i] != null) {
                    npc[i]?.update()
                }
            }
        }
        if (gameState == pauseState) {

        }
    }

    override fun paintComponent(graphics: Graphics) {

        super.paintComponent(graphics)

        /**
         * GraphicsをGraphics2Dに変換しています。
         */

        val graphics2D = graphics as Graphics2D

        /**
         * デバッグ
         */

        val drawStart = 0
        if (keyHandler.checkDrawTime) {

            var drawStart: Long = System.nanoTime()
            if (keyHandler.showDebugText) {
                drawStart = System.nanoTime()
            }

        }


        tileManager.draw(graphics2D)


        // OBJECT
        for (i in objects.indices) {
            if (objects[i] != null) {
                objects[i].objectDraw(graphics2D, this)
            }
        }

        for (i in npc.indices) {
            if (npc[i] != null) {
                npc[i]?.npcDraw(graphics2D)
            }
        }


        player.draw(graphics2D)

        uiTool.uiDraw(graphics2D)

        if (keyHandler.showDebugText) {

            val drawEnd: Long = System.nanoTime()

            val passed: Long = drawEnd - drawStart

            graphics2D.font = Font("アリアル", Font.PLAIN, 20)

            graphics2D.color = Color.WHITE

            val x = 10

            var y = 400

            val lineHeight = 20

            graphics2D.drawString("WorldX" + player.getStartPlayerPointX(), x, y)

            y += lineHeight

            graphics2D.drawString("WorldY" + player.getStartPlayerPointY(), x, y)

            y += lineHeight

            graphics2D.drawString(
                "Col" + (player.getStartPlayerPointX() +
                        player.getPlayerSolidArea().x) / getTileSize(), x, y
            )

            y += lineHeight

            graphics2D.drawString(
                "Row" + (player.getStartPlayerPointY() +
                        player.getPlayerSolidArea().y) / getTileSize(), x, y
            )

            y += lineHeight

            graphics2D.drawString("Draw Time: " + passed, x, y)

            println("Draw Time: " + passed / 1000000000)

            /**
             * dispose
             *
             * リソースを節約するため
             */

            graphics2D.dispose()

        }
    }

    fun playMusic(i: Int) {

        sound.setFile(i)

        sound.play()

        sound.loop()
    }

    fun stoMusic() {

        sound.stop()
    }

    fun playSE(i: Int) {

        sound.setFile(i)

        sound.play()
    }

    fun startGameThread() {

        gameThread = Thread(this)

        gameThread?.start()
    }

    fun getTileSize() = tileSize
    fun getScreenWidth() = screenWidth
    fun getScreenHeight() = screenHeight
    fun getMaxWorldMapCol() = maxWorldMapCol
    fun getMaxWorldMapRow() = maxWorldMapRow
    fun getPlayer() = player
    fun getCollisionChecker() = collisionChecker
    fun getTileManager() = tileManager
    fun getObjects() = objects
    fun getGameState() = gameState

    fun setGameState(gameState: Int) {

        this.gameState = gameState
    }

    fun getPlayState() = playState
    fun getPauseState() = pauseState
    fun getNPC() = npc
    fun getDialogueState() = dialogueState
}
