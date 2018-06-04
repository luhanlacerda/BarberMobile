package com.github.mavbraz.barbermobile.model.basicas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Agendamento implements Parcelable {
    private int id;
    private long horario;
    private Situacao situacao;
    private Pagamento pagamento;
    private Cliente cliente;
    private List<Servico> servicos;

    public Agendamento() {
    }

    public Agendamento(int id, long horario, Situacao situacao, Pagamento pagamento, Cliente cliente, List<Servico> servicos) {
        this.id = id;
        this.horario = horario;
        this.situacao = situacao;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.servicos = servicos;
    }

    private Agendamento(Parcel from) {
        id = from.readInt();
        horario = from.readLong();
        situacao = Situacao.valueOf(from.readString());
        pagamento = Pagamento.valueOf(from.readString());
        cliente = from.readParcelable(Cliente.class.getClassLoader());
        from.readTypedList(servicos, Servico.CREATOR);
    }

    public static final Parcelable.Creator<Agendamento> CREATOR = new Parcelable.Creator<Agendamento>() {
        @Override
        public Agendamento createFromParcel(Parcel in) {
            return new Agendamento(in);
        }

        @Override
        public Agendamento[] newArray(int size) {
            return new Agendamento[size];
        }
    };

    @Override
    public int describeContents() {
        return 2;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(horario);
        dest.writeString(situacao.name());
        dest.writeString(pagamento.name());
        dest.writeParcelable(cliente, flags);
        dest.writeTypedList(servicos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ID] ");
        builder.append(id);
        builder.append("\n");
        builder.append("[HORARIO] ");
        builder.append(horario);
        builder.append("\n");
        builder.append("[SITUACAO] ");
        builder.append(situacao);
        builder.append("\n");
        builder.append("[PAGAMENTO] ");
        builder.append(pagamento);
        builder.append("\n");
        builder.append("[CLIENTE] ");
        builder.append(cliente == null ? cliente : cliente.getNome());
        builder.append("\n");
        builder.append("[SERVICOS]\n");
        if (servicos != null) {
            for (int i = 0; i < servicos.size(); i++) {
                builder.append("[SERVICO ");
                builder.append(i);
                builder.append("] ");
                builder.append(servicos.get(i).getNome());
                builder.append("\n");
            }
        } else {
            builder.append("[SERVICO] ");
            builder.append(servicos);
            builder.append("\n");
        }
        return builder.toString();
    }
}
