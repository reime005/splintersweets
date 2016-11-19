package de.reimerm.splintersweets.actors.scene2d

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.GameSettings
import de.reimerm.splintersweets.utils.Resources

/**
 * Created by mariu on 04.11.2016.
 */

class HighScoreTable(scores: Map<String, String>?) : Table() {

    val pane: ScrollPane

    init {
        val paneStyle = ScrollPane.ScrollPaneStyle()
        paneStyle.background = TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.HIGHSCORE_PANE.name])

        pane = ScrollPane(this, paneStyle)
        pane.setScrollingDisabled(true, false)
        pane.setFadeScrollBars(false)

        var labelStyle = Label.LabelStyle(AssetsManager.highscoreTextFont, Color.WHITE)

        if (scores == null) {
            add(Label("Loading data...", labelStyle)).expand().colspan(3)
        } else if (scores.isEmpty()) {
            add(Label("No data available", labelStyle)).expand().colspan(3)
        } else {
            labelStyle = Label.LabelStyle(AssetsManager.highscoreTextFont, Color.WHITE)

            var i = 1
            for ((key, value) in scores) {
                row().padBottom(GameSettings.WIDTH * 0.015f)
                add(Label("" + i, labelStyle)).expand().colspan(1)
                add(Label(key, labelStyle)).expand().colspan(1)
                add(Label(value, labelStyle)).expand().colspan(1)
                i++
            }

            for (k in 0..5) {

                row().padBottom(GameSettings.WIDTH * 0.015f)
                add(Label("", labelStyle)).expand().colspan(1)
                add(Label("", labelStyle)).expand().colspan(1)
                add(Label("", labelStyle)).expand().colspan(1)
            }
        }
    }
}
