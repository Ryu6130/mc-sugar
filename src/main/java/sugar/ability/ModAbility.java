package sugar.ability;

public class ModAbility {
    public static void clientInitialize() {
        JetManager.clientInitialize();
        MultiAttackManager.clientInitialize();
        WindAttackManager.clientInitialize();
    }

    public static void initialize() {
        JetManager.initialize();
        MultiAttackManager.initialize();
        WindAttackManager.initialize();
    }
}
