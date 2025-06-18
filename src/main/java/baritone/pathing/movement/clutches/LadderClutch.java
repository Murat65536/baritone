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

import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Clutch;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.pathing.MutableClutchResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class LadderClutch extends Clutch {
    public static final Clutch INSTANCE = new LadderClutch();

    private LadderClutch() {
        super(false, new ItemStack(Items.LADDER));
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.LADDER);
    }
    public boolean clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result) {
        ItemStack item = getClutchingItem(context);
        if (MovementHelper.canPlaceAgainst(context.bsi, x, y, z) &&
                (context.get(x - 1, y + 1, z).isFaceSturdy(context.bsi.access, new BlockPos(x - 1, y + 1, z), Direction.EAST) ||
                        context.get(x + 1, y + 1, z).isFaceSturdy(context.bsi.access, new BlockPos(x + 1, y + 1, z), Direction.WEST) ||
                        context.get(x, y + 1, z - 1).isFaceSturdy(context.bsi.access, new BlockPos(x, y + 1, z - 1), Direction.SOUTH) ||
                        context.get(x, y + 1, z + 1).isFaceSturdy(context.bsi.access, new BlockPos(x, y + 1, z + 1), Direction.NORTH)) &&
            item != null) {
            if (result != null) {
                result.clutch = INSTANCE;
                result.stack = item;
            }
            return true;
        }
        return false;
    }
}
