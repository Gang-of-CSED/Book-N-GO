package com.example.book_n_go.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Amenity {
  
  SCREEN("Screen"),
  PROJECTOR("Projector"),
  AC("AC"),
  CEILING_FANS("Ceiling Fans"),
  WHITE_BOARD("White Board");

  @Getter
  private final String amenity;
}
