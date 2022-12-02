package haloofblocks.projectarsenal.client;

import net.minecraftforge.common.MinecraftForge;

/**
 * @author Autovw
 */
public class ClientHandler
{
    public static void setup()
    {
        MinecraftForge.EVENT_BUS.addListener(KeyBindings::register);
    }
}
