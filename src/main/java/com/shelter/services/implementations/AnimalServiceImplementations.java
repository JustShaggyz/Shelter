package com.shelter.services.implementations;

import com.google.gson.Gson;
import com.shelter.data.entities.Animal;
import com.shelter.data.entities.AnimalType;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.AnimalTypeRepository;
import com.shelter.dto.*;
import com.shelter.exceptions.AnimalNotFoundException;
import com.shelter.services.AnimalService;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImplementations implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;
    private final AnimalTypeRepository animalTypeRepository;
    private static final String IMGBB_API_KEY = "b30a30d4a14ea4da2de7c718b7f346c0";


    public returnDetailedAnimalDTO addAnimal(AnimalDTO animalDTO) {
        Animal animal = modelMapper.map(animalDTO, Animal.class);
        createAnimalType(animalDTO.getType());

        AnimalType animalType = animalTypeRepository.findByType(animalDTO.getType());

        animal.setType(animalType);

        //If picture is provided, upload it to imgbb. Else set default picture
        if (animalDTO.getPicture() != null && !animalDTO.getPicture().isEmpty()) {
            String pictureUrl = uploadPicture(animalDTO.getPicture());
            animal.setPictureUrl(pictureUrl);
        }
        else {
            animal.setPictureUrl("https://i.ibb.co/gMVTFHs/default-20230526171923.jpg");
        }
        return modelMapper.map(animalRepository.save(animal), returnDetailedAnimalDTO.class);
    }

    private String uploadPicture(MultipartFile picture) {
        try {
            String originalFileName = picture.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);

            //Add timestamp to name
            String fileName = generateUniqueFileName(originalFileName);

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", fileName,
                            RequestBody.create(MediaType.parse("image/*"), picture.getBytes()))
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.imgbb.com/1/upload?key=" + IMGBB_API_KEY)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to upload picture to ImgBB. Response code: " + response.code());
            }

            String responseBody = response.body().string();
            String imageUrl = parseImageUrl(responseBody);

            response.close();

            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload picture to ImgBB.");
        }
    }

    //Appends timestamp to picture name
    private String generateUniqueFileName(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        String fileNameWithoutExtension = (dotIndex >= 0) ? originalFileName.substring(0, dotIndex) : originalFileName;
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return fileNameWithoutExtension + "_" + timeStamp;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private String parseImageUrl(String responseBody) {

        Gson gson = new Gson();
        ImgBBResponse imgBBResponse = gson.fromJson(responseBody, ImgBBResponse.class);
        return imgBBResponse.getData().getUrl();
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

    //Get all non-adopted animals
    public List<returnAnimalDTO> getAllAnimals() {
        return animalRepository.findByIsAdoptedFalse()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    //Get available animals
    public List<returnAnimalDTO> getAvailableAnimals() {
        return animalRepository.findByIsAvailableTrue()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void returnAnimalFromWalk(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found"));
        animal.setAvailable(false);
        animalRepository.save(animal);
    }

    //Get animal types
    @Override
    public List<returnAnimalType> getAnimalTypes() {
        return animalTypeRepository.findAll()
                .stream()
                .map(animalType -> modelMapper.map(animalType, returnAnimalType.class))
                .collect(Collectors.toList());
    }

    //Create new animal type
    @Override
    public boolean createAnimalType(String type) {
        AnimalType animalType = animalTypeRepository.findByType(type);
        if (animalType == null) {
            animalType = new AnimalType(type);
            animalTypeRepository.save(animalType);
            return true;
        }
        return false;
    }

    //Adopt animal
    public returnDetailedAnimalDTO adopt(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found"));

        //Set adopted true and availability to false
        animal.setAdopted(true);
        animal.setAvailable(false);
        return modelMapper.map(animalRepository.save(animal), returnDetailedAnimalDTO.class);
    }

    //Get unavailable animals
    @Override
    public List<returnAnimalDTO> getAnimalsOutForWalk() {
        return animalRepository.findByIsAvailableFalseAndIsAdoptedFalse()
                .stream()
                .map(animal -> modelMapper.map(animal, returnAnimalDTO.class))
                .collect(Collectors.toList());
    }

    //Get animal by id
    @Override
    public returnDetailedAnimalDTO getAnimalById(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found"));

        return modelMapper.map(animal, returnDetailedAnimalDTO.class);
    }

}
