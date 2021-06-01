package me.primitive.manhunt.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.val;
import me.primitive.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Sprite {

    @Getter
    private final int frameCount;

    private final Image[] frames;

    public Sprite(String path, int frameHeight) throws IOException {
        val resourceInputStream = JavaPlugin.getPlugin(Manhunt.class).getResource(path);

        if (resourceInputStream == null) {
            throw new RuntimeException("Compass sprite not found");
        }

        BufferedImage sprite = ImageIO.read(resourceInputStream);

        frameCount = sprite.getHeight() / frameHeight;
        Bukkit.getLogger().info("sprite frames: " + frameCount);
        frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = sprite.getSubimage(0, i * frameHeight, sprite.getWidth(), frameHeight);
        }
    }

    public Image getFrame(int number) {
        return frames[number];
    }
}
