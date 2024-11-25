package com.example.gohike;

public class data_class {

    // Model data OpenTrip
    public static class OpenTrip {
        private String title;
        private String duration;
        private String status;

        // Constructor
        public OpenTrip(String title, String duration, String status) {
            this.title = title;
            this.duration = duration;
            this.status = status;
        }

        // Getter for title
        public String getTitle() {
            return title;
        }

        // Getter for duration
        public String getDuration() {
            return duration;
        }

        // Getter for status
        public String getStatus() {
            return status;
        }
    }
}
