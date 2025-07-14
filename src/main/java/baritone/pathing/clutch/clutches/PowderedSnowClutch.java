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

import baritone.api.utils.IPlayerContext;
import baritone.pathing.clutch.ClutchHelper;
import baritone.pathing.clutch.Clutch;
import baritone.pathing.movement.MovementState;
import baritone.utils.pathing.MutableClutchResult;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class PowderedSnowClutch extends Clutch {
    public static final PowderedSnowClutch INSTANCE = new PowderedSnowClutch();

    private PowderedSnowClutch() {
        super(ImmutableSet.of(new ItemStack(Items.POWDER_SNOW_BUCKET)), 0f, false);
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.POWDER_SNOW);
    }
    @Override
    public boolean finished(IPlayerContext ctx, MovementState state, MutableClutchResult result) {
        return ClutchHelper.bucketPickup(state, ctx.player().getInventory());
    }
}