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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public final class VineClutch extends Clutch {
    public static final Clutch INSTANCE = new VineClutch();

    private VineClutch() {
        super(false, new ItemStack(Items.VINE), new ItemStack(Items.VINE));
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.VINE);
    }

    // Had to yoink this out of VineBlock since it was private lol
    private boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            BlockPos lv = pos.relative(direction);
            if (VineBlock.isAcceptableNeighbour(level, lv, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                // If there's a vine block above it, you can still place more vine blocks below it. IDK why this would be useful for clutching since you can just land on the top vine block.
                // This timing is going to be so tight XD.
                // Imma need to align the cursor to the middle of the AABB or something.
                BooleanProperty lv2 = VineBlock.PROPERTY_BY_DIRECTION.get(direction);
                BlockState lv3 = level.getBlockState(pos.above());
                return lv3.is(Blocks.VINE) && lv3.getValue(lv2);
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
