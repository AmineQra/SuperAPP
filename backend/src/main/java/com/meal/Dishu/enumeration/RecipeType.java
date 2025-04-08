package com.meal.Dishu.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RecipeType {
    MATIN,
    MIDI,
    SOIR,
    GOUTER;

    @JsonCreator
    public static RecipeType fromValue(String value) {
        return RecipeType.valueOf(value.toUpperCase());
    }
}
