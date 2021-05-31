package me.primitive.manhunt.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.val;
import me.primitive.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sprite {

    public final int frameCount;
    /*private*/ public Image[] frames; //TODO

    public Sprite(String path, int height) throws IOException {
        val resourceInputStream = JavaPlugin.getPlugin(Manhunt.class).getResource(path);

        if (resourceInputStream == null) {
            throw new RuntimeException("Compass sprite not found");
        }

        BufferedImage sprite = ImageIO.read(resourceInputStream);

        frameCount = sprite.getHeight() / height;
        Bukkit.getLogger().info("sprite frames: " + frameCount);
        frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = sprite.getSubimage(0, i * height, sprite.getWidth(), height);
        }
    }

    public Image getFrame(int number) {
        return frames[number];
    }
}
