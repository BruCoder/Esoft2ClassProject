package modelo;

/**
 * Created by Bruno on 05-03-2015.
 */
public class Contacto {

    private String nome;
    private String email;

    public Contacto(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return " Contacto: " +
                " nome= " + nome +'|' +
                " email= " + email;
    }
}
