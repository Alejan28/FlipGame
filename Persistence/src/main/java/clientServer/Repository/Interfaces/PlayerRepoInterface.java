package clientServer.Repository.Interfaces;

import Domain.Player;

public interface PlayerRepoInterface extends IRepository<Integer,Player>{
    Player findByNickname(String nickname);
}
