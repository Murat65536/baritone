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
import baritone.api.utils.input.Input;
import baritone.pathing.clutch.Clutch;
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.MovementHelper;
import baritone.pathing.movement.MovementState;
import baritone.utils.pathing.MutableClutchResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public final class ScaffoldingClutch extends Clutch {
    public static final ScaffoldingClutch INSTANCE = new ScaffoldingClutch();

    private ScaffoldingClutch() {
        super(Set.of(new ItemStack(Items.SCAFFOLDING)));
    }

    public boolean compare(BlockState state) {
        return state.is(Blocks.SCAFFOLDING);
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

    @Override
    public boolean finished(IPlayerContext ctx, MovementState state, MutableClutchResult result) {
        state.setInput(Input.SNEAK, true);
        return ctx.world().getBlockState(ctx.playerFeet()).is(Blocks.SCAFFOLDING);
    }
}
