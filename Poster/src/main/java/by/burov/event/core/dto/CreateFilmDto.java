package by.burov.event.core.dto;

public class CreateFilmDto extends CreateEventDto {

    private String country;
    private int releaseYear;
    private String releaseDate;
    private int duration;

    public String getCountry() {
        return country;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }
}
