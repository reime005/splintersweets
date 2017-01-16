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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

/**
 * Created by Marius Reimer on 04-Jul-16.
 */
object PersistenceManager {

    private val preferences: Preferences = Gdx.app.getPreferences(GameSettings.PREFERENCES_GENERAL)

    @Synchronized
    fun saveBoolean(key: String, value: Boolean) {
        preferences.putBoolean(key, value)
        preferences.flush()
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return preferences.getBoolean(key, default)
    }

    fun reset() {
        preferences.clear()
    }
}