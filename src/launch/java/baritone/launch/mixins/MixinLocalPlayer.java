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

package baritone.launch.mixins;

import baritone.utils.accessor.ILocalPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import java.util.UUID;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer implements ILocalPlayer {
    public MixinLocalPlayer(ClientLevel arg, GameProfile gameProfile) {
        super(arg, gameProfile);
    }

    @Unique
    public float baritone$getRun() {
        return this.run;
    }

    @Unique
    public void baritone$setRun(float run) {
        this.run = run;
    }

    @Unique
    public void baritone$setUUID(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    @Unique
    public boolean baritone$getJumping() {
        return this.jumping;
    }
}
