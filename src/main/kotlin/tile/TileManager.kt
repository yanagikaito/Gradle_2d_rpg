package tile

import main.GamePanel
import `object`.SuperObject
import java.awt.Graphics2D
import java.io.*
import javax.imageio.ImageIO

class TileManager(private var gamePanel: GamePanel) {

    private var tile: Array<Tile?>

    private var mapTileNum: Array<IntArray>

    init {
        this.gamePanel = gamePanel

        /**
         * 10種類のタイル
         *
         * arrayOfNullsはnullで初期化された配列
         */


        tile = arrayOfNulls(10)

        /**
         * mapTileNumの中に配列の数字がすべてある状態
         */

        mapTileNum = Array(gamePanel.getMaxWorldMapCol()) { IntArray(gamePanel.getMaxWorldMapRow()) }
        tileImage
        loadMap("images/maps/world01.txt")
    }

    private val tileImage: Unit
        get() {
            try {

                tile[0] = Tile(0)
                tile[0]!!.image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/sand.jpeg"))

                tile[1] = Tile(1)
                tile[1]!!.image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/wall.jpeg"))
                tile[1]?.collision = true

                tile[2] = Tile(2)
                tile[2]!!.image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/water.png"))
                tile[2]?.collision = true

                tile[3] = Tile(0)
                tile[3]!!.image =
                    ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/earth.jpeg"))

                tile[4] = Tile(1)
                tile[4]!!.image =
                    ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/grass.jpeg"))
                tile[4]?.collision = true

                tile[5] = Tile(2)
                tile[5]!!.image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/tiles/gray.png"))

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    private fun loadMap(filePath: String) {

        val string: InputStream = javaClass.getClassLoader().getResourceAsStream(filePath)

        val br = BufferedReader(InputStreamReader(string))

        var col = 0
        var row = 0

        while (col < gamePanel.getMaxWorldMapCol() && row < gamePanel.getMaxWorldMapRow()) {

            val line = br.readLine()
            while (col < gamePanel.getMaxWorldMapCol()) {

                val numbers = line.split(" ")

                val num = numbers[col].toInt()

                mapTileNum[col][row] = num
                col++
            }
            if (col == gamePanel.getMaxWorldMapCol()) {
                col = 0
                row++
            }
        }
        br.close()

    }

    fun draw(g2: Graphics2D) {

        var worldMapNumberCol = 0
        var worldMapNumberRow = 0


        while (worldMapNumberCol < gamePanel.getMaxWorldMapCol() && worldMapNumberRow < gamePanel.getMaxWorldMapRow()) {

            val tileNum = mapTileNum[worldMapNumberCol][worldMapNumberRow]

            val playerPositionWorldX = worldMapNumberCol * gamePanel.getTileSize()

            val playerPositionWorldY = worldMapNumberRow * gamePanel.getTileSize()

            val screenX =
                playerPositionWorldX - gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer()
                    .getScreenCenterX()

            val screenY =
                playerPositionWorldY - gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer()
                    .getScreenCenterY()

            if (playerPositionWorldX + gamePanel.getTileSize() >

                gamePanel.getPlayer().getStartPlayerPointX() - gamePanel.getPlayer().getScreenCenterX() &&

                playerPositionWorldX - gamePanel.getTileSize() <

                gamePanel.getPlayer().getStartPlayerPointX() + gamePanel.getPlayer().getScreenCenterX() &&

                playerPositionWorldY + gamePanel.getTileSize() >

                gamePanel.getPlayer().getStartPlayerPointY() - gamePanel.getPlayer().getScreenCenterY() &&

                playerPositionWorldY - gamePanel.getTileSize() <

                gamePanel.getPlayer().getStartPlayerPointY() + gamePanel.getPlayer().getScreenCenterY()
            ) {

                g2.drawImage(
                    tile[tileNum]?.image,
                    screenX,
                    screenY,
                    gamePanel.getTileSize(),
                    gamePanel.getTileSize(),
                    null
                )
            }

            worldMapNumberCol++


            if (worldMapNumberCol == gamePanel.getMaxWorldMapCol()) {
                worldMapNumberCol = 0
                worldMapNumberRow++

            }
        }
    }
    fun getMapTileNum() = mapTileNum
    fun getTile(): Array<Tile?> = tile
}