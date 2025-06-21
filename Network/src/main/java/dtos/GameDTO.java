package dtos;

import Domain.Game;

import java.time.Duration;
import java.time.LocalDateTime;

public class GameDTO {
    private int id;
    private PlayerDTO player;
    private ConfigurationDTO configuration;
    private Game.gameStatus status;
    private LocalDateTime startTime;
    private Duration duration;
    private Integer trials;
    public GameDTO() {

    }
    public GameDTO(int id, PlayerDTO player, ConfigurationDTO configuration, Game.gameStatus status) {
        this.id = id;
        this.player = player;
        this.configuration = configuration;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public PlayerDTO getPlayer() {
        return player;
    }
    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
    public ConfigurationDTO getConfiguration() {
        return configuration;
    }
    public void setConfiguration(ConfigurationDTO configuration) {
        this.configuration = configuration;
    }
    public Game.gameStatus getStatus() {
        return status;
    }
    public void setStatus(Game.gameStatus status) {
        this.status = status;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public Integer getTrials() {
        return trials;
    }
    public void setTrials(Integer trials) {
        this.trials = trials;
    }


}
