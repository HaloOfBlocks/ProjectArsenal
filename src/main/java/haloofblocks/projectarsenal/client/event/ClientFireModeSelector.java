package haloofblocks.projectarsenal.client.event;

import haloofblocks.projectarsenal.ProjectArsenal;
import haloofblocks.projectarsenal.client.KeyBindings;
import haloofblocks.projectarsenal.common.FireMode;
import haloofblocks.projectarsenal.common.item.ArsenalGunItem;
import haloofblocks.projectarsenal.core.registry.ArsenalSounds;
import haloofblocks.projectarsenal.network.PacketHandler;
import haloofblocks.projectarsenal.network.message.MessageSelectFireMode;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author Autovw
 */
@Mod.EventBusSubscriber(modid = ProjectArsenal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientFireModeSelector
{
    private static AbstractClientPlayer player;
    private static ItemStack stack;

    @SubscribeEvent
    public static void onKeyInputEvent(InputEvent.Key event)
    {
        if (player == null)
            return;

        if (stack == null)
            return;

        // Check if the key to change the fire mode is pressed
        if (KeyBindings.KEY_SELECT_FIRE_MODE.consumeClick() && event.getAction() == GLFW.GLFW_PRESS)
        {
            if (!(stack.getItem() instanceof ArsenalGunItem gunItem) || !gunItem.hasFireMode())
                return;

            FireMode fireMode = gunItem.getFireMode();
            int size = fireMode.getFireModes().size();
            int index = fireMode.getFireModes().indexOf(gunItem.getSelectedFireMode());
            int next = index >= size - 1 ? 0 : index + 1;

            // Change the fire mode through client to server message
            PacketHandler.getPlayChannel().sendToServer(new MessageSelectFireMode(next));

            // Play sound effect when fire mode is switched
            player.playSound(ArsenalSounds.SWITCH_FIRE_MODE.get());
        }
    }

    @SubscribeEvent
    public static void onRenderArmEvent(RenderArmEvent event)
    {
        player = event.getPlayer();
        stack = event.getPlayer().getMainHandItem();
    }
}
