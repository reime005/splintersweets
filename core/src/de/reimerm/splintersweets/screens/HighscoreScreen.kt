package de.reimerm.splintersweets.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.reimerm.splintersweets.abstract.AbstractStretchStage
import de.reimerm.splintersweets.actors.BackgroundActor
import de.reimerm.splintersweets.actors.scene2d.AudioButton
import de.reimerm.splintersweets.actors.scene2d.HighScoreTable
import de.reimerm.splintersweets.main.MainGame
import de.reimerm.splintersweets.utils.AssetsManager
import de.reimerm.splintersweets.utils.AudioUtils
import de.reimerm.splintersweets.utils.GameSettings
import de.reimerm.splintersweets.utils.Resources
import java.util.*

/**
 * Created by mariu on 16.11.2016.
 */
class HighscoreScreen : Screen {

    private lateinit var stage: Stage
    private lateinit var stretchStage: Stage
    private lateinit var scoreData: HashMap<String, String>
    private var timer = 0f
    private lateinit var table: Table
    private lateinit var highScoreTable: HighScoreTable
    private var loaded = false
    private var exitButton: Button? = null

    constructor (scoreData: HashMap<String, String>, loaded: Boolean) {

        this.loaded = loaded
        stage = HighscoreScreenStretchStage()
        stretchStage = HighscoreScreenStretchStage()
        Gdx.input.inputProcessor = stage

        if (AudioUtils.isMusicOn() == false) {
            AudioUtils.playBackgroundMusic()
        }

        this.scoreData = scoreData

        setupTable()
    }

    private fun setupTable() {
        table = Table()
        stage.addActor(table)

        table.setFillParent(true)
        table.pad(GameSettings.HEIGHT * 0.025f)

        val title = Label("High Scores", Label.LabelStyle(AssetsManager.highscoreTitleFont, null))

        val audioButton =
        if (AudioUtils.isMusicOn() == true) {
            AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_ON.name]))
        } else {
            AudioButton(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_AUDIO_OFF.name]))
        }

        val exitButton = createExitButton()

        if (loaded) {
            highScoreTable = HighScoreTable(scoreData)
        } else {
            highScoreTable = HighScoreTable(null)
        }

        table.add(audioButton).left().top().size(GameSettings.WIDTH * 0.06f)
        table.add(title).expandX().center()
        table.add(exitButton).right().top().size(GameSettings.WIDTH * 0.06f)

        table.row()

        table.add().height(GameSettings.HEIGHT * 0.075f)

        table.row()

        val labelStyle = Label.LabelStyle(AssetsManager.highscoreTextFont, Color.LIGHT_GRAY)

        table.add(Label("Rank", labelStyle)).expandX().colspan(1)
        table.add(Label("Player", labelStyle)).expandX().colspan(1)
        table.add(Label("Score", labelStyle)).expandX().colspan(1)

        table.row()

        table.add(highScoreTable.pane).center().colspan(3).size(GameSettings.WIDTH - table.padLeft - table.padRight, GameSettings.HEIGHT * 0.65f).expand()
    }

    private fun createExitButton(): ImageButton? {
        val exitButton = ImageButton(ImageButton.ImageButtonStyle(TextureRegionDrawable(AssetsManager.textureMap[Resources.RegionNames.BUTTON_QUIT_NAME.name]), null, null, null, null, null))

        exitButton.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                MainGame.screen = MenuScreen()
                return super.touchDown(event, x, y, pointer, button)
            }
        })

        return exitButton
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Start the render process
        stretchStage.viewport.apply()
        stretchStage.draw()

        stage.viewport.apply()
        stage.draw()
        stage.act(delta)

        // max time of 7 seconds before "no data is available"
        if (timer >= 7f && !loaded) {
            loaded = true
            table.clear()
            setupTable()
        }

        timer += delta
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
        stretchStage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        stage.dispose()
        stretchStage.dispose()
    }

    private inner class HighscoreScreenStretchStage : AbstractStretchStage {

        constructor() : super() {
            addActor(BackgroundActor(AssetsManager.textureMap[Resources.RegionNames.BACKGROUND_NAME.name]))
        }

        override fun keyDown(keyCode: Int): Boolean {
            when (keyCode) {
                Input.Keys.BACK -> {
                    exitButton?.toggle()
                }
            }
            return super.keyDown(keyCode)
        }
    }
}