package main.main

import java.io.IOException
import java.net.URL
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class Sound {

    var clip: Clip? = null

    private var soundURL = arrayOfNulls<URL>(30)

    init {

        soundURL[0] = javaClass.getClassLoader().getResource("images/sound/field.wav")
        soundURL[1] = javaClass.getClassLoader().getResource("images/sound/item.wav")
        soundURL[2] = javaClass.getClassLoader().getResource("images/sound/powerup.wav")
        soundURL[3] = javaClass.getClassLoader().getResource("images/sound/unlock.wav")
        soundURL[4] = javaClass.getClassLoader().getResource("images/sound/fanfare.wav")
    }

    fun setFile(i: Int) {

        try {

            val audioInputStream = AudioSystem.getAudioInputStream(soundURL[i])

            clip = AudioSystem.getClip()

            clip?.open(audioInputStream)

        } catch (e: IOException) {

            e.printStackTrace()
        }
    }

    fun play() {

        clip?.start()
    }

    fun loop() {

        clip?.loop(Clip.LOOP_CONTINUOUSLY)
    }

    fun stop() {

        clip?.stop()
    }
}
