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

package baritone.pathing.clutch.clutches;

import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.pathing.clutch.Clutch;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.pathing.MutableClutchResult;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class HayBaleClutch extends Clutch {
    public static final HayBaleClutch INSTANCE = new HayBaleClutch();

    private HayBaleClutch() {
        super(ImmutableSet.of(new ItemStack(Items.HAY_BLOCK)), 0.2f);
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.HAY_BLOCK);
    }
    public boolean clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result) {
        ItemStack item = getClutchingItem(context);
        if (MovementHelper.canPlaceAgainst(context.bsi, x, y, z) && item != null) {
            if (result != null) {
                result.clutch = INSTANCE;
                result.stack = item;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean clutched(IPlayerContext ctx, BetterBlockPos dest) {
        return super.clutched(ctx, dest.above());
    }
}
