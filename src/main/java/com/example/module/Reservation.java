import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int reservationId;
    private int userId;
    private int terrainId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructor
    public Reservation(int reservationId, int userId, int terrainId, LocalDate reservationDate,
                       LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.terrainId = terrainId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}