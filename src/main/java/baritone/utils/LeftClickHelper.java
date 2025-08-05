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

package baritone.utils;

import baritone.Baritone;
import baritone.api.utils.IPlayerContext;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.HitResult;

/**
 * @author Brady
 * @since 8/25/2018
 */
public final class LeftClickHelper {
    // base ticks between block breaks caused by tick logic
    private static final int BASE_BREAK_DELAY = 1;

    private final IPlayerContext ctx;
    private boolean isBreaking = false;
    private int breakDelayTimer = 0;
    private int leftClickTimer = 0;

    LeftClickHelper(IPlayerContext ctx) {
        this.ctx = ctx;
    }

    public void stopBreakingBlock() {
        isBreaking = false;
        ctx.minecraft().options.keyAttack.setDown(false);
    }

    public void tick(boolean isLeftClick) {
        HitResult trace = ctx.minecraft().hitResult;
        if (isLeftClick && trace != null) {
            switch (trace.getType()) {
                case ENTITY:
                    stopBreakingBlock();
                    if (leftClickTimer > 0) {
                        leftClickTimer--;
                    } else if (!Baritone.settings().timedAttacks.value || ctx.player().getAttackStrengthScale(0f) == 1f) {
                        leftClickTimer = Baritone.settings().leftClickSpeed.value;
                        KeyMapping.click(ctx.minecraft().options.keyAttack.getDefaultKey());
                    }
                    break;
                case BLOCK:
                    if (breakDelayTimer > 0) {
                        breakDelayTimer--;
                    } else {
                        if (!isBreaking) {
                            isBreaking = true;
                            KeyMapping.click(ctx.minecraft().options.keyAttack.getDefaultKey());
                        }
                        ctx.minecraft().options.keyAttack.setDown(true);
                        if (ctx.playerController().hasBrokenBlock()) {
                            breakDelayTimer = Baritone.settings().blockBreakSpeed.value - BASE_BREAK_DELAY;
                        }
                    }
                    break;
                default:
                    stopBreakingBlock();
                    break;
            }
        } else {
            stopBreakingBlock();
        }
    }
}
