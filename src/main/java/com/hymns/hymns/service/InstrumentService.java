package com.hymns.hymns.service;

import com.hymns.hymns.dto.InstrumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InstrumentService {

    void addInstrument(InstrumentDto dto);

    String saveImage(MultipartFile file) throws IOException;

    List<InstrumentDto> getAllInstruments();

    InstrumentDto getInstrument(int id);

    List<InstrumentDto> getUserInstruments();

    void changeAdded(int id);
}
