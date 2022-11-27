package haloofblocks.projectarsenal.core.registry;

import com.mrcrayfish.guns.common.GunModifiers;
import com.mrcrayfish.guns.item.BarrelItem;
import com.mrcrayfish.guns.item.UnderBarrelItem;
import com.mrcrayfish.guns.item.attachment.impl.Barrel;
import com.mrcrayfish.guns.item.attachment.impl.UnderBarrel;
import haloofblocks.projectarsenal.ProjectArsenal;
import haloofblocks.projectarsenal.ProjectArsenalTab;
import haloofblocks.projectarsenal.common.item.ArsenalGunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Autovw
 */
public class ArsenalItems
{
    /**
     * The {@link DeferredRegister} we use for registering items.
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProjectArsenal.MOD_ID);

    // ===== GUNS ===== //
    public static final RegistryObject<Item> AA_TWELVE = registerGun("aa12", true);
    public static final RegistryObject<Item> AUTO_NINE = registerGun("auto9", false);
    public static final RegistryObject<Item> DESERT_EAGLE = registerGun("desert_eagle", false);
    public static final RegistryObject<Item> DP_TWENTY_SEVEN = registerGun("dp27", false);
    public static final RegistryObject<Item> GOLDEN_HAWK = registerGun("golden_hawk", false);
    public static final RegistryObject<Item> M_TWO_FOUR_NINE = registerGun("m249", false);
    public static final RegistryObject<Item> MAXIM_NINE = registerGun("maxim9", false);
    public static final RegistryObject<Item> MOSSOU = registerGun("mossou", false);
    public static final RegistryObject<Item> MP_FIVE_A_FOUR = registerGun("mp5a4", false);
    public static final RegistryObject<Item> VAL = registerGun("val", true);
    public static final RegistryObject<Item> SHRIKE = registerGun("shrike", true);
    public static final RegistryObject<Item> GLOCK_TWENTY_ONE = registerGun("glock21", true);
    public static final RegistryObject<Item> P_TWO_FIFTY = registerGun("p250", true);
    public static final RegistryObject<Item> MARK_XIX = registerGun("markxix", false);
    public static final RegistryObject<Item> PRISMATIC = registerGun("prismatic", false);
    public static final RegistryObject<Item> M_NINETEEN_ELEVEN = registerGun("m1911", false);
    public static final RegistryObject<Item> CZ_SEVEN_FIVE = registerGun("cz75", true);
    public static final RegistryObject<Item> VECTOR = registerGun("vector", true);
    public static final RegistryObject<Item> M_NINE = registerGun("m9", false);
    public static final RegistryObject<Item> SCARH = registerGun("scarh", true);
    public static final RegistryObject<Item> DESERT_EAGLE_CLASSIC = registerGun("desert_eagle_classic", false);
    public static final RegistryObject<Item> P_NINETY = registerGun("p90", true);

    // ===== ATTACHMENTS ===== //
    public static final RegistryObject<Item> SPECIAL_MUZZLE_BREAK = registerBarrel("special_muzzle_break", Barrel.create(6.0f, GunModifiers.REDUCED_RECOIL), false);
    public static final RegistryObject<Item> R_TWO_SUPPRESSOR = registerBarrel("r2_suppressor", Barrel.create(13.0f, GunModifiers.REDUCED_RECOIL, GunModifiers.SILENCED), false);

    public static final RegistryObject<Item> SKELETON_GRIP = registerUnderBarrel("skeletongrip", UnderBarrel.create(GunModifiers.LIGHT_RECOIL), false);
    public static final RegistryObject<Item> VERTICAL_GRIP = registerUnderBarrel("verticalgrip", UnderBarrel.create(GunModifiers.LIGHT_RECOIL), false);

    // ===== AMMUNITION ===== //

    /**
     * Helper method for registering guns
     *
     * @param name The in-game name of the gun
     * @param canColor If the gun can be colored or not
     * @return {@link RegistryObject} for the gun item
     */
    private static RegistryObject<Item> registerGun(String name, boolean canColor)
    {
        return ITEMS.register(name, () -> new ArsenalGunItem(new Item.Properties().stacksTo(1).tab(ProjectArsenalTab.TAB), canColor));
    }

    /**
     * Helper method for registering barrel attachments
     *
     * @param name The in-game name of the barrel attachment
     * @param barrel The {@link Barrel} properties
     * @param canColor If the attachment can be colored or not
     * @return {@link RegistryObject} for the barrel attachment item
     */
    private static RegistryObject<Item> registerBarrel(String name, Barrel barrel, boolean canColor)
    {
        return ITEMS.register(name, () -> new BarrelItem(barrel, new Item.Properties().stacksTo(1).tab(ProjectArsenalTab.TAB), canColor));
    }

    /**
     * Helper method for registering under barrel attachments
     *
     * @param name The in-game name of the under barrel attachment
     * @param underBarrel The {@link UnderBarrel} properties
     * @param canColor If the attachment can be colored or not
     * @return {@link RegistryObject} for the under barrel attachment item
     */
    private static RegistryObject<Item> registerUnderBarrel(String name, UnderBarrel underBarrel, boolean canColor)
    {
        return ITEMS.register(name, () -> new UnderBarrelItem(underBarrel, new Item.Properties().stacksTo(1).tab(ProjectArsenalTab.TAB), canColor));
    }
}
