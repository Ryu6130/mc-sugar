package sugar.menu;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import sugar.SugarMod;

public class ModMenuTypes {
	public static final MenuType<DesugarerMenu> DESUGARER =
		register("desugarer", DesugarerMenu::new);
	public static final MenuType<SugarStorageMenu> SUGAR_STORAGE =
		register("sugar_storage", new ExtendedMenuType<>(
			SugarStorageMenu::new,
			ByteBufCodecs.VAR_INT
		));

	public static <T extends AbstractContainerMenu> MenuType<T> register(
        String name,
        MenuType.MenuSupplier<T> constructor
	) {
		return Registry.register(
			BuiltInRegistries.MENU,
			SugarMod.id(name),
			new MenuType<>(constructor, FeatureFlagSet.of())
		);
	}

    public static <T extends AbstractContainerMenu, D> MenuType<T> register(
        String name,
        ExtendedMenuType<T, D> type
    ) {
        return Registry.register(
            BuiltInRegistries.MENU,
            SugarMod.id(name),
            type
        );
    }
	
    public static void initialize() {}
}