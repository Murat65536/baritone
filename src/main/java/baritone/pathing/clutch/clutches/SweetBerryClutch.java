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
import baritone.pathing.clutch.Clutch;
import baritone.pathing.movement.CalculationContext;
import com.google.common.collect.ImmutableSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class SweetBerryClutch extends Clutch {
    public static final SweetBerryClutch INSTANCE = new SweetBerryClutch();

    private SweetBerryClutch() {
        super(ImmutableSet.of(new ItemStack(Items.SWEET_BERRIES)), Blocks.SWEET_BERRY_BUSH);
    }
    @Override
    public boolean placeable(CalculationContext context, int x, int y, int z, BlockState block) {
        return block.is(BlockTags.DIRT) || block.is(Blocks.FARMLAND);
    }
    @Override
    public double getCostMultiplier() {
        return 0.75d;
    }
}
