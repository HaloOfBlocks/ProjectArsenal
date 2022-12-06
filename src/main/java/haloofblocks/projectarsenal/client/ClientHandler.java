package haloofblocks.projectarsenal.client;

import haloofblocks.projectarsenal.client.event.HudOverlay;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author Autovw
 */
public class ClientHandler
{
    public static void setup()
    {
        MinecraftForge.EVENT_BUS.addListener(KeyBindings::register);
        MinecraftForge.EVENT_BUS.addListener(HudOverlay::onRenderTick);
    }
}
