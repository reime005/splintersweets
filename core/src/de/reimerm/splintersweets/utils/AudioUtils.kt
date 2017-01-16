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

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.utils.Disposable

/**
 * Created by Marius Reimer on 08-Jul-16.
 */
object AudioUtils : Disposable {

    private var music: Music? = null

    fun init() {
        music = AssetsManager.manager.get(Resources.BACKGROUND_MUSIC, Music::class.java)
        music?.isLooping = true
        music?.volume = 1f
        playBackgroundMusic()
    }

    fun playBackgroundMusic() {
        if (isMusicPreferenceTrue() && music?.isPlaying == false) {
            music?.play()
        }
    }

    private fun playSound(sound: Sound) {
        if (isMusicOn() == true) {
            sound.play(0.5f)
        }
    }

    @Synchronized
    fun playBlopSound() {
        playSound(AssetsManager.manager.get(Resources.SOUND_BLOP, Sound::class.java))
    }

    fun playGameOverSound() {
        playSound(AssetsManager.manager.get(Resources.SOUND_BUZZ, Sound::class.java))
    }

    fun stopMusic() {
        music?.pause()
    }

    fun isMusicOn(): Boolean? {
        return music?.isPlaying
    }

    fun toggleMusicPreference() {
        PersistenceManager.saveBoolean(GameSettings.MUSIC_PREFERENCES, !isMusicPreferenceTrue())
    }

    fun isMusicPreferenceTrue(): Boolean {
        return de.reimerm.splintersweets.utils.PersistenceManager.getBoolean(GameSettings.MUSIC_PREFERENCES, true)
    }

    override fun dispose() {
        music?.dispose()
    }
}