package haloofblocks.projectarsenal.common.event;

import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.event.GunFireEvent;
import haloofblocks.projectarsenal.ProjectArsenal;
import haloofblocks.projectarsenal.common.FireModeSelector;
import haloofblocks.projectarsenal.common.FireModes;
import haloofblocks.projectarsenal.common.item.ArsenalGunItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Autovw
 */
@Mod.EventBusSubscriber(modid = ProjectArsenal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FireModesHandler
{
    private static int burstCount = 0;

    @SubscribeEvent
    public static void onPreGunFireEvent(GunFireEvent.Pre event)
    {
        ItemStack stack = event.getStack();

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        if (!gunItem.hasFireModeSelector())
            return;

        if (gunItem.getSelectedFireMode(stack).equals(FireModes.SAFETY))
        {
            // cancel all gun firing when safety mode is enabled
            event.setCanceled(true);
        }

        if (gunItem.getSelectedFireMode(stack).equals(FireModes.BURST))
        {
            // questionable way to check if player is in singleplayer or multiplayer.
            // I couldn't find a more reliable way to check this.
            // GunFireEvent is fired on both sides which leads to different outcomes depending on the environment.
            int serverModifier = event.getEntity().getServer() == null ? 2 : 1;
            if ((burstCount / serverModifier) >= gunItem.getFireModeSelector().getBurstCount())
            {
                applyBurst(stack);
            }

            if (gunItem.isBurst(stack))
            {
                // Cancel gun firing if Burst tag has been set
                event.setCanceled(true);
            }
        }

        // modify "auto" field when fire mode switches between full-auto and semi-auto
        setAuto(stack, !gunItem.getSelectedFireMode(stack).equals(FireModes.SEMI_AUTOMATIC));
    }

    @SubscribeEvent
    public static void onPostGunFireEvent(GunFireEvent.Post event)
    {
        if (event.isClient())
            return;

        ItemStack stack = event.getStack();

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        if (!gunItem.hasFireModeSelector())
            return;

        if (gunItem.getSelectedFireMode(stack).equals(FireModes.BURST))
        {
            if (burstCount <= gunItem.getFireModeSelector().getBurstCount())
            {
                burstCount++;
            }
        }
    }

    /**
     * Allow modification of the "auto": boolean field found in gun data.
     * @param stack Gun stack
     * @param auto If gun should fire automatically or not
     */
    public static void setAuto(ItemStack stack, boolean auto)
    {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        // return if fire mode is already properly set in gun data
        Gun modifiedGun = gunItem.getModifiedGun(stack);
        if (modifiedGun.getGeneral().isAuto() == auto)
            return;

        CompoundTag gunTag = tag.getCompound("Gun");
        if (!tag.contains("Gun", Tag.TAG_COMPOUND))
            tag.put("Gun", gunTag);

        CompoundTag generalTag = gunTag.getCompound("General");
        if (!gunTag.contains("General", Tag.TAG_COMPOUND))
            gunTag.put("General", generalTag);

        // return if fire mode tag is already properly set
        if (generalTag.contains("Auto") && generalTag.getBoolean("Auto") == auto)
            return;

        if (generalTag.contains("Auto", Tag.TAG_ANY_NUMERIC))
            generalTag.remove("Auto");

        // modify "auto" tag
        generalTag.putBoolean("Auto", auto);
    }

    /**
     * When called, selects the next available fire mode and adjusts the <code>FireMode</code> tag accordingly.
     * @param stack The weapon's item stack
     */
    public static void nextFireMode(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem) || !gunItem.hasFireModeSelector())
            return;

        FireModeSelector fireModeSelector = gunItem.getFireModeSelector();
        FireModes selectedFireMode = gunItem.getSelectedFireMode(stack);
        int index = fireModeSelector.getFireModes().indexOf(selectedFireMode); // index of current fire mode
        int next = index + 1; // index of the next fire mode
        int size = fireModeSelector.getFireModes().size();

        // reset back to initial fire mode if there are no more fire modes available
        if (index >= size - 1)
            next = fireModeSelector.getFireModes().indexOf(gunItem.getInitialFireMode());

        if (tag.contains("FireMode", Tag.TAG_INT))
            tag.remove("FireMode");

        // Modify "FireMode" tag
        tag.putInt("FireMode", next);
    }

    /**
     * Applies <code>Burst</code> tag to the weapon that will prevent the weapon from firing.
     * @param stack The weapon stack
     */
    private static void applyBurst(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem) || !gunItem.hasFireModeSelector())
            return;

        if (!gunItem.getSelectedFireMode(stack).equals(FireModes.BURST))
            return;

        // Return early if we already have the tag set
        if (gunItem.isBurst(stack))
            return;

        // Set tag that will prevent the weapon from firing
        tag.putBoolean("Burst", true);
    }

    /**
     * Helper method for resetting the weapon's burst counter
     * @param stack The weapon stack
     */
    public static void resetBurstCount(ItemStack stack) {
        CompoundTag tag = stack.getTag();

        if (tag == null)
            return;

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem) || !gunItem.hasFireModeSelector())
            return;

        burstCount = 0; // Reset server-side burst counter

        if (gunItem.isBurst(stack))
        {
            tag.remove("Burst"); // Remove nbt tag that prevents the weapon from firing
        }
    }
}
