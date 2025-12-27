package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

@FunctionalInterface
public interface RollbackFunc {
    void execute() throws Exception;
}
