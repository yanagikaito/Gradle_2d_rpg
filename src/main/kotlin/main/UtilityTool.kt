package main.main

import java.awt.image.BufferedImage

class UtilityTool {

    fun scaleImage(original: BufferedImage?, width: Int, height: Int): BufferedImage {

        val scaledImage =
            BufferedImage(width, height, original!!.type)
        val graphics2D = scaledImage.createGraphics()
        graphics2D.drawImage(original, 0, 0, width, height, null)
        graphics2D.dispose()

        return scaledImage
    }
}
