package com.twu.biblioteca;

public class Movie extends LibraryItem {

    private String director;
    private RatingOption rating;

    private final String type = "Movie";

    public enum RatingOption {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(2),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10);

        public final int val;
        RatingOption(int val) {
            this.val = val;
        }
    };

    public Movie(String name, String director, int year, RatingOption rating) {
        this.name = name;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    public String Director() {
        return director;
    }

    public RatingOption Rating() { return rating; }

    public String PrintDetails() {
        return String.join(" | ", Name(), Director(), String.valueOf(Year()), String.valueOf(Rating().val), type);
    }

    public String Type() {
        return type;
    }
}
