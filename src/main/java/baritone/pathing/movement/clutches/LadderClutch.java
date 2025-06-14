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
import baritone.pathing.movement.CalculationContext;
import baritone.pathing.movement.Clutch;
import baritone.pathing.movement.MovementHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class LadderClutch extends Clutch {
    public static final Clutch INSTANCE = new LadderClutch();

    private LadderClutch() {
        super(new ItemStack(Items.LADDER), false);
    }
    public boolean compare(BlockState state) {
        return state.is(Blocks.LADDER);
    }
    public boolean clutchable(CalculationContext context, int x, int y, int z) {
        return Baritone.settings().allowLadderFall.value &&
                Inventory.isHotbarSlot(context.getBaritone().getPlayerContext().player().getInventory().findSlotMatchingItem(getItemStack())) &&
                MovementHelper.canPlaceAgainst(context.bsi, x, y, z) &&
                (MovementHelper.canPlaceAgainst(context.bsi, x - 1, y + 1, z) ||
                        MovementHelper.canPlaceAgainst(context.bsi, x + 1, y + 1, z) ||
                        MovementHelper.canPlaceAgainst(context.bsi, x, y + 1, z - 1) ||
                        MovementHelper.canPlaceAgainst(context.bsi, x, y + 1, z + 1));
    }
}
