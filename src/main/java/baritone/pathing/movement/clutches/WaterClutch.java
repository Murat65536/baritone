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

package baritone.pathing.movement.clutches;

import baritone.Baritone;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Clutch;
import baritone.pathing.movement.MovementHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.WaterFluid;

public final class WaterClutch extends Clutch {
    public static final Clutch INSTANCE = new WaterClutch();

    private WaterClutch() {
        super(new ItemStack(Items.WATER_BUCKET), true);
    }
    public boolean compare(BlockState state) {
        return state.getFluidState().getType() instanceof WaterFluid;
    }
    public boolean clutchable(CalculationContext context, int x, int y, int z) {
        BlockState below = context.get(x, y, z);
        return Baritone.settings().allowWaterBucketFall.value &&
                Inventory.isHotbarSlot(context.getBaritone().getPlayerContext().player().getInventory().findSlotMatchingItem(getItemStack())) &&
                !(below.getBlock() instanceof SimpleWaterloggedBlock) &&
                MovementHelper.canPlaceAgainst(context.bsi, x, y, z, below) &&
                context.world.dimension() != Level.NETHER;
    }
}
