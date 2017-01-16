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

/**
 * Created by Marius Reimer on 20-Sep-16.
 */
interface GameEventListener {
    fun login()
    fun displayLeaderBoard()
    fun showAd()
    fun hideAd()
    fun submitScore()
    fun showInterstitialAd()
    fun loadHighScore()
}