package io.github.thatkawaiisam.plugintemplate.shared;

public interface IConfiguration {

    void loadFile();
    void saveFile();

    <T> T getAmbig();
}
