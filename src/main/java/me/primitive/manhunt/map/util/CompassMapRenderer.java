package me.primitive.manhunt.map.util;

import java.io.IOException;
import java.util.HashMap;
import me.primitive.manhunt.containers.PlayerInPvh;
import me.primitive.manhunt.containers.PvhHunter;
import me.primitive.manhunt.management.GameTeamManager;
import me.primitive.manhunt.render.Sprite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class CompassMapRenderer extends MapRenderer {

    public static HashMap<Player, Double> hunterRotateMap = new HashMap<Player, Double>();
    public static Player trackedPlayer;
    private static Sprite sprite = null;
    private static double anglePerFrame;
    private static boolean isInited = false;

    static {
        try {
            sprite = new Sprite("resources/compass-sprite.png", 64);
            anglePerFrame = Math.PI * 2 / (sprite.frameCount - 1);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Sprite not loaded! " + e.getLocalizedMessage());
        }
    }

    public CompassMapRenderer(Player trackedPlayer) {
        if (!isInited) {
            for (PlayerInPvh playerInPvh : GameTeamManager.INSTANCE.pvhPlayers) {
                if (playerInPvh instanceof PvhHunter) {
                    hunterRotateMap.put(playerInPvh.getBukkitPlayer(), 0.0);
                }
            }

            isInited = true;
        }

        CompassMapRenderer.trackedPlayer = trackedPlayer;
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        double rotate = hunterRotateMap.get(player);
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
            int frame = (int) Math.round(rotate / anglePerFrame);
            if (frame < 0) {
                frame += sprite.frameCount;
            }
            canvas.drawImage(31, 31, sprite.frames[frame]);
            drawTextCenter(canvas, trackedPlayer.getDisplayName(), 105, MapPalette.RED);
        }
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
            MapFont.CharacterSprite symbolSprite = MinecraftFont.Font.getChar(symbol);
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
