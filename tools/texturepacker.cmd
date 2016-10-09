@echo off
java -cp gdx.jar;gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker sprites/ ../android/assets/ sprites.atlas
pause