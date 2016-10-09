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

package de.reimerm.splintersweets.abstract

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import de.reimerm.splintersweets.actors.GameActor

/**
 * Created by Marius Reimer on 10-Jun-16.
 */
open class AbstractCircleActor : GameActor {

    protected var radius: Float = 0.0f
    protected var texture: TextureRegion? = TextureRegion()

    constructor(body: Body) : super(body) {
        // careful when radius changes over time!
        if (body.fixtureList.size > 0) {
            radius = body.fixtureList[0].shape.radius
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (texture != null) {
            batch?.draw(texture, body.position.x - radius, body.position.y - radius, radius * 2, radius * 2)
        }
    }
}