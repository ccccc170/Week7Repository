package com.sparta.week6project.daos.interfaces;

import com.sparta.week6project.dtos.TitleDTO;
import com.sparta.week6project.entities.TitleId;

import java.util.Optional;

public interface TitleService<T> {

    Optional<T> findById(TitleId id);

    TitleDTO save(TitleDTO e);

    void update(TitleDTO e,TitleId id);

    void deleteById(TitleId id);
}
