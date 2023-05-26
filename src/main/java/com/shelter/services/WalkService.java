package com.shelter.services;

import com.shelter.data.entities.Walk;
import com.shelter.dto.WalkDTO;
import com.shelter.dto.returnWalkDTO;

import java.util.List;

public interface WalkService {

    returnWalkDTO takeAnimalForWalk(WalkDTO walkDTO);

    returnWalkDTO returnFromWalk(Long walkId, String comment);

    List<returnWalkDTO> getOngoingWalks();

    returnWalkDTO getWalkById(Long walkId);
}
