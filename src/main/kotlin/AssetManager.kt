package main

import entity.NPC_OldMan

class AssetManager(private var gamePanel: GamePanel) {

    private var tileSize: Int

    init {

        this.gamePanel = gamePanel
        this.tileSize = gamePanel.getTileSize()

    }

    fun setObjects() {

    }

    fun setNPC() {

        gamePanel.getNPC()[0] = NPC_OldMan(gamePanel)

        gamePanel.getNPC()[0]!!.setStartPlayerPointX(gamePanel.getTileSize() * 21)

        gamePanel.getNPC()[0]!!.setStartPlayerPointY(gamePanel.getTileSize() * 23)
    }
}