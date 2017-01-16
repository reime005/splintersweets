/*
 * Copyright (c) 2017. Marius Reimer
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

package de.reimerm.splintersweets.utils

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Timer
import de.reimerm.splintersweets.enums.Color
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.menu.GameMenu
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Singleton to manage game relevant objects.
 *
 * Created by Marius Reimer on 21-Jun-16.
 */
object GameManager {
    lateinit var bodiesToRemove: Set<Body>
    lateinit var actorsToAdd: Set<Actor>

    var listener: GameEventListener? = null
        @Synchronized get

    lateinit var world: World
        @Synchronized get

    lateinit var gameState: GameState
        @Synchronized get
        @Synchronized set

    var menu: GameMenu? = null

    var time = GameSettings.GAME_TIME
        @Synchronized get
        @Synchronized set

    var score: Long = 0
        @Synchronized get
        @Synchronized set

    var lastColorRemoved: Color = Color.RED

    var comboPoints = GameSettings.CIRCLE_REMOVE_CHAIN

    var levelsPlayed = 0

    var appFirstStart = true

    var onlineScore: Long = 0

    fun reset() {
        gameState = GameState.RUNNING
        bodiesToRemove = Collections.newSetFromMap(ConcurrentHashMap<Body, Boolean>())
        actorsToAdd = Collections.newSetFromMap(ConcurrentHashMap<Actor, Boolean>())
        menu = null
        time = GameSettings.GAME_TIME
        score = 0
    }

    @Synchronized
    fun addBodyToRemove(body: Body) {
        bodiesToRemove = bodiesToRemove.plus(body)
    }

    @Synchronized
    fun addActorToAdd(actor: Actor) {
        actorsToAdd = actorsToAdd.plus(actor)
    }

    @Synchronized
    fun onPause() {
        if (gameState == GameState.RUNNING) {
            gameState = GameState.PAUSED
            AudioUtils.stopMusic()
        }
    }

    @Synchronized
    fun destroyBody(body: Body) {
        // if, for some reason, the body is already destroyed
        if (body.userData == null || body.fixtureList.size == 0) {
            bodiesToRemove = bodiesToRemove.minus(body)
            return
        }

        // for some reason there there was a non reproducible null pointer exception thrown
        try {
            world.destroyBody(body)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        bodiesToRemove = bodiesToRemove.minus(body)
    }

    fun onGameOver() {
        levelsPlayed++

        if (score > onlineScore) {
            onlineScore = score
        }

        if (levelsPlayed >= 5) {
            levelsPlayed = 0

            Timer.schedule(object : Timer.Task() {
                override fun run() {
                    listener?.showInterstitialAd()
                }
            }, 0.5f)
        }
    }
}