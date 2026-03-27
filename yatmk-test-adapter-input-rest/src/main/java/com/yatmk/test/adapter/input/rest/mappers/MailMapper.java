package com.yatmk.test.adapter.input.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import com.yatmk.test.adapter.input.rest.dto.MailInput;
import com.yatmk.test.ports.domain.events.Mail;
import com.yatmk.test.ports.domain.events.Mail.MailFile;
import com.yatmk.test.ports.domain.exception.ServerSideException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MailMapper {

  public static final MailMapper INSTANCE = Mappers.getMapper(MailMapper.class);

  // 1. Main entry point: Maps simple fields automatically
  @Mapping(target = "attachments", source = "attachments")
  public abstract Mail toDomain(MailInput mailInput);

  // 2. Collection mapping: MapStruct calls this for the 'attachments' list
  protected List<MailFile> mapAttachments(List<MultipartFile> files) {

    return Optional.ofNullable(files)
        .orElseGet(Collections::emptyList).stream()
        .map(this::fileToMailFile)
        .collect(Collectors.toList());
  }

  // 3. Custom logic: Handles the try-catch and builder logic
  protected MailFile fileToMailFile(MultipartFile file) {
    try {
      return MailFile.builder()
          .fileName(file.getOriginalFilename())
          .contentType(file.getContentType())
          .inputStream(file.getInputStream())
          .fileSize(file.getSize())
          .build();
    } catch (IOException e) {
      throw new ServerSideException("Error processing file: " + file.getOriginalFilename(), e);
    }
  }
}