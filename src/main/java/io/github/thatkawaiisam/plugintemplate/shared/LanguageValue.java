package io.github.thatkawaiisam.plugintemplate.shared;


public class LanguageValue<T> {

    private T value;

    public LanguageValue(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
