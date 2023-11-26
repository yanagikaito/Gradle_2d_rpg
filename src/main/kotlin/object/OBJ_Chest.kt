package `object`

import java.io.IOException
import javax.imageio.ImageIO

class OBJ_Chest : SuperObject() {

    init {

        name = "Chest"

        try {

            image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/objects/chest.jpeg"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}