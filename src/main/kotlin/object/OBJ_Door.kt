package `object`

import java.io.IOException
import javax.imageio.ImageIO

class OBJ_Door : SuperObject() {

    init {

        name = "Door"


        try {

            image = ImageIO.read(javaClass.getClassLoader().getResourceAsStream("images/objects/door.png"))

        } catch (e: IOException) {
            e.printStackTrace()
        }
        setCollision(true)
    }
}