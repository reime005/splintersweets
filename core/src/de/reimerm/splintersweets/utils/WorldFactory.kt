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
import com.badlogic.gdx.physics.box2d.*
import de.reimerm.splintersweets.enums.UserDataType
import de.reimerm.splintersweets.userdata.UserData

/**
 * Created by Marius Reimer on 10-Jun-16.
 */
object WorldFactory {

    fun createWorld(): World {
        return World(GameSettings.WORLD_GRAVITY, true)
    }

    fun createCircle(position: Vector2? = null): Body {
        val def: BodyDef = BodyDef()
        def.type = BodyDef.BodyType.StaticBody

        if (position == null) {
            def.position.set(BodyUtils.getPosition())
        } else {
            def.position.set(position)
        }

        val body: Body = GameManager.world.createBody(def)

        val shape: CircleShape = CircleShape()
        shape.radius = GameSettings.RADIUS

        val fixtureDef: FixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 0.5f
        fixtureDef.friction = 0.4f
        fixtureDef.restitution = 0.6f

        body.userData = UserData(UserDataType.CIRCLE)
        body.createFixture(fixtureDef)

        shape.dispose()
        return body
    }

    fun createFrame(world: World, position: Vector2, width: Int, height: Int): Body {
        val def: BodyDef = BodyDef()
        def.position.set(position)

        val body: Body = world.createBody(def)

        val shape: PolygonShape = PolygonShape()
        shape.setAsBox(width.toFloat(), height.toFloat())

        val fixtureDef: FixtureDef = FixtureDef()
        fixtureDef.shape = shape

        body.userData = UserData(UserDataType.FRAME)
        body.gravityScale = 0f
        body.createFixture(fixtureDef)
        shape.dispose()
        return body
    }
}