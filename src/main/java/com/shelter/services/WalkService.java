package com.shelter.services;

import com.shelter.data.entities.Walk;
import com.shelter.dto.WalkDTO;

import java.util.List;

public interface WalkService {

    Walk takeAnimalForWalk(WalkDTO walkDTO);

    Walk returnFromWalk(Long walkId, String comment);

    List<Walk> getOngoingWalks();
}
