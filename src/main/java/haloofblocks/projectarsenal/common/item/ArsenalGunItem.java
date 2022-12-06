package haloofblocks.projectarsenal.common.item;

import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.item.GunItem;
import haloofblocks.projectarsenal.common.FireMode;
import haloofblocks.projectarsenal.common.FireModes;
import haloofblocks.projectarsenal.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Autovw
 */
public class ArsenalGunItem extends GunItem
{
    private final boolean canColor;
    private final FireMode fireMode;
    private FireModes selectedFireMode;

    public ArsenalGunItem(@Nullable FireMode fireMode, Properties properties, boolean canColor)
    {
        super(properties);
        this.fireMode = fireMode;
        this.canColor = canColor;

        if (hasFireMode())
        {
            this.selectedFireMode = fireMode.getFireModes().get(0);
        }
    }

    public ArsenalGunItem(Properties properties, boolean canColor)
    {
        this(null, properties, canColor);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, world, tooltip, flag);

        int index = tooltip.size() - 1;

        Gun modifiedGun = getModifiedGun(stack);
        CompoundTag nbt = stack.getTag();

        if (nbt != null)
        {
            // Reload Amount
            if (Config.CLIENT.gunTooltipInfo.showReloadAmount.get())
            {
                tooltip.add(index++, Component.translatable("info.projectarsenal.reload_amount", ChatFormatting.WHITE.toString() + modifiedGun.getGeneral().getReloadAmount()).withStyle(ChatFormatting.GRAY));
            }

            // Fire Rate
            if (Config.CLIENT.gunTooltipInfo.showFireRate.get())
            {
                tooltip.add(index++, Component.translatable("info.projectarsenal.fire_rate", ChatFormatting.WHITE.toString() + modifiedGun.getGeneral().getRate()).withStyle(ChatFormatting.GRAY));
            }

            // Fire Mode
            if (Config.CLIENT.gunTooltipInfo.showFireMode.get())
            {
                String key = "info.projectarsenal.fire_mode";
                tooltip.add(index++, Component.translatable(key, fireModeComponent(stack)).withStyle(ChatFormatting.GRAY));
            }

            // Projectile Speed
            if (Config.CLIENT.gunTooltipInfo.showProjectileSpeed.get())
            {
                tooltip.add(index++, Component.translatable("info.projectarsenal.projectile_speed", ChatFormatting.WHITE.toString() + modifiedGun.getProjectile().getSpeed()).withStyle(ChatFormatting.GRAY));
            }

            // Projectile Spread
            if (Config.CLIENT.gunTooltipInfo.showProjectileSpread.get())
            {
                tooltip.add(index++, Component.translatable("info.projectarsenal.projectile_spread", ChatFormatting.WHITE.toString() + modifiedGun.getGeneral().getSpread()).withStyle(ChatFormatting.GRAY));
            }

            // Non-dyeable
            if (!canColor(stack) && Config.CLIENT.gunTooltipInfo.showNonDyeable.get())
            {
                tooltip.add(index++, Component.translatable("info.projectarsenal.non_dyeable").withStyle(ChatFormatting.RED));
            }
        }
    }

    public MutableComponent fireModeComponent(ItemStack stack)
    {
        String fireMode = null;

        if (hasFireMode())
        {
            switch (getSelectedFireMode())
            {
                case SEMI_AUTOMATIC -> fireMode = "semi_auto";
                case FULL_AUTOMATIC -> fireMode = "full_auto";
                case SAFETY -> fireMode = "safety";
            }
        }
        else
        {
            fireMode = getModifiedGun(stack).getGeneral().isAuto() ? "full_auto" : "semi_auto";
        }

        return Component.translatable("info.projectarsenal.fire_mode." + fireMode).withStyle(ChatFormatting.WHITE);
    }

    @Override
    public boolean canColor(ItemStack stack)
    {
        return this.canColor || Config.COMMON.experimental.forceGunDyeAbility.get();
    }

    @Override
    public boolean isFoil(ItemStack stack)
    {
        return super.isFoil(stack) && Config.CLIENT.enableGunEnchantmentGlint.get();
    }

    /**
     * @return If {@link FireMode} has been set for this gun
     */
    public boolean hasFireMode()
    {
        return this.fireMode != null;
    }

    public FireMode getFireMode()
    {
        return this.fireMode;
    }

    public FireModes getSelectedFireMode()
    {
        return this.selectedFireMode;
    }

    public void setSelectedFireMode(FireModes selectedFireMode)
    {
        this.selectedFireMode = selectedFireMode;
    }
}
