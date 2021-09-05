package io.github.thatkawaiisam.artus.bukkit.language;


public class LanguageValue<T> {

    private T value;

    /**
     * Language Value.
     *
     * @param value to set.
     */
    public LanguageValue(T value) {
        this.value = value;
    }

    /**
     * Set Language Value.
     *
     * @param value to set.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Get Language Value.
     *
     * @return language value.
     */
    public T getValue() {
        return value;
    }
}
