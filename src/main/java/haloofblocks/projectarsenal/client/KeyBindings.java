package haloofblocks.projectarsenal.client;

import haloofblocks.projectarsenal.ProjectArsenal;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

/**
 * @author Autovw
 */
public class KeyBindings
{
    public static final KeyMapping KEY_SELECT_FIRE_MODE = new KeyMapping("key." + ProjectArsenal.MOD_ID + ".select_fire_mode", GLFW.GLFW_KEY_V, "key.categories." + ProjectArsenal.MOD_ID);

    public static void register(RegisterKeyMappingsEvent event)
    {
        event.register(KEY_SELECT_FIRE_MODE);
    }
}
