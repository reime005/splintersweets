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

package de.reimerm.splintersweets.menu

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import de.reimerm.splintersweets.actors.scene2d.AudioButton
import de.reimerm.splintersweets.actors.scene2d.PlayPauseResumeButton
import de.reimerm.splintersweets.actors.scene2d.Score
import de.reimerm.splintersweets.actors.scene2d.Timer
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.screens.MenuScreen
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.AudioUtils
import de.reimerm.splintersweets.utils.GameSettings
import de.reimerm.splintersweets.utils.Resources

/**
 * Implementation of the in-game UI, like the play or volume buttons, score, ...
 *
 * Created by Marius Reimer on 19-Jun-16.
 */
class GameMenu : Disposable {

    var table: Table
        private set

    constructor() {
        val pauseResumeButton: ImageButton
        val audioButton: Button

        table = Table()
        table.width = GameSettings.WIDTH
        table.height = GameSettings.HEIGHT * 0.175f
        table.setPosition(0f, GameSettings.HEIGHT - (GameSettings.HEIGHT * 0.175f))
        table.pad(GameSettings.HEIGHT * 0.025f)

        if (GameSettings.debug)
            table.debug()

        val style = ImageButton.ImageButtonStyle()
        val draw: Drawable = Image(AssetsManager.textureMap[Resources.RegionNames.BUTTON_QUIT_NAME.name]).drawable
        style.imageUp = draw

        val scoreLabel = Score("", Label.LabelStyle(AssetsManager.smallFont, Color.LIGHT_GRAY))
        scoreLabel.setAlignment(Align.center)
        val timerLabel = Timer("")
        timerLabel.setAlignment(Align.center)

        val menuButton = ImageButton(style)
        menuButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                MainGame.screen = MenuScreen()
                return false
            }
        })

        pauseResumeButton = PlayPauseResumeButton()

        if (AudioUtils.isMusicOn() == true) {
            audioButton = AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_ON.name]))
        } else {
            audioButton = AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_OFF.name]))
        }

        table.add(menuButton).top().left().size(GameSettings.WIDTH * 0.06f).padRight(GameSettings.WIDTH * 0.02f)
        table.add(pauseResumeButton).top().left().size(GameSettings.WIDTH * 0.06f).padRight(GameSettings.WIDTH * 0.02f)
        table.add(audioButton).top().left().size(GameSettings.WIDTH * 0.06f).expand()

        val tableInner = Table()
        tableInner.setSize(GameSettings.WIDTH * 0.25f, GameSettings.HEIGHT * 0.175f)

        tableInner.add(timerLabel).right().top().expand().padBottom(GameSettings.HEIGHT * 0.001f)
        tableInner.row()
        tableInner.add(scoreLabel).right().bottom().expand()

        table.add(tableInner).right().expand()
    }

    override fun dispose() {
    }
}