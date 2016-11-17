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

package de.reimerm.splintersweets.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Array
import de.reimerm.splintersweets.enums.Color
import java.util.*

/**
 * Defines operations on bodies.
 *
 * Created by Marius Reimer on 22-Jun-16.
 */
object BodyUtils {
    var random: Random = Random()

    fun bodyIsOutOfWorld(body: Body): Boolean {
        return ((body.position.x < 0 || body.position.y < 0 || body.position.x > GameSettings.WIDTH || body.position.y > GameSettings.HEIGHT))
    }

    private fun getRandomPosition(): Vector2 {
        val gap = (GameSettings.RADIUS * 3.5f).toInt()
        var x: Float = random.nextInt(GameSettings.WIDTH.toInt() - gap).toFloat()
        if (x <= gap) {
            x = gap.toFloat()
        }

        var y: Float = random.nextInt((GameSettings.HEIGHT.toInt() - gap * 1.25f).toInt()).toFloat()
        if (y <= gap) {
            y = gap.toFloat()
        }

        return Vector2(x, y)
    }

    private fun isBodyOnPosition(position: Vector2, distance: Float): Boolean {
        val bodies: Array<Body> = Array(GameManager.world.bodyCount)
        GameManager.world.getBodies(bodies)

        for (b in bodies) {
            if (b.position.dst(position) < distance) {
                return true
            }
        }

        return false
    }

    fun getPosition(): Vector2 {
        var pos = getRandomPosition()

        while (isBodyOnPosition(pos, GameSettings.RADIUS * 2.25f)) {
            pos = getRandomPosition()
        }

        return pos
    }

    fun getRandomColor(): Color {
        when (random.nextInt(3)) {
            0 -> return Color.RED
            1 -> return Color.BROWN
            2 -> return Color.PINK
        }
        return Color.RED
    }
}