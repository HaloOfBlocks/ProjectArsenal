package haloofblocks.projectarsenal.core.registry;

import haloofblocks.projectarsenal.ProjectArsenal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Autovw
 */
public class ArsenalSounds
{
    /**
     * The {@link DeferredRegister} we use for registering sound events.
     */
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ProjectArsenal.MOD_ID);

    // ===== GUNFIRE SOUNDS ===== //
    public static final RegistryObject<SoundEvent> AA_TWELVE = register("item.aa12.fire");
    public static final RegistryObject<SoundEvent> AUTO_NINE = register("item.auto9.fire");
    public static final RegistryObject<SoundEvent> DESERT_EAGLE = register("item.deserteagle.fire");
    public static final RegistryObject<SoundEvent> DP_TWENTY_SEVEN = register("item.dp27.fire");
    public static final RegistryObject<SoundEvent> GOLDEN_HAWK = register("item.goldenhawk.fire");
    public static final RegistryObject<SoundEvent> M_TWO_FOUR_NINE = register("item.m249.fire");
    public static final RegistryObject<SoundEvent> MOSSOU = register("item.mossou.fire");
    public static final RegistryObject<SoundEvent> MP_FIVE_A_FOUR = register("item.mp5a4.fire");
    public static final RegistryObject<SoundEvent> INTEGRAL_SUPPRESSION = register("item.integral_suppression.fire");
    public static final RegistryObject<SoundEvent> SHRIKE_FIRE = register("item.shrike.fire");
    public static final RegistryObject<SoundEvent> SHRIKE_SUPPRESSED = register("item.shrike.suppressed");
    public static final RegistryObject<SoundEvent> P_TWO_FIFTY = register("item.p250.fire");
    public static final RegistryObject<SoundEvent> MARKXIX = register("item.markxix.fire");
    public static final RegistryObject<SoundEvent> M_NINETEEN_ELEVEN = register("item.m1911.fire");
    public static final RegistryObject<SoundEvent> CZ_SEVEN_FIVE = register("item.cz75.fire");
    public static final RegistryObject<SoundEvent> PRISMATIC = register("item.prismatic.fire");
    public static final RegistryObject<SoundEvent> VECTOR = register("item.vector.fire");
    public static final RegistryObject<SoundEvent> VECTOR_SUPPRESSED = register("item.vector.suppressed");
    public static final RegistryObject<SoundEvent> SCARH = register("item.scarh.fire");
    public static final RegistryObject<SoundEvent> P_NINETY = register("item.p90.fire");

    // ===== MISCELLANEOUS SOUNDS ===== //
    public static final RegistryObject<SoundEvent> SWITCH_FIRE_MODE = register("item.switch_fire_mode");

    /**
     * Helper method used to register sounds
     * @param name The string name of the fire sound
     */
    private static RegistryObject<SoundEvent> register(String name)
    {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(ProjectArsenal.MOD_ID, name)));
    }
}
