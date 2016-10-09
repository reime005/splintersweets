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
import com.badlogic.gdx.scenes.scene2d.Actor
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.userdata.UserData
import de.reimerm.splintersweets.utils.GameManager

/**
 * A basic class that you can derive from to create actors.
 *
 * Created by Marius Reimer on 11-Jun-16.
 */
open class GameActor : Actor {

    lateinit var body: Body
    var userData: UserData

    constructor(body: Body) {
        this.body = body
        userData = body.userData as UserData
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (GameManager.gameState != GameState.RUNNING) {
            return
        }

        if (body == null || body.userData == null) {
            remove()
        }
    }
}