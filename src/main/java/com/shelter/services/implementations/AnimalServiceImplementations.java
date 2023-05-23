package com.shelter.services.implementations;

import com.shelter.data.entities.Animal;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.dto.AnimalDTO;
import com.shelter.dto.returnAnimalDTO;
import com.shelter.dto.returnDetailedAnimalDTO;
import com.shelter.exceptions.AnimalNotFoundException;
import com.shelter.services.AnimalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImplementations implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;


    public returnDetailedAnimalDTO addAnimal(AnimalDTO animalDTO) {
        Animal animal = modelMapper.map(animalDTO, Animal.class);

        // Process and save the picture
        if (animalDTO.getPicture() != null && !animalDTO.getPicture().isEmpty()) {
            String pictureUrl = savePicture(animalDTO.getPicture());
            animal.setPictureUrl(pictureUrl);
        }
        return modelMapper.map(animalRepository.save(animal), returnDetailedAnimalDTO.class);

    }

    private String savePicture(MultipartFile picture) {
        try {
            String fileName = picture.getOriginalFilename();

            String homeDir = System.getProperty("user.home");
            String desktopPath = homeDir + File.separator + "Desktop";
            String uploadDir = desktopPath + File.separator + "images" + File.separator;

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = uploadDir + fileName;
            File destination = new File(filePath);
            picture.transferTo(destination);

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save the picture file.");
        }
    }

    public List<returnAnimalDTO> getAllAnimals() {

        return animalRepository.findByIsAdoptedFalse()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    public List<returnAnimalDTO> getAvailableAnimals() {
        return animalRepository.findByIsAvailableTrue()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void returnAnimalFromWalk(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));
        animal.setAvailable(false);
        animalRepository.save(animal);
    }

    public returnDetailedAnimalDTO adopt(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));

        animal.setAdopted(true);
        animal.setAvailable(false);
        return modelMapper.map(animalRepository.save(animal), returnDetailedAnimalDTO.class);
    }

    @Override
    public List<returnAnimalDTO> getAnimalsOutForWalk() {
        return animalRepository.findByIsAvailableFalseAndIsAdoptedFalse()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public returnDetailedAnimalDTO getAnimalById(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));

        return modelMapper.map(animal, returnDetailedAnimalDTO.class);
    }

}
