package io.github.thatkawaiisam.artus.module;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModuleOptions {

    private boolean generateConfiguration = false;
    private ModuleLoadLevel loadLevel = ModuleLoadLevel.NORMAL;

}
