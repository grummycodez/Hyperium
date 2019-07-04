/*
 *     Copyright (C) 2018  Hyperium <https://hyperium.cc/>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mixins;

import cc.hyperium.handlers.handlers.animation.cape.CapeHandler;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public abstract class MixinTextureManager {

    /**
     * @author Sk1er and Mojang
     * @reason Add lock on cape loading to prevent concurrent modification exception in texture manager
     */
    @Inject(method = "onResourceManagerReload", at = @At("HEAD"))
    private void lockCape(IResourceManager resourceManager, CallbackInfo ci) {
        CapeHandler.LOCK.lock();
    }

    @Inject(method = "onResourceManagerReload", at = @At("RETURN"))
    private void unlockCape(IResourceManager resourceManager, CallbackInfo ci) {
        CapeHandler.LOCK.unlock();
    }
}
