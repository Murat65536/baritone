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
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import baritone.utils.pathing.MutableClutchResult;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class Clutch {
    private final ImmutableSet<ItemStack> stack;
    private final Block block;

    protected Clutch(ImmutableSet<ItemStack> stack, Block block) {
        this.stack = stack;
        this.block = block;
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
    public final Block getBlock() {
        return block;
    }
    public boolean isSolid(CalculationContext context) {
        return false;
    }
    public boolean compare(BlockState state) {
        return state.is(getBlock());
    }
    public boolean placeable(CalculationContext context, int x, int y, int z, BlockState block) {
        return MovementHelper.canPlaceAgainst(context.bsi, x, y, z, block);
    }
    public boolean clutchable(CalculationContext context) {
        return true;
    }
    public void clutch(IBaritone baritone, MovementState state, BetterBlockPos dest, MutableClutchResult result) {
        ClutchHelper.blockClutch(baritone, state, dest, result, true);
    }
    public boolean clutched(IPlayerContext ctx, BetterBlockPos dest) {
        return ctx.player().getBoundingBox().intersects(Vec3.atLowerCornerOf(dest), Vec3.atLowerCornerWithOffset(dest, 1, 1, 1));
    }
    public boolean finished(IPlayerContext ctx, MovementState state, MutableClutchResult result) {
        return true;
    }
    public float getFallDamageModifier() {
        return 0f;
    }
    public double getAdditionalCost() {
        return 0d;
    }
    public double getCostMultiplier() {
        return 1d;
    }
    public boolean slowsOnTopBlock() {
        return true;
    }
}
