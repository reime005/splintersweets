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

package de.reimerm.splintersweets.utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import java.util.*

/**
 * Stores all assets for the game.
 *
 * Created by Marius Reimer on 12-Jun-16.
 */
object AssetsManager : Disposable {

    var manager: AssetManager = AssetManager()
        private set

    var textureAtlas: TextureAtlas = TextureAtlas()
        private set

    var textureMap: HashMap<String, TextureRegion> = HashMap()
        private set

    var animationMap: HashMap<String, Animation<TextureRegion>> = HashMap()
        private set

    lateinit var gameOverFont: BitmapFont
    lateinit var gameOverScoreFont: BitmapFont
    lateinit var smallFont: BitmapFont

    lateinit var highscoreTitleFont: BitmapFont
    lateinit var highscoreTextFont: BitmapFont

    fun loadSplashAssets() {
        manager.load(Resources.SPLASH_IMAGE_PATH, Texture::class.java)
        manager.finishLoading()
    }

    fun loadAssets() {
        manager.load(Resources.BACKGROUND_MUSIC, Music::class.java)
        manager.load(Resources.SOUND_BLOP, Sound::class.java)
        manager.load(Resources.SOUND_BUZZ, Sound::class.java)
        manager.load(Resources.SPRITES_ATLAS_PATH, TextureAtlas::class.java)
        manager.load(Resources.FONT_PATH, BitmapFont::class.java)
        manager.load(Resources.GAME_OVER_FONT_PATH, BitmapFont::class.java)
        manager.load(Resources.GAME_OVER_SCORE_FONT_PATH, BitmapFont::class.java)
        manager.finishLoading()
        loadAtlas()
    }

    private fun loadAtlas() {
        textureAtlas = manager.get(Resources.SPRITES_ATLAS_PATH, TextureAtlas::class.java)

        for (e in Resources.RegionNames.values()) {
            textureMap.put(e.name, textureAtlas.findRegion(e.str))
        }

        for (e in Resources.AnimationNames.values()) {
            animationMap.put(e.name, createAnimation(e.array))
        }

        makeFonts()
    }

    fun makeFonts() {
        smallFont = manager[Resources.FONT_PATH]
        smallFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        smallFont.data.scale(0.08f)

        gameOverFont = manager[Resources.GAME_OVER_FONT_PATH]
        gameOverFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        gameOverFont.data.scale(0.3f)

        gameOverScoreFont = manager[Resources.GAME_OVER_SCORE_FONT_PATH]
        gameOverScoreFont.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        gameOverScoreFont.data.scale(0.4f)

        highscoreTitleFont = smallFont

        highscoreTextFont = smallFont
    }

    private fun createAnimation(regionNames: Array<String>): Animation<TextureRegion> {
        val frames = com.badlogic.gdx.utils.Array<TextureRegion>()
        frames.setSize(regionNames.size)

        for (i in regionNames.indices) {
            val path = regionNames[i]
            frames[i] = textureAtlas.findRegion(path)
        }

        return Animation<TextureRegion>(0.1f, frames)
    }

    override fun dispose() {
        textureAtlas.dispose()
        textureMap.clear()
        smallFont.dispose()
        gameOverFont.dispose()
        gameOverScoreFont.dispose()
        manager.dispose()
    }
}