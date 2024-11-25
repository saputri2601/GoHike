package com.example.gohike;

public class data_class_uploaddata {

        private String name;
        private String address;
        private String email;
        private String age;

        // Default constructor (required for Firebase)
        public data_class_uploaddata() {}

        // Constructor
        public data_class_uploaddata(String name, String address, String email, String age) {
                this.name = name;
                this.address = address;
                this.email = email;
                this.age = age;
        }

        // Getters and setters
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getAge() {
                return age;
        }

        public void setAge(String age) {
                this.age = age;
        }
}
