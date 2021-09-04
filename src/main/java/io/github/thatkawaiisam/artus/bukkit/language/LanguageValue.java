package io.github.thatkawaiisam.artus.bukkit.language;


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
