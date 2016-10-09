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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.Body
import de.reimerm.splintersweets.enums.Color
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.utils.*

/**
 * Created by Marius Reimer on 10-Jun-16.
 */
class SpawnCircle : GameActor {

    private var removeTimer = 0f
    private var radius = GameSettings.RADIUS
    private var animation: Animation? = null
    private var reverse = false

    constructor(body: Body, color: Color?, reverse: Boolean) : super(body) {
        this.reverse = reverse

        if (color == null) {
            userData.color = BodyUtils.getRandomColor()
        } else {
            userData.color = color
        }

        when (userData.color) {
            Color.BROWN -> {
                if (!reverse) {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_BROWN_START_ANIMATIONS.name]
                } else {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_BROWN_START_ANIMATIONS_REVERSE.name]
                }
            }
            Color.PINK -> {
                if (!reverse) {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_PINK_START_ANIMATIONS.name]
                } else {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_PINK_START_ANIMATIONS_REVERSE.name]
                }
            }
            Color.RED -> {
                if (!reverse) {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_RED_START_ANIMATIONS.name]
                } else {
                    animation = AssetsManager.animationMap[Resources.AnimationNames.CIRCLE_RED_START_ANIMATIONS_REVERSE.name]
                }
            }
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (GameManager.gameState == GameState.RUNNING) {
            removeTimer += Gdx.graphics.deltaTime
        }

        if (animation != null && removeTimer <= 0.5f) {
            batch?.draw(animation?.getKeyFrame(removeTimer * 1.5f, false), body.position.x - radius, body.position.y - radius, radius * 2f, radius * 2f)
        } else {
            if (reverse) {
                GameManager.addActorToAdd(Circle(WorldFactory.createCircle(body.position), userData.color))
            } else {
                GameManager.addActorToAdd(SpawnCircle(WorldFactory.createCircle(), null, true))
            }
            GameManager.addBodyToRemove(body)
            remove()
        }
    }
}