package haloofblocks.projectarsenal.network;

import haloofblocks.projectarsenal.ProjectArsenal;
import haloofblocks.projectarsenal.network.message.MessageSelectFireMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * @author Autovw
 */
// TODO move to Framework's API
public class PacketHandler
{
    private static String VERSION = "1";
    private static SimpleChannel PLAY_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ProjectArsenal.MOD_ID, "play"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void setup()
    {
        int index = 0;
        PLAY_CHANNEL.messageBuilder(MessageSelectFireMode.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(MessageSelectFireMode::encode).decoder(MessageSelectFireMode::decode).consumerMainThread(MessageSelectFireMode::handle).add();
    }

    public static SimpleChannel getPlayChannel()
    {
        return PLAY_CHANNEL;
    }
}
