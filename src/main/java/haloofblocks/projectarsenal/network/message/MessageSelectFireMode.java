package haloofblocks.projectarsenal.network.message;

import haloofblocks.projectarsenal.network.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author Autovw
 */
// TODO move to Framework's API
public class MessageSelectFireMode
{
    private final int nextFireMode;

    public MessageSelectFireMode(int nextFireMode)
    {
        this.nextFireMode = nextFireMode;
    }

    public static void encode(MessageSelectFireMode message, FriendlyByteBuf buffer)
    {
        buffer.writeInt(message.nextFireMode);
    }

    public static MessageSelectFireMode decode(FriendlyByteBuf buffer)
    {
        return new MessageSelectFireMode(buffer.readInt());
    }

    public static void handle(MessageSelectFireMode message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();

            if (player != null)
            {
                ServerPlayHandler.handleSelectFireMode(message.nextFireMode, player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
