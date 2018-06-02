package com.github.mavbraz.barbermobile.utils;

import java.util.ArrayList;
import java.util.List;

public class BarberException extends Exception {

    public static String EMAIL = "email";
    public static String SENHA = "senha";

    private static String ONLY_LIST = "Apenas permitido com o construtor (boolean)";
    private static String ONLY_ONCE = "Apenas permitido com o construtor (String, String)";
    private static String COMPONENT_INVALID = "Não é permitido informar um component nulo ou vazio";

    private List<BarberException> exceptions;

    private String component;

    public BarberException(String message) {
        super(message);
    }

    public BarberException(boolean hasMany) {
        super();

        if (hasMany) {
            this.exceptions = new ArrayList<>();
        }
    }

    public BarberException(String message, String component) throws BarberException {
        super(message);

        this.setComponent(component);
    }

    public String getComponent() throws BarberException {
        if (this.component == null) {
            throw new BarberException(BarberException.ONLY_ONCE);
        }

        return component;
    }

    public void setComponent(String component) throws BarberException {
        if (component == null || component.trim().isEmpty()) {
            throw new BarberException(BarberException.COMPONENT_INVALID, component);
        }

        this.component = component;
    }

    public List<BarberException> getExceptions() throws BarberException {
        if (this.exceptions == null) {
            throw new BarberException(BarberException.ONLY_LIST);
        }

        return exceptions;
    }

    public void addException(BarberException exception) throws BarberException {
        if (this.exceptions == null) {
            throw new BarberException(BarberException.ONLY_LIST);
        }

        this.exceptions.add(exception);
    }

}
