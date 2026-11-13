package sugar.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;

@Mixin(Holder.Reference.class)
public interface HolderReferenceAccessor<T> {
    @Accessor("owner")
    HolderOwner<T> sugar$getOwner();
}