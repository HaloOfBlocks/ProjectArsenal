package haloofblocks.projectarsenal.common.container;

import com.mrcrayfish.guns.crafting.WorkbenchRecipes;
import haloofblocks.projectarsenal.common.blockentity.ArsenalWorkbenchBlockEntity;
import haloofblocks.projectarsenal.core.registry.ArsenalContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * @author Autovw
 */
public class ArsenalWorkbenchContainer extends Container
{
    private ArsenalWorkbenchBlockEntity workbench;
    private BlockPos pos;

    public ArsenalWorkbenchContainer(int windowId, IInventory playerInventory, ArsenalWorkbenchBlockEntity workbench)
    {
        super(ArsenalContainers.ARSENAL_WORKBENCH.get(), windowId);
        this.workbench = workbench;
        this.pos = workbench.getBlockPos();

        int offset = WorkbenchRecipes.isEmpty(workbench.getLevel()) ? 0 : 28;

        this.addSlot(new Slot(workbench, 0, 174, 18)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return stack.getItem() instanceof DyeItem;
            }

            @Override
            public int getMaxStackSize()
            {
                return 1;
            }
        });

        for (int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 102 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 160));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn)
    {
        return workbench.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();

            if (index == 0)
            {
                if (!this.moveItemStackTo(slotStack, 1, 36, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (slotStack.getItem() instanceof DyeItem)
                {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 28)
                {
                    if(!this.moveItemStackTo(slotStack, 28, 36, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index <= 36 && !this.moveItemStackTo(slotStack, 1, 28, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if (slotStack.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return stack;
    }

    public ArsenalWorkbenchBlockEntity getWorkbench()
    {
        return this.workbench;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }
}
