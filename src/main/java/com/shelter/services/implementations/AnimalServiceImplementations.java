package com.shelter.services.implementations;

import com.shelter.data.entities.Animal;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.dto.AnimalDTO;
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

    /*public Animal addAnimal(AnimalDTO animalDTO) {
        Animal animal = modelMapper.map(animalDTO, Animal.class);
        return animalRepository.save(animal);
    }*/

    public Animal addAnimal(AnimalDTO animalDTO) {
        Animal animal = modelMapper.map(animalDTO, Animal.class);

        // Process and save the picture
        if (animalDTO.getPicture() != null && !animalDTO.getPicture().isEmpty()) {
            String pictureUrl = savePicture(animalDTO.getPicture());
            animal.setPictureUrl(pictureUrl);
        }

        return animalRepository.save(animal);
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

    public List<Animal> getAllAnimals() {

        return animalRepository.findByIsAdoptedFalse();
    }

    public List<Animal> getAvailableAnimals() {
        return animalRepository.findByIsAvailableTrue();
    }

    public Animal adopt(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal not found"));

        animal.setAdopted(true);
        animal.setAvailable(false);
        return animalRepository.save(animal);
    }

    @Override
    public List<Animal> getAnimalsOutForWalk() {
        return animalRepository.findByIsAvailableFalseAndIsAdoptedFalse();
    }

    @Override
    public Animal getAnimalById(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal not found"));

    }

}
