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

import baritone.pathing.movement.CalculationContext;
import baritone.pathing.clutch.Clutch;
import baritone.pathing.movement.MovementHelper;
import baritone.utils.pathing.MutableClutchResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public final class VineClutch extends Clutch {
    public static final Clutch INSTANCE = new VineClutch();

    private VineClutch() {
        super(Set.of(new ItemStack(Items.VINE)), Set.of(Property.NO_BOTTOM_BLOCK_SUPPORT));
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.VINE);
    }

    // Had to yoink this out of VineBlock since it was private
    private boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            if (VineBlock.isAcceptableNeighbour(level, pos, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BlockState lv3 = level.getBlockState(pos.above());
                return lv3.is(Blocks.VINE) && lv3.getValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction));
            }
        }
    }

    public boolean clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result) {
        ItemStack item = getClutchingItem(context);
        if (MovementHelper.canPlaceAgainst(context.bsi, x, y, z) &&
                (canSupportAtFace(context.bsi.access, new BlockPos(x - 1, y + 1, z), Direction.WEST) ||
                        canSupportAtFace(context.bsi.access, new BlockPos(x + 1, y + 1, z), Direction.EAST) ||
                        canSupportAtFace(context.bsi.access, new BlockPos(x, y + 1, z - 1), Direction.NORTH) ||
                        canSupportAtFace(context.bsi.access, new BlockPos(x, y + 1, z + 1), Direction.SOUTH)) &&
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
