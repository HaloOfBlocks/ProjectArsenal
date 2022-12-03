package haloofblocks.projectarsenal.network;

import com.mrcrayfish.guns.common.Gun;
import haloofblocks.projectarsenal.common.FireMode;
import haloofblocks.projectarsenal.common.item.ArsenalGunItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * @author Autovw
 */
public class ServerPlayHandler
{
    public static void handleSelectFireMode(int nextFireMode, ServerPlayer player)
    {
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        FireMode fireMode = gunItem.getFireMode();

        // Change selected fire mode
        gunItem.setSelectedFireMode(fireMode.getFireModes().get(nextFireMode));
    }
}
