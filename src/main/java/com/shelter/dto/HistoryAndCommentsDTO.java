package com.shelter.dto;

import com.shelter.data.entities.Walk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryAndCommentsDTO {
    private List<Walk> walks;
    private List<String> comments;
}
