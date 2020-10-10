package io.github.thatkawaiisam.plugintemplate.shared;

public interface IConfiguration {

    /**
     *
     */
    void loadFile();

    /**
     *
     */
    void saveFile();

    /**
     *
     * @param <T>
     * @return
     */
    <T> T getAmbig();

}
