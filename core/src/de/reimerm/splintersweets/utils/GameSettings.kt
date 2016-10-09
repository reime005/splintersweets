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

import com.badlogic.gdx.math.Vector2

/**
 * Singleton to manage game relevant settings.
 *
 * Created by Marius Reimer on 10-Jun-16.
 */
object GameSettings {
    val PREFERENCES_GENERAL = "general_prefs"
    val MUSIC_PREFERENCES = "music"
    val debug = false
    val TIME_STEP: Float = 1 / 250f;
    val HEIGHT = 480f
    val WIDTH = 800f
    val WORLD_GRAVITY: Vector2 = Vector2(0f, 0f)
    val FIRST_SPLASH_SCREEN_TIME = 5f
    val RADIUS = 25f
    val GAME_TIME = 50f
    val CIRCLE_REMOVE_CHAIN = 100
    val CIRCLE_REMOVE_NORMAL = 50
    val ANIMATION_FRAME_DURATION = 0.15f

    val CLIENT_ID = "1092360994879-8m1ki1gtjseni28ud2d9s3q2cnmfl4c0.apps.googleusercontent.com"
    val PLAY_SERVICE_LEADERBOARD = "CgkIv4CoruUfEAIQAQ"

    val ADMOB_APP_ID = "ca-app-pub-9059743820788712~2262510588"
    val ADMOB_BANNER_ID = "ca-app-pub-9059743820788712/6692710180"
    val ADMOB_INTERSTITIAL_ID = "ca-app-pub-9059743820788712/8169443387"
}