package space.yurisi.universecorev2.model.profile;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.user.UserId;

import java.util.UUID;

public record Profile(
        @NotNull UserId id,
        @NotNull UUID mcUuid,
        @NotNull String name
) {
    public Profile withName(String newName) {
        return new Profile(id, mcUuid, newName);
    }
}
