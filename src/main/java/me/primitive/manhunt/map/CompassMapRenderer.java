package me.primitive.manhunt.map;

import java.io.IOException;

import lombok.val;
import lombok.var;
import me.primitive.manhunt.game.container.ManhuntSpeedrunner;
import me.primitive.manhunt.util.Sprite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import org.jetbrains.annotations.NotNull;

public class CompassMapRenderer extends MapRenderer {
    private static Sprite sprite = null;
    private static double anglePerFrame;
    static {
        try {
            sprite = new Sprite("compass-sprite.png", 64);
            anglePerFrame = Math.PI * 2 / (sprite.frameCount - 1);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Sprite not loaded! " + e.getLocalizedMessage());
        }
    }

    public ManhuntSpeedrunner target;
    public CompassMapRenderer(ManhuntSpeedrunner target) {
        this.target = target;
    }

    @Override
    public void render(@NotNull MapView view, @NotNull MapCanvas canvas, @NotNull Player player) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                canvas.setPixel(x, y, (byte) 0);
            }
        }

        if (sprite == null) {
            drawLine(canvas, 16, 55, 112, 55, MapPalette.RED);
            drawTextCenter(canvas, "[ MAP ERROR ]", 60, MapPalette.RED);
            drawLine(canvas, 16, 72, 112, 72, MapPalette.RED);
        } else {
            nextFrame(player, canvas);
        }
    }

    private void nextFrame(Player trackerOwner, MapCanvas canvas) {
        var targetPlayer = target.getPlayer();
        var isRotating = targetPlayer == null || targetPlayer.isDead();

        int frame = 0;
        if (!isRotating) {
            val speedrunner = targetPlayer.getLocation();
            val hunter = trackerOwner.getLocation();

            var deltaX = speedrunner.getX() - hunter.getX();
            var deltaZ = speedrunner.getZ() - hunter.getZ();

            var distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            if (distance < 1) {
                isRotating = true;
            } else {
                if (distance > 500) {
                    drawTextCenter(canvas, ((double) Math.round(distance / 100) / 10) + "k blocks", 22, MapPalette.BLUE);
                }

                var rotate = (Math.atan2(deltaX, deltaZ) + Math.toRadians(hunter.getYaw())) % (Math.PI * 2);
                frame = (int) Math.round(rotate / anglePerFrame);
            }
        }

        if (isRotating) {
            frame = (int) System.currentTimeMillis() % (32 * 50) / 50;
        }

        if (frame < 0) {
            frame += sprite.frameCount;
        }

        canvas.drawImage(31, 31, sprite.frames[frame]);
        drawTextCenter(canvas, target.getOfflinePlayer().getName(), 105, MapPalette.RED);
    }

    private void drawLine(MapCanvas canvas, int x1, int y1, int x2, int y2, byte color) {
        double deltaX = (x2 - x1);
        double deltaY = (y2 - y1);
        double deltaXm = Math.abs(deltaX);
        double deltaYm = Math.abs(deltaY);
        double step = deltaXm >= deltaYm ? deltaXm : deltaYm;

        deltaX = deltaX / step;
        deltaY = deltaY / step;
        double x = x1;
        double y = y1;
        int i = 1;
        while (i <= step) {
            canvas.setPixel((int) x, (int) y, color);
            x += deltaX;
            y += deltaY;
            i++;
        }
    }

    private void drawTextCenter(MapCanvas canvas, String text, int y, byte color) {
        int x = 63 - MinecraftFont.Font.getWidth(text) / 2;
        for (char symbol : text.toCharArray()) {
            val symbolSprite = MinecraftFont.Font.getChar(symbol);
            for (int row = 0; row < symbolSprite.getHeight(); row++) {
                for (int col = 0; col < symbolSprite.getWidth(); col++) {
                    if (!symbolSprite.get(row, col)) {
                        continue;
                    }
                    canvas.setPixel(x + col, y + row, color);
                }
            }

            x += symbolSprite.getWidth() + 1;
        }
    }
}
