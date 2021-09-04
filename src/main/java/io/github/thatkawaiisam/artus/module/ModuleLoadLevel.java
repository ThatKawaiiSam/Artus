package io.github.thatkawaiisam.artus.module;

import lombok.Getter;

public enum ModuleLoadLevel {

    HIGH(100),
    NORMAL(50),
    LOW(25);

    @Getter
    private int weight;

    /**
     * Module Load Level.
     *
     * @param weight of level.
     */
    ModuleLoadLevel(int weight) {
        this.weight = weight;
    }

}