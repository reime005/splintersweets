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

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.screens.MenuScreen
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.GameSettings
import de.reimerm.splintersweets.utils.Resources

/**
 * Created by Marius Reimer on 06-Oct-16.
 */
class GameOverTable : Table {

    constructor() : super() {
        setFillParent(true)

        if (GameSettings.debug)
            debug()

        val style = Label.LabelStyle(AssetsManager.gameOverFont, Color.GOLDENROD)
        val label = Label("GAME OVER", style)

        val scoreLabel = Score("", Label.LabelStyle(AssetsManager.gameOverScoreFont, Color.LIGHT_GRAY))
        scoreLabel.setAlignment(Align.center)

        val menuButton = ImageButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_QUIT_NAME.name]))
        menuButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                MainGame.screen = MenuScreen()
                return false
            }
        })

        add(label).center().expandX().colspan(3).padBottom(GameSettings.WIDTH * 0.05f)
        row()
        add(scoreLabel).center().expandX().colspan(3).padBottom(GameSettings.WIDTH * 0.05f)
        row()
        add(menuButton).size(GameSettings.WIDTH * 0.1f).center().expandX()
        if (Gdx.app.type == Application.ApplicationType.Android || Gdx.app.type == Application.ApplicationType.iOS) {
            add(LeaderBoardButton()).size(GameSettings.WIDTH * 0.1f).center().expandX()
        } else {
            add().size(GameSettings.WIDTH * 0.1f).center().expandX()
        }
        add(PlayPauseResumeButton()).size(GameSettings.WIDTH * 0.1f).center().expandX()
    }
}