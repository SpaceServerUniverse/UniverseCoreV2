package space.yurisi.universecorev2.repository;

import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.model.profile.Profile;
import space.yurisi.universecorev2.model.user.UserId;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {
    void create(@NotNull Profile user);
    Optional<Profile> findByMinecraftId(@NotNull UserId id);
    Optional<Profile> findByMinecraftId(@NotNull UUID mcUuid);
    void update(@NotNull Profile user);
    void delete(@NotNull UserId id);
}
