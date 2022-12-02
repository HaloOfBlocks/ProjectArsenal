package haloofblocks.projectarsenal.common;

import com.mrcrayfish.guns.common.Gun;

/**
 * @author Autovw
 */
public enum FireModes
{
    /**
     * Semi-Automatic fire mode.
     * Equivalent of {@link Gun.General#isAuto()} when false.
     */
    SEMI_AUTOMATIC,
    /**
     * Full-Automatic fire mode.
     * Equivalent of {@link Gun.General#isAuto()} when true.
     */
    FULL_AUTOMATIC,
    /**
     * Safety fire mode that will prevent the weapon from firing a round.
     */
    SAFETY
}
