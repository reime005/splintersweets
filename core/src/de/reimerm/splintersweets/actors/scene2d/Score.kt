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

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Label
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.utils.GameManager

/**
 * Created by Marius Reimer on 05-Oct-16.
 */
class Score(text: CharSequence? = "", style: LabelStyle?) : Label(text, style) {

    private var score: Long = 0

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (GameManager.gameState != GameState.PAUSED) {
            score = GameManager.score
        }

        if (GameManager.gameState == GameState.MENU) {
            score = GameManager.onlineScore
        }

        setText(score.toString())
    }
}