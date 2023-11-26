package `object`

import java.io.IOException
import javax.imageio.ImageIO

class OBJ_Boots : SuperObject() {

    init {

        name = "Boots"

        try {

            image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/objects/Boot.png"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
