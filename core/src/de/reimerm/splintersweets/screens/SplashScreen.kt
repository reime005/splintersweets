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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import de.reimerm.splintersweets.abstract.AbstractStretchStage
import de.reimerm.splintersweets.actors.BackgroundActor
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.AudioUtils
import de.reimerm.splintersweets.utils.GameSettings
import de.reimerm.splintersweets.utils.Resources
import kotlin.concurrent.thread

/**
 * Screen that is shown once when the app is starting and loading assets.
 *
 * Created by Marius Reimer on 18-Jun-16.
 */
class SplashScreen : Screen {

    private var stage: Stage
    private var time = 0f
    private var loaded = false
    private var started = false

    constructor() {
        stage = SplashStretchStage()
    }

    override fun show() {
    }

    override fun pause() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Start the render process
        stage.draw()
        stage.act(delta)

        time += delta

        if (!started) {
            started = true

            thread {
                Gdx.app.postRunnable {
                    AssetsManager.loadAssets()
                    AssetsManager.manager.finishLoading()
                    AssetsManager.loadAtlas()
                    loaded = true
                }
            }
        }

        // load is finished and the game is at least ... seconds running
        if (loaded && time > GameSettings.FIRST_SPLASH_SCREEN_TIME) {
            AudioUtils.init()
            Gdx.app.log("SplashScreen", "load time: " + time)
            MainGame.screen = MenuScreen()
        }
    }

    override fun resume() {
    }

    override fun dispose() {
        stage.dispose()
    }

    private inner class SplashStretchStage : AbstractStretchStage {

        constructor() : super() {
            addActor(BackgroundActor(TextureRegion(AssetsManager.manager.get(Resources.SPLASH_IMAGE_PATH, Texture::class.java))))
        }
    }
}