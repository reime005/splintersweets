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
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.screens.MainScreen
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.AudioUtils
import de.reimerm.splintersweets.utils.GameManager
import de.reimerm.splintersweets.utils.Resources

/**
 * Created by Marius Reimer on 05-Oct-16.
 */
class PlayPauseResumeButton : ImageButton {

    private var imagePlay: Drawable
    private var imagePause: Drawable
    private var imageAgain: Drawable

    constructor(imageUp: Drawable = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_PAUSE_NAME.name])) : super(imageUp) {
        imagePlay = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_RESUME_NAME.name])
        imagePause = imageUp
        imageAgain = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AGAIN_NAME.name])

        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                when (GameManager.gameState) {
                    GameState.OVER -> {
                        MainGame.screen = MainScreen()
                    }

                    GameState.RUNNING -> {
                        GameManager.gameState = GameState.PAUSED
                    }

                    GameState.PAUSED -> {
                        GameManager.gameState = GameState.RUNNING
                        AudioUtils.playBackgroundMusic()
                    }
                }

                return false
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        when (GameManager.gameState) {
            GameState.OVER -> style.imageUp = imageAgain
            GameState.RUNNING -> style.imageUp = imagePause
            GameState.PAUSED -> style.imageUp = imagePlay
        }
    }
}