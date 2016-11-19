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

package de.reimerm.splintersweets.screens

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.reimerm.splintersweets.abstract.AbstractStretchStage
import de.reimerm.splintersweets.actors.BackgroundActor
import de.reimerm.splintersweets.actors.scene2d.AudioButton
import de.reimerm.splintersweets.actors.scene2d.LeaderBoardButton
import de.reimerm.splintersweets.actors.scene2d.Score
import de.reimerm.splintersweets.enums.GameState
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.utils.*

/**
 * Screen for the Main Menu.
 *
 * Created by Marius Reimer on 18-Jun-16.
 */
class MenuScreen : Screen {

    private var stage: Stage
    private var exitButton: Button

    constructor() {
        val audioButton: Button

        stage = MenuScreenStretchStage()
        Gdx.input.inputProcessor = stage

        val table: Table = Table()
        stage.addActor(table)
        table.setFillParent(true)
        table.pad(GameSettings.HEIGHT * 0.025f)

        val styleExitButton = ImageButton.ImageButtonStyle()
        val drawExitButton: Drawable = Image(AssetsManager.textureMap[Resources.RegionNames.BUTTON_QUIT_NAME.name]).drawable
        styleExitButton.imageUp = drawExitButton

        val stylePlayButton = ImageButton.ImageButtonStyle()
        val drawPlayButton: Drawable = Image(AssetsManager.textureMap[Resources.RegionNames.BUTTON_RESUME_NAME.name]).drawable
        stylePlayButton.imageUp = drawPlayButton

        if (GameSettings.debug)
            table.debug()

        exitButton = ImageButton(styleExitButton)
        exitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                AssetsManager.dispose()
                System.exit(0)
            }
        })

        if (AudioUtils.isMusicOn() == true) {
            audioButton = AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_ON.name]))
        } else {
            audioButton = AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_OFF.name]))
        }

        val playButton: ImageButton = ImageButton(stylePlayButton)
        playButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                MainGame.screen = MainScreen()
                return false
            }
        })

        table.add(exitButton).top().left().size(GameSettings.WIDTH * 0.06f).padRight(GameSettings.WIDTH * 0.02f)
        table.add(audioButton).top().left().size(GameSettings.WIDTH * 0.06f).expand()
        table.add(Score("", Label.LabelStyle(AssetsManager.smallFont, Color.LIGHT_GRAY))).right().top()
        table.row()
        table.add(playButton).expand().size(GameSettings.WIDTH * 0.15f).center().colspan(3)
        table.row()
        if (Gdx.app.type == Application.ApplicationType.Android || Gdx.app.type == Application.ApplicationType.iOS) {
            table.add(LeaderBoardButton()).expand().size(GameSettings.WIDTH * 0.15f).center().colspan(3).padBottom(GameSettings.WIDTH * 0.05f)
        } else {
            table.add().expand().size(GameSettings.WIDTH * 0.15f).center().colspan(3).padBottom(GameSettings.WIDTH * 0.05f)
        }

        if (GameManager.appFirstStart) {
            GameManager.appFirstStart = false
            GameManager.listener?.login()
        }
    }

    override fun show() {
        GameManager.gameState = GameState.MENU
        GameManager.listener?.showAd()
        AudioUtils.playBackgroundMusic()
    }

    override fun pause() {
        GameManager.gameState = GameState.PAUSED
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.viewport.apply()
        stage.draw()
        stage.act(delta)
    }

    override fun resume() {
    }

    override fun dispose() {
        stage.dispose()
    }

    private inner class MenuScreenStretchStage : AbstractStretchStage {

        constructor() : super() {
            addActor(BackgroundActor(AssetsManager.textureMap[Resources.RegionNames.BACKGROUND_NAME.name]))
        }

        override fun keyDown(keyCode: Int): Boolean {
            when (keyCode) {
                Input.Keys.BACK -> {
                    exitButton.toggle()
                }
            }
            return super.keyDown(keyCode)
        }
    }
}