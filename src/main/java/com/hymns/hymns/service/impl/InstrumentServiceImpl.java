package com.hymns.hymns.service.impl;

import com.hymns.hymns.dto.InstrumentDto;
import com.hymns.hymns.entity.Instrument;
import com.hymns.hymns.repository.InstrumentRepo;
import com.hymns.hymns.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepo instrumentRepo;
    private final String uploadDirectory = System.getProperty("user.dir") + "/instrument_images";

    @Override
    public void addInstrument(InstrumentDto dto) {
        try {
            Instrument instrument = new Instrument();
            instrument.setInstrumentName(dto.getInstrumentName());
            instrument.setInstrumentType(dto.getInstrumentType());
            instrument.setInstrumentRentalPrice(dto.getInstrumentRentalPrice());
            instrument.setInstrumentCondition(dto.getInstrumentCondition());
            instrument.setInstrumentRentalStatus("available");
            instrument.setInstrumentImage(dto.getInstrumentImage());
            instrumentRepo.save(instrument);

        } catch (Exception e) {
            throw new RuntimeException("Error in saving instrument");
        }

    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String imageName = file.getName() + "_" + System.currentTimeMillis();

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Save the original image file
        Path originalFilePath = Paths.get(uploadDirectory, imageName + fileExtension);
        Files.write(originalFilePath, file.getBytes());


        // Resize the image
        int desiredWidth = 200;  // Set your desired width
        int desiredHeight = 200; // Set your desired height

        // Provide the path to the output resized image
        String resizedImagePath = uploadDirectory + "/" + imageName + "_resized" + fileExtension;

        // Resize the image using Thumbnails library
        Thumbnails.of(originalFilePath.toFile())
                .size(desiredWidth, desiredHeight)
                .outputFormat(fileExtension.substring(1)) // Remove the dot (.) from the file extension
                .toFile(resizedImagePath);

        // Update the item with the resized image path
        return imageName + fileExtension;
    }

    @Override
    public List<InstrumentDto> getAllInstruments() {
        List<Instrument> instruments = instrumentRepo.findAll();
//        instruments into dto

        return instruments.stream().map(
                instrument -> InstrumentDto.builder()
                        .instrumentId(instrument.getInstrumentId())
                        .instrumentName(instrument.getInstrumentName())
                        .instrumentType(instrument.getInstrumentType())
                        .instrumentRentalPrice(instrument.getInstrumentRentalPrice())
                        .instrumentCondition(instrument.getInstrumentCondition())
                        .instrumentRentalStatus(instrument.getInstrumentRentalStatus())
                        .instrumentImage(instrument.getInstrumentImage())
                        .build()
        ).toList();

    }

    @Override
    public InstrumentDto getInstrument(int id) {
        Instrument instrument = instrumentRepo.findById(id).orElse
                (new Instrument());

        return InstrumentDto.builder()
                .instrumentId(instrument.getInstrumentId())
                .instrumentName(instrument.getInstrumentName())
                .instrumentType(instrument.getInstrumentType())
                .instrumentRentalPrice(instrument.getInstrumentRentalPrice())
                .instrumentCondition(instrument.getInstrumentCondition())
                .instrumentRentalStatus(instrument.getInstrumentRentalStatus())
                .instrumentImage(instrument.getInstrumentImage())
                .build();
    }
}
