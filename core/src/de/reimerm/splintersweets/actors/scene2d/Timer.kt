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

package de.reimerm.splintersweets.actors.scene2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.GameManager

/**
 * Created by Marius Reimer on 05-Oct-16.
 */
class Timer(text: CharSequence? = "", style: LabelStyle = Label.LabelStyle(AssetsManager.smallFont, Color.GOLDENROD)) : Label(text, style) {

    private var startTime = 3f

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (GameManager.gameState == GameState.RUNNING) {

            if (startTime >= 0f) {
                startTime -= Gdx.graphics.deltaTime
            } else {
                GameManager.time -= Gdx.graphics.deltaTime
            }

            if (GameManager.time < 0f) {
                GameManager.time = 0f
                GameManager.gameState = GameState.OVER
            }
        }

        setText(String.format("%.0f", GameManager.time))
    }
}