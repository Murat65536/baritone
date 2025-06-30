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

package baritone.pathing.clutch;

import baritone.Baritone;
import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementState;
import baritone.utils.pathing.MutableClutchResult;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class Clutch {
    private final ImmutableSet<ItemStack> stack;
    private final float fallDamageModifier;

    protected Clutch(ImmutableSet<ItemStack> stack, float fallDamageModifier) {
        this.stack = stack;
        this.fallDamageModifier = fallDamageModifier;
    }
    public final ItemStack getClutchingItem(CalculationContext context) {
        for (ItemStack item : stack) {
            int slot = context.getBaritone().getPlayerContext().player().getInventory().findSlotMatchingItem(item);
            if (Inventory.isHotbarSlot(slot) || (Baritone.settings().allowInventory.value && slot != -1)) {
                return item;
            }
        }
        return null;
    }
    public final float getFallDamageModifier() {
        return fallDamageModifier;
    }
    public abstract boolean compare(BlockState state);
    public abstract ItemStack clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result);
    public void clutch(IBaritone baritone, MovementState state, BetterBlockPos dest, MutableClutchResult result) {
        ClutchHelper.blockClutch(baritone, state, dest, result, true);
    }
    public boolean clutched(IPlayerContext ctx, BetterBlockPos dest) {
        return ctx.player().getBoundingBox().intersects(Vec3.atLowerCornerOf(dest), Vec3.atLowerCornerWithOffset(dest, 1, 1, 1));
    }
    public boolean finished(IPlayerContext ctx, MovementState state, MutableClutchResult result) {
        return true;
    }
}
