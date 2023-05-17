package com.shelter.services;

import com.shelter.data.entities.Walk;
import com.shelter.dto.WalkDTO;

public interface WalkService {

    Walk takeAnimalForWalk(WalkDTO walkDTO);

    Walk returnFromWalk(Long walkId, String comment);
}
