package com.info.model;

public enum TipoDocumento {
    CEDULA("C"),
    PASAPORTE("P");
    private final String codigo;
    TipoDocumento(String codigo){
        this.codigo= codigo;
    }
    public String getCodigo(){
        return codigo;
    }
}
