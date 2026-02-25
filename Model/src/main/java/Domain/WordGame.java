package Domain;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name="word_games")
public class WordGame extends Entity<Integer> {
    public enum gameStatus{
        STARTED, WON, LOST
    }
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @OneToOne(cascade = CascadeType.ALL) // Add cascade to persist the config automatically
    @JoinColumn(name = "config_id")
    private WordGameConfig config;
    @Column(name = "status", nullable = false)
    private Game.gameStatus status;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "duration")
    private Duration duration;
    @Column(name = "turns")
    private Integer turns;

    public WordGame() {

    }
    public WordGame(Player player, WordGameConfig config) {
        this.player = player;
        this.config = config;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public WordGameConfig getConfig() {
            return config;
    }
    public void setConfig(WordGameConfig config) {
        this.config = config;
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WordGame other = (WordGame) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
