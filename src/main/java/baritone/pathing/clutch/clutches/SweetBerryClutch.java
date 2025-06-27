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
import baritone.utils.pathing.MutableClutchResult;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public final class SweetBerryClutch extends Clutch {
    public static final SweetBerryClutch INSTANCE = new SweetBerryClutch();

    private SweetBerryClutch() {
        super(Set.of(new ItemStack(Items.SWEET_BERRIES)));
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.SWEET_BERRY_BUSH);
    }

    public boolean clutchable(CalculationContext context, int x, int y, int z, MutableClutchResult result) {
        ItemStack item = getClutchingItem(context);
        BlockState state = context.world.getBlockState(new BetterBlockPos(x, y, z));
        if ((state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND)) && item != null) {
            if (result != null) {
                result.clutch = INSTANCE;
                result.stack = item;
            }
            return true;
        }
        return false;
    }
}
