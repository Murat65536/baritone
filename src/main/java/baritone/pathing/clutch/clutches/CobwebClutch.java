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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class CobwebClutch extends Clutch {
    public static final CobwebClutch INSTANCE = new CobwebClutch();

    private CobwebClutch() {}
    @Override
    public boolean compare(BlockState state) {
        return state.is(Blocks.COBWEB);
    }
    @Override
    public boolean acceptedItem(Item item) {
        return item.equals(Items.COBWEB);
    }
    @Override
    public boolean clutched(IPlayerContext ctx, BetterBlockPos dest) {
        return super.clutched(ctx, dest.above());
    }
    @Override
    public double getCostMultiplier() {
        return 0.05d;
    }
}
