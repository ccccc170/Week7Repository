package com.sparta.week6project.mappers;

import com.sparta.week6project.dtos.TitleDTO;
import com.sparta.week6project.entities.Title;

public interface TitleMapper {

    TitleDTO titleToDTO(Title title);
    Title dtoToTitle(TitleDTO titleDTO);
}