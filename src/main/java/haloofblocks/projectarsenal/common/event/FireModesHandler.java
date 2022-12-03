package haloofblocks.projectarsenal.common.event;

import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.event.GunFireEvent;
import haloofblocks.projectarsenal.ProjectArsenal;
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
    @SubscribeEvent
    public static void onPreGunFireEvent(GunFireEvent.Pre event)
    {
        ItemStack stack = event.getStack();

        if (!(stack.getItem() instanceof ArsenalGunItem gunItem))
            return;

        if (!gunItem.hasFireMode())
            return;

        if (gunItem.getSelectedFireMode().equals(FireModes.SAFETY))
        {
            // cancel all gun firing when safety mode is enabled
            event.setCanceled(true);
        }

        // modify "auto" field when fire mode switches between full-auto and semi-auto
        setAuto(stack, !gunItem.getSelectedFireMode().equals(FireModes.SEMI_AUTOMATIC));
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
        if (generalTag.getBoolean("Auto") == auto)
            return;

        if (generalTag.contains("Auto", Tag.TAG_ANY_NUMERIC))
            generalTag.remove("Auto");

        // modify "auto" tag
        generalTag.putBoolean("Auto", auto);
    }
}
