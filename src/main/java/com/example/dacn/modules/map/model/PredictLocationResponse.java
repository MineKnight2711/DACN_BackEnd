package com.example.dacn.modules.map.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PredictLocationResponse
{
    private List<Predictions> predictions;
    private String status;
    @Data
    @Getter
    @Setter
    public class Predictions
    {
        private String description;
        private Compound compound;
        @Data
        @Getter
        @Setter
        public class Compound
        {
            private String district;
            private String commune;
            private String province;
        }
    }
}
