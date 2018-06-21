package com.github.mavbraz.barbermobile.model.basicas;

import java.util.List;

public class TodosServicos {

    private int total;
    private List<Servico> servicos;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

}
