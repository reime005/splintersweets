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

package de.reimerm.splintersweets.main

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import de.reimerm.splintersweets.screens.SplashScreen
import de.reimerm.splintersweets.utils.AssetsManager

/**
 * Launcher for the game.
 *
 * Created by Marius Reimer on 10-Jun-16.
 */
object MainGame : Game() {

    override fun create() {
        AssetsManager.loadSplashAssets()
        setScreen(SplashScreen())
    }

    override fun setScreen(screen: Screen?) {
        super.setScreen(screen)
    }

    override fun dispose() {
        super.dispose()
        screen?.dispose()
    }
}