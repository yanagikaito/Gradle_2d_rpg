package main

import entity.Entity

class CollisionChecker(private var gamePanel: GamePanel) {

    init {

        this.gamePanel = gamePanel

    }


    fun playerCheckTile(entity: Entity) {

        val playerTileLeftWorldX = entity.getStartPlayerPointX() + entity.getPlayerSolidArea().x

        val playerTileRightWorldX =
            entity.getStartPlayerPointX() + entity.getPlayerSolidArea().x + entity.getPlayerSolidArea().width

        val playerTileTopWorldY = entity.getStartPlayerPointY() + entity.getPlayerSolidArea().y

        val playerTileBottomWorldY = entity.getStartPlayerPointY() +
                entity.getPlayerSolidArea().y + entity.getPlayerSolidArea().height

        var entityLeftCol = playerTileLeftWorldX / gamePanel.getTileSize()

        var entityRightCol = playerTileRightWorldX / gamePanel.getTileSize()

        var entityToPRow = playerTileTopWorldY / gamePanel.getTileSize()

        var entityBottomRow = playerTileBottomWorldY / gamePanel.getTileSize()

        val tileLeft: Int

        val tileRight: Int

        when (entity.getDirection()) {

            "up" -> {

                entityToPRow = (playerTileTopWorldY - entity.getPlayerSpeed()) / gamePanel.getTileSize()

                tileLeft = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityToPRow]

                tileRight = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityToPRow]

                if (gamePanel.getTileManager().getTile()[tileLeft]!!.collision ||
                    gamePanel.getTileManager().getTile()[tileRight]!!.collision
                ) {
                    entity.setCollisionOn(true)
                }
            }


            "down" -> {

                entityBottomRow = (playerTileBottomWorldY + entity.getPlayerSpeed()) / gamePanel.getTileSize()
                tileLeft = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow]
                tileRight = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow]

                if (gamePanel.getTileManager().getTile()[tileLeft]!!.collision ||
                    gamePanel.getTileManager().getTile()[tileRight]!!.collision
                ) {
                    entity.setCollisionOn(true)
                }
            }

            "left" -> {

                entityLeftCol = (playerTileLeftWorldX - entity.getPlayerSpeed()) / gamePanel.getTileSize()
                tileLeft = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityToPRow]
                tileRight = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow]

                if (gamePanel.getTileManager().getTile()[tileLeft]!!.collision ||
                    gamePanel.getTileManager().getTile()[tileRight]!!.collision
                ) {
                    entity.setCollisionOn(true)
                }

            }

            "right" -> {

                entityRightCol = (playerTileRightWorldX + entity.getPlayerSpeed()) / gamePanel.getTileSize()
                tileLeft = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityToPRow]
                tileRight = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow]

                if (gamePanel.getTileManager().getTile()[tileLeft]!!.collision ||
                    gamePanel.getTileManager().getTile()[tileRight]!!.collision
                ) {
                    entity.setCollisionOn(true)
                }
            }
        }
    }

    fun checkObject(entity: Entity, player: Boolean): Int {

        var index = 999

        val objects = gamePanel.getObjects()

        for (i in objects.indices) {

            if (objects[i] != null) {

                entity.getPlayerSolidArea().x += entity.getSolidAreaDefaultX()
                entity.getPlayerSolidArea().y += entity.getSolidAreaDefaultY()

                objects[i].getObjectSolidArea().x += objects[i].getObjectWorldX()

                objects[i].getObjectSolidArea().y += objects[i].getObjectWorldY()

                when (entity.getDirection()) {

                    "up" -> {

                        entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
                        if (entity.getPlayerSolidArea()
                                .intersects(objects[i].getObjectSolidArea())

                        ) {

                            if (objects[i].getCollision()) {
                                entity.setCollisionOn(true)
                            }

                            if (player) {
                                index = i
                            }
                        }
                    }

                    "down" -> {

                        entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
                        if (entity.getPlayerSolidArea()
                                .intersects(objects[i].getObjectSolidArea())
                        ) {

                            if (objects[i].getCollision()) {
                                entity.setCollisionOn(true)
                            }

                            if (player) {
                                index = i
                            }
                        }
                    }

                    "left" -> {
                        entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
                        if (entity.getPlayerSolidArea()
                                .intersects(objects[i].getObjectSolidArea())
                        ) {

                            if (objects[i].getCollision()) {
                                entity.setCollisionOn(true)
                            }

                            if (player) {
                                index = i
                            }
                        }
                    }

                    "right" -> {
                        entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
                        if (entity.getPlayerSolidArea()
                                .intersects(objects[i].getObjectSolidArea())
                        ) {

                            if (objects[i].getCollision()) {
                                entity.setCollisionOn(true)
                            }

                            if (player) {
                                index = i
                            }
                        }

                        entity.getPlayerSolidArea().x = entity.getSolidAreaDefaultX()
                        entity.getPlayerSolidArea().y = entity.getSolidAreaDefaultY()
                        objects[i].getObjectSolidArea().x =
                            objects[i].getObjectSolidAreaDefaultX()
                        objects[i].getObjectSolidArea().y =
                            objects[i].getObjectSolidAreaDefaultY()
                    }
                }
            }
        }
        return index
    }

    // NPC と MONSTER
    fun checkEntity(entity: Entity, target: Array<Entity?>): Int {

        var index = 999

        for (i in target.indices) {
            if (target[i] != null) {

                entity.getPlayerSolidArea().x += entity.getStartPlayerPointX()
                entity.getPlayerSolidArea().y += entity.getSolidAreaDefaultY()

                target[i]!!.getPlayerSolidArea().x += target[i]!!.getStartPlayerPointX()
                target[i]!!.getPlayerSolidArea().y += target[i]!!.getStartPlayerPointY()

                when (entity.getDirection()) {

                    "up" -> entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
                    "down" -> entity.getPlayerSolidArea().y += entity.getPlayerSpeed()
                    "left" -> entity.getPlayerSolidArea().x -= entity.getPlayerSpeed()
                    "right" -> entity.getPlayerSolidArea().x += entity.getPlayerSpeed()
                }
                if (entity.getPlayerSolidArea().intersects(target[i]!!.getPlayerSolidArea())) {

                    if (target[i] != entity) {
                        entity.setCollisionOn(true)
                        index = 1
                        break
                    }
                }

                entity.getPlayerSolidArea().x = entity.getSolidAreaDefaultX()
                entity.getPlayerSolidArea().y = entity.getSolidAreaDefaultY()
                target[i]!!.getPlayerSolidArea().x = target[i]!!.getSolidAreaDefaultX()
                target[i]!!.getPlayerSolidArea().y = target[i]!!.getSolidAreaDefaultY()
            }
        }
        return index
    }

    fun checkPlayer(entity: Entity): Boolean {

        var contactPlayer = false

        entity.getPlayerSolidArea().x += entity.getStartPlayerPointX()
        entity.getPlayerSolidArea().y += entity.getStartPlayerPointY()

        gamePanel.getPlayer().getPlayerSolidArea().x += gamePanel.getPlayer().getStartPlayerPointX()
        gamePanel.getPlayer().getPlayerSolidArea().y += gamePanel.getPlayer().getStartPlayerPointY()

        when (entity.getDirection()) {

            "up" -> entity.getPlayerSolidArea().y -= entity.getPlayerSpeed()
            "down" -> entity.getPlayerSolidArea().y += entity.getPlayerSpeed()
            "left" -> entity.getPlayerSolidArea().x -= entity.getPlayerSpeed()
            "right" -> entity.getPlayerSolidArea().x += entity.getPlayerSpeed()
        }
        if (entity.getPlayerSolidArea().intersects(gamePanel.getPlayer().getPlayerSolidArea())) {
            entity.setCollisionOn(true)
            contactPlayer = true
        }

        entity.getPlayerSolidArea().x = entity.getSolidAreaDefaultX()
        entity.getPlayerSolidArea().y = entity.getSolidAreaDefaultY()

        gamePanel.getPlayer().getPlayerSolidArea().x = gamePanel.getPlayer().getSolidAreaDefaultX()
        gamePanel.getPlayer().getPlayerSolidArea().y = gamePanel.getPlayer().getSolidAreaDefaultY()
        return contactPlayer
    }
}
