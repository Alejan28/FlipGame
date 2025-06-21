package Domain;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name="games")
public class Game extends Entity<Integer>{
    public enum gameStatus{
        STARTED, WON, LOST
    }
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "config_id")
    private Configuration config;
    @Column(name = "status", nullable = false)
    private gameStatus status;
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "duration")
    private Duration duration;
    @Column(name = "trials")
    private Integer trials;
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public Duration getDuration() {
        return duration;
    }
    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    @Override
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
    public gameStatus getStatus() {
        return status;
    }
    public void setStatus(gameStatus status) {
        this.status = status;
    }
    public Game(){

    }
    public Game(Player player, Configuration config){
        this.player = player;
        this.config = config;
        this.status = gameStatus.STARTED;
    }
    public void setTrials(Integer trials) {
        this.trials = trials;
    }
    public Integer getTrials() {
        return trials;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Game other = (Game) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
