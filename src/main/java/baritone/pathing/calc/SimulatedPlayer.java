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

package baritone.pathing.calc;

import baritone.utils.accessor.ILivingEntity;
import baritone.utils.accessor.IPlayer;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.client.player.Input;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;

public class SimulatedPlayer {
    private final Player player;
    public SimulatedPlayerInput input;
    public Vec3 pos;
    public Vec3 velocity;
    public AABB boundingBox;
    private float yaw;
    private float pitch;
    private boolean sprinting;

    private float fallDistance;
    private int jumpingCooldown;
    private boolean isJumping;
    private boolean isFallFlying;
    private boolean onGround;
    private boolean horizontalCollision;
    private boolean verticalCollision;

    private boolean touchingWater;
    private boolean isSwimming;
    private boolean submergedInWater;
    private Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    private HashSet<TagKey<Fluid>> submergedFluidTag;

    private Level world;

    private int simulatedTicks = 0;

    public SimulatedPlayer(Player player, SimulatedPlayerInput input) {
        this.player = player;
        this.input = input;
        pos = player.position();
        velocity = player.getDeltaMovement();
        boundingBox = player.getBoundingBox();
        yaw = player.getYRot();
        pitch = player.getXRot();
        sprinting = player.isSprinting();
        fallDistance = player.fallDistance;
        jumpingCooldown = ((IPlayer) player).getJumpTriggerTime();
        isJumping = ((ILivingEntity) player).isJumping();
        isFallFlying = player.isFallFlying();
        onGround = player.isOnGround();
        horizontalCollision = player.horizontalCollision;
        verticalCollision = player.verticalCollision;

        touchingWater = player.isInWater();
        isSwimming = player.isSwimming();
        submergedInWater = player.isUnderWater();
    }

    public void tick() {
        if (jumpingCooldown > 0) {
            jumpingCooldown--;
        }

        isJumping = input.jumping;

        Vec3 currentVelocity = velocity;
        double h = currentVelocity.x;
        double i = currentVelocity.y;
        double j = currentVelocity.z;

        if (Math.abs(h) < 0.003) {
            h = 0.0;
        }
        if (Math.abs(i) < 0.003) {
            i = 0.0;
        }
        if (Math.abs(j) < 0.003) {
            j = 0.0;
        }

        velocity = new Vec3(h, i, j);

        if (isJumping) {

        }
    }

    public static class SimulatedPlayerInput extends Input {
        public final boolean forwards;
        public final boolean backwards;
        public final boolean left;
        public final boolean right;
        public final boolean jumping;
        public boolean sprinting;
        public boolean slowDown = false;

        public SimulatedPlayerInput(boolean forwards, boolean backwards, boolean left, boolean right, boolean jumping) {
            this.forwards = forwards;
            this.backwards = backwards;
            this.left = left;
            this.right = right;
            this.jumping = jumping;
        }

        public void update() {
            forwardImpulse = forwards != backwards ? forwards ? 1f : -1f : 0f;
            leftImpulse = left != right ? left ? 1f : -1f : 0f;

            if (slowDown) {
                // TODO Is the conversion really nessecary?
                forwardImpulse = (float) ((double) forwardImpulse * 0.3);
                leftImpulse = (float) ((double) leftImpulse * 0.3);
            }
        }
    }
}
