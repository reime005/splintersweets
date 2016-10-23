package de.reimerm.splintersweets.leaderboards

import de.reimerm.splintersweets.utils.GameSettings

enum class Leaderboard
constructor(val id: String) {
    Scores(GameSettings.PLAY_SERVICE_LEADERBOARD),
    Test("")
}
