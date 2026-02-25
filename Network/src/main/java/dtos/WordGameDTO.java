package dtos;

import Domain.Game;

import java.time.Duration;
import java.time.LocalDateTime;

public class WordGameDTO {
    private int id;
    private PlayerDTO player;
    private WordGameConfigurationDTO configuration;
    private Game.gameStatus status;
    private LocalDateTime startTime;
    private Duration duration;
    private Integer turns;
    public WordGameDTO() {

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
    public WordGameConfigurationDTO getConfiguration() {
        return configuration;
    }
    public void setConfiguration(WordGameConfigurationDTO configuration) {
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
    public Integer getTurns() {
        return turns;
    }
    public void setTurns(Integer turns) {
        this.turns = turns;
    }
}
