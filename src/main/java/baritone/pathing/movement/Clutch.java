/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.pathing.movement;

import baritone.utils.pathing.MutableClutchResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class Clutch {
    private final boolean pickupable;
    private final ItemStack[] stack;

    protected Clutch(boolean pickupable, ItemStack... stack) {
        this.pickupable = pickupable;
        this.stack = stack;
    }
    public ItemStack[] getItemStack() {
        return stack;
    }
    public boolean isPickupable() {
        return pickupable;
    }
    public ItemStack getClutchingItem(CalculationContext context) {
        for (ItemStack item : stack) {
            if (Inventory.isHotbarSlot(context.getBaritone().getPlayerContext().player().getInventory().findSlotMatchingItem(item))) {
                return item;
            }
        }
        return null;
    }
    public abstract boolean compare(BlockState state);
    public abstract boolean clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result);
}
