/*
 * Copyright (c) 2016. Marius Reimer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.reimerm.splintersweets.actors

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import de.reimerm.splintersweets.abstract.AbstractCircleActor
import de.reimerm.splintersweets.actors.scene2d.PointsLabel
import de.reimerm.splintersweets.enums.Color
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.utils.*

/**
 * Created by Marius Reimer on 10-Jun-16.
 */
class Circle : AbstractCircleActor {

    private var removed = false

    constructor(body: Body, color: Color) : super(body) {
        userData.color = color

        when (userData.color) {
            Color.RED -> {
                texture = AssetsManager.textureMap[Resources.RegionNames.CIRCLE_RED.name]
            }
            Color.BROWN -> {
                texture = AssetsManager.textureMap[Resources.RegionNames.CIRCLE_BROWN.name]
            }
            Color.PINK -> {
                texture = AssetsManager.textureMap[Resources.RegionNames.CIRCLE_PINK.name]
            }
        }

        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (GameManager.gameState == GameState.RUNNING) {
                    if (GameManager.lastColorRemoved == userData.color) {
                        GameManager.comboPoints += GameSettings.CIRCLE_REMOVE_CHAIN / 10
                        addPointsLabel(GameManager.comboPoints)
                        GameManager.score = GameManager.score + GameManager.comboPoints
                    } else {
                        addPointsLabel(GameSettings.CIRCLE_REMOVE_NORMAL)
                        GameManager.score = GameManager.score + GameSettings.CIRCLE_REMOVE_NORMAL
                        GameManager.comboPoints = GameSettings.CIRCLE_REMOVE_CHAIN
                    }

                    GameManager.lastColorRemoved = userData.color
                    GameManager.addBodyToRemove(body)
                    remove()
                }
            }
        })

        setBounds(body.position.x - radius, body.position.y - radius, radius * 2, radius * 2)
    }

    private fun addPointsLabel(value: Int) {
        GameManager.addActorToAdd(PointsLabel(body.position.x, body.position.y, "+" + value))
    }

    override fun remove(): Boolean {
        if (!removed) {
            removed = true
            AudioUtils.playBlopSound()
            GameManager.addActorToAdd(SpawnCircle(WorldFactory.createCircle(body.position), userData.color, false))
        }

        return super.remove()
    }
}