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

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.AudioUtils
import de.reimerm.splintersweets.utils.Resources

/**
 * Created by Marius Reimer on 05-Oct-16.
 */
class AudioButton : ImageButton {

    private var imageOn: Drawable
    private var imageOff: Drawable

    constructor(imageUp: Drawable) : super(imageUp) {
        imageOn = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_ON.name])
        imageOff = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_OFF.name])

        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (AudioUtils.isMusicOn() == true) {
                    AudioUtils.stopMusic()
                }

                AudioUtils.toggleMusicPreference()
                AudioUtils.playBackgroundMusic()

                if (AudioUtils.isMusicPreferenceTrue()) {
                    style.imageUp = imageOn
                } else {
                    style.imageUp = imageOff
                }
            }
        })
    }
}