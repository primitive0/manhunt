package me.primitive.manhunt.render;

import lombok.val;
import lombok.var;
import me.primitive.manhunt.ManhuntPlugin;
import me.primitive.manhunt.ManhuntTrackerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import static me.primitive.manhunt.ManhuntTrackerManager.isTracker;

public class TrackerMapRenderer extends MapRenderer {
    private static Sprite trackerSprite;

    private static double anglePerFrame;

    boolean rendered = false;

    public static void init() {
        try {
            trackerSprite = new Sprite("resources/compass-sprite.png", 64);
            anglePerFrame = 2 * Math.PI / (trackerSprite.frameCount - 1);
        } catch (Exception e) {
            ManhuntPlugin.LOGGER.warning("Tracker sprite is not loaded!");
        }
    }

    public TrackerMapRenderer() {
        super(true);
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                canvas.setPixel(i, j, MapPalette.TRANSPARENT);
            }
        }

        //val startPoint = new Location(ManhuntPlugin.OVERWORLD, 0.0, 0.0, 0.0);
        val playerLocation = player.getLocation();

        var rotate = Math.atan2(playerLocation.getX(), -playerLocation.getZ())/* + playerLocation.getYaw()*/;

        if (rotate < 0)
            rotate += 2 * Math.PI;


        rotate = 2 * Math.PI - rotate;

        //val compassRotate = deg(deg(rotate + 270) + deg(playerLocation.getYaw()));
        val frame = (int) Math.round(rotate / anglePerFrame);

        canvas.drawImage(31, 31, trackerSprite.getFrame(frame));

//        if (rotate < 0)
//            rotate += 360;
//
//        rotate -= 90 + playerLocation.getYaw();

        //drawTextCenter(canvas, Double.toString(, 60, MapPalette.RED);
        drawTextCenter(canvas, Double.toString(Math.toDegrees(rotate)), 105, MapPalette.RED);
        drawTextCenter(canvas, Integer.toString(frame), 115, MapPalette.RED);
    }

    private void drawTextCenter(MapCanvas canvas, String text, int y, byte color) {
        int x = 63 - MinecraftFont.Font.getWidth(text) / 2;
        for (char symbol : text.toCharArray()) {
            MapFont.CharacterSprite symbolSprite = MinecraftFont.Font.getChar(symbol);
            for (int row = 0; row < symbolSprite.getHeight(); row++) {
                for (int col = 0; col < symbolSprite.getWidth(); col++) {
                    if (!symbolSprite.get(row, col)) continue;
                    canvas.setPixel(x + col, y + row, color);
                }
            }

            x += symbolSprite.getWidth() + 1;
        }
    }

    private boolean shouldRender(Player player) {
        val inv = player.getInventory();

        return isTracker(inv.getItemInMainHand()) || isTracker(inv.getItemInOffHand());
    }


    public static double deg(double in) {
        val res = in % 360;

        if (res < 0)
            return 360 + res;

        return res;
    }
}
