package haloofblocks.projectarsenal.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author Autovw
 */
public class Config
{
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static
    {
        final Pair<Client, ForgeConfigSpec> clientConfigPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientConfigPair.getRight();
        CLIENT = clientConfigPair.getLeft();

        final Pair<Common, ForgeConfigSpec> commonConfigPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonConfigPair.getRight();
        COMMON = commonConfigPair.getLeft();
    }

    /**
     * Client config for Project Arsenal
     */
    public static class Client
    {
        public final GunTooltipInfo gunTooltipInfo;
        public final HudOverlay hudOverlay;
        public final ForgeConfigSpec.BooleanValue enableGunEnchantmentGlint;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");
            {
                this.gunTooltipInfo = new GunTooltipInfo(builder);

                this.hudOverlay = new HudOverlay(builder);

                this.enableGunEnchantmentGlint = builder
                        .comment("If true, renders enchanted guns from Project Arsenal with the purple enchantment glint. True by default.")
                        .define("enableGunEnchantmentGlint", true);
            }
            builder.pop();
        }

        public static class GunTooltipInfo
        {
            public final ForgeConfigSpec.BooleanValue showReloadAmount;
            public final ForgeConfigSpec.BooleanValue showFireRate;
            public final ForgeConfigSpec.BooleanValue showFireMode;
            public final ForgeConfigSpec.BooleanValue showProjectileSpeed;
            public final ForgeConfigSpec.BooleanValue showProjectileSpread;
            public final ForgeConfigSpec.BooleanValue showNonDyeable;

            public GunTooltipInfo(ForgeConfigSpec.Builder builder)
            {
                builder.push("gun_tooltip_info");
                {
                    this.showReloadAmount = builder
                            .comment("If true, shows Reload Amount of the gun on the tooltip. True by default.")
                            .define("showReloadAmount", true);
                    this.showFireRate = builder
                            .comment("If true, shows Fire Rate of the gun on the tooltip. True by default.")
                            .define("showFireRate", true);
                    this.showFireMode = builder
                            .comment("If true, shows the gun's Fire Mode on the tooltip. True by default.")
                            .define("showFireMode", true);
                    this.showProjectileSpeed = builder
                            .comment("If true, shows the Projectile Speed of projectiles fired from the gun on the tooltip. False by default.")
                            .define("showProjectileSpeed", false);
                    this.showProjectileSpread = builder
                            .comment("If true, shows the Projectile Spread of projectiles fired from the gun on the tooltip. False by default.")
                            .define("showProjectileSpread", false);
                    this.showNonDyeable = builder
                            .comment("If true, shows a tooltip on guns that are not dyeable. True by default.")
                            .define("showNonDyeable", true);
                }
                builder.pop();
            }
        }

        public static class HudOverlay
        {
            public final ForgeConfigSpec.BooleanValue showHudOverlay;
            public final ForgeConfigSpec.EnumValue<haloofblocks.projectarsenal.client.event.HudOverlay.OverlayPositions> overlayPosition;

            public HudOverlay(ForgeConfigSpec.Builder builder)
            {
                builder.push("hud_overlay");
                {
                    this.showHudOverlay = builder
                            .comment("If true, shows HUD overlay with ammo counter and selected fire mode. True by default.")
                            .define("showHudOverlay", true);
                    this.overlayPosition = builder
                            .comment("Position of the HUD overlay on the screen. Currently placements of the overlay are only supported on the right side of the screen.")
                            .defineEnum("overlayPosition", haloofblocks.projectarsenal.client.event.HudOverlay.OverlayPositions.RIGHT_BOTTOM);
                }
                builder.pop();
            }
        }
    }

    /**
     * Common config for Project Arsenal
     */
    public static class Common
    {
        public final Experimental experimental;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            {
                this.experimental = new Experimental(builder);
            }
            builder.pop();
        }

        public static class Experimental
        {
            public final ForgeConfigSpec.BooleanValue forceGunDyeAbility;

            public Experimental(ForgeConfigSpec.Builder builder)
            {
                builder.push("experimental");
                {
                    this.forceGunDyeAbility = builder
                            .comment("If true, forces guns that have the dyeing behaviour turned off by default to be dye-able again. This option could be useful to modpack and/or resource pack developers. False by default.")
                            .define("forceGunDyeAbility", false);
                }
                builder.pop();
            }
        }
    }
}
