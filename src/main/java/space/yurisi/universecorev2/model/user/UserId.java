package space.yurisi.universecorev2.model.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record UserId(
        @NotNull UUID value
) {
}
