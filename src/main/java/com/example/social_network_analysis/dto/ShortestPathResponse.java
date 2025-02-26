package com.example.social_network_analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ShortestPathResponse {
    private List<String> path;

    public ShortestPathResponse(List<String> shortestPath) {
    }
}
