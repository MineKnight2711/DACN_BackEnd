package com.example.dacn.modules.map.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class LocationResponse {
    private List<Result> results;
    private String status;
    @Data
    @Getter
    @Setter
    public class Result {
        private String formatted_address;
        private Geometry geometry;
        private String name;
        private String address;
        @Data
        @Getter
        @Setter
        public class Geometry {
            private Location location;
            @Data
            @Getter
            @Setter
            public class Location{
                private String lat;
                private String lng;
            }
        }
    }
}
