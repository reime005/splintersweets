@echo off
java -cp gdx.jar;gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker ui/ ../android/assets/ ui.atlas
pause