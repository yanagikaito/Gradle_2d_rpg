package `object`

import java.io.IOException
import javax.imageio.ImageIO


class OBJ_Key: SuperObject() {

    init {

        name = "Key"

        try {

            image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/objects/Key.png"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

