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

package de.reimerm.splintersweets.utils

/**
 * All resources listed in an enum class.
 *
 * Created by Marius Reimer on 12-Jun-16.
 */
object Resources {
    val SPRITES_ATLAS_PATH = "sprites.atlas"
    val SPLASH_IMAGE_PATH = "splash.png"
    val BACKGROUND_MUSIC = "background.mp3"
    val FONT_PATH = "font.fnt"
    val GAME_OVER_FONT_PATH = "gameover.fnt"
    val GAME_OVER_SCORE_FONT_PATH = "gameoverscore.fnt"
    val SOUND_BLOP = "blop.mp3"
    val SOUND_BUZZ = "buzz.mp3"

    enum class RegionNames(val str: String) {
        BACKGROUND_NAME("background"),
        BUTTON_LEADERBOARD("button_leaderboard"),
        BUTTON_AUDIO_ON("button_audio_on"),
        BUTTON_AUDIO_OFF("button_audio_off"),
        CIRCLE_RED("jellybig_red"),
        CIRCLE_BROWN("mmstroke_brown"),
        CIRCLE_PINK("swirl_pink"),
        BUTTON_PAUSE_NAME("button_pause"),
        BUTTON_RESUME_NAME("button_resume"),
        BUTTON_AGAIN_NAME("button_again"),
        BUTTON_QUIT_NAME("button_quit");
    }

    enum class AnimationNames(val array: Array<String>) {
        CIRCLE_RED_START_ANIMATIONS(Array(4, { i -> "jellybig_red_anim_" + i })),
        CIRCLE_BROWN_START_ANIMATIONS(Array(4, { i -> "mmstroke_brown_anim_" + i })),
        CIRCLE_PINK_START_ANIMATIONS(Array(4, { i -> "swirl_pink_anim_" + i })),
        CIRCLE_RED_START_ANIMATIONS_REVERSE(Array(4, { i -> "jellybig_red_anim_" + +(4 - i - 1) })),
        CIRCLE_BROWN_START_ANIMATIONS_REVERSE(Array(4, { i -> "mmstroke_brown_anim_" + +(4 - i - 1) })),
        CIRCLE_PINK_START_ANIMATIONS_REVERSE(Array(4, { i -> "swirl_pink_anim_" + +(4 - i - 1) })),
    }
}