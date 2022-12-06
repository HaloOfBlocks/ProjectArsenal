package haloofblocks.projectarsenal.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.util.GunEnchantmentHelper;
import haloofblocks.projectarsenal.common.item.ArsenalGunItem;
import haloofblocks.projectarsenal.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

/**
 * @author Autovw
 */
public class HudOverlay
{
    public static void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if (!Config.CLIENT.hudOverlay.showHudOverlay.get())
            return;

        Minecraft mc = Minecraft.getInstance();

        if (!mc.isWindowActive() || mc.options.hideGui)
            return;

        if (mc.screen != null || mc.isPaused())
            return;

        Player player = mc.player;
        if (player == null)
            return;

        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        Gun modifiedGun = gunItem.getModifiedGun(stack);
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        int ammoCapacity = GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun);
        int ammoCount = tag.getInt("AmmoCount");

        ChatFormatting ammoCountColor = ChatFormatting.GREEN;

        if (ammoCount <= ammoCapacity / 2)
        {
            ammoCountColor = ChatFormatting.YELLOW;
        }

        if (ammoCount <= ammoCapacity / 4)
        {
            ammoCountColor = ChatFormatting.RED;
        }

        Component ammoCapacityComponent = Component.literal(ammoCountColor.toString() + ammoCount + ChatFormatting.RESET.toString() + "/" + ammoCapacity).withStyle(ChatFormatting.WHITE);
        Component ammoComponent = Component.translatable("info.cgm.ammo",  ammoCapacityComponent).withStyle(ChatFormatting.GRAY);

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        float xPos = width * 0.72f;
        float yPos = height * 0.87f;

        if (Config.CLIENT.hudOverlay.overlayPosition.get().equals(OverlayPositions.RIGHT_TOP))
            yPos = height - yPos;

        // ammo counter
        mc.font.drawShadow(poseStack, ammoComponent, xPos, yPos - 8, ChatFormatting.BLACK.getId());

        // fire mode
        if (gunItem.hasFireMode())
        {
            Component component = Component.translatable("info.projectarsenal.fire_mode", gunItem.fireModeComponent(stack)).withStyle(ChatFormatting.GRAY);
            mc.font.drawShadow(poseStack, component, xPos, yPos + 8, ChatFormatting.BLACK.getId());
        }

        poseStack.popPose();
    }

    public enum OverlayPositions
    {
        RIGHT_BOTTOM,
        RIGHT_TOP
    }
}
