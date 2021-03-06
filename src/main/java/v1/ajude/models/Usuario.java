package v1.ajude.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import v1.ajude.dtos.CampanhaDTO;
import v1.ajude.dtos.DoacaoDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Column(unique = true)
    private String urlUser;
    private String primeiroNome;
    private String ultimoNome;
    @Id
    private String email;
    private String numCartao;
    private String senha;

    @OneToMany
    private List<Campanha> campanhasCriadas;
    @OneToMany
    @JsonIgnore
    private List<Comentario> comentariosFeitos;
    @OneToMany
    @JsonIgnore
    private List<Likes> likesEmCampanhas;
    @OneToMany
    private List<Doacao> doacoesFeitas;

    public Usuario() {
        super();
    }
    public Usuario(String urlUser, String primeiroNome, String ultimoNome, String email, String numCartao, String senha) {
        super();
        this.urlUser = urlUser;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.numCartao = numCartao;
        this.senha = senha;
        this.campanhasCriadas = new ArrayList<Campanha>();
        this.comentariosFeitos = new ArrayList<Comentario>();
        this.likesEmCampanhas = new ArrayList<Likes>();
        this.doacoesFeitas = new ArrayList<Doacao>();
    }

    public String getUrlUser() {
        return this.urlUser;
    }
    public String getPrimeiroNome() {
        return this.primeiroNome;
    }
    public String getUltimoNome() {
        return this.ultimoNome;
    }
    public String getEmail() {
        return this.email;
    }
    public String getNumCartao() {
        return this.numCartao;
    }
    public String getSenha() {
        return this.senha;
    }

    public List<Campanha> getCampanhasCriadas() {
        return this.campanhasCriadas;
    }
    public List<Doacao> getDoacoesFeitas() {
        return this.doacoesFeitas;
    }

    public List<CampanhaDTO> getCampanhasCriadasDTO() {
        List<CampanhaDTO> campanhaDTOS = new ArrayList<CampanhaDTO>();
        for (Campanha campanha : this.campanhasCriadas) {
            campanhaDTOS.add(new CampanhaDTO(campanha.getNomeCurto(), campanha.getURL(), campanha.getDeadLineString(),
                    campanha.getStatus(), campanha.getMeta(), campanha.getDoacoes(), campanha.getDescricao(), campanha.getNumeroDeLikes()));
        }
        return campanhaDTOS;
    }
    public List<DoacaoDTO> getDoacoesFeitasDTO() {
        List<DoacaoDTO> doacaoDTOS = new ArrayList<DoacaoDTO>();
        for (Doacao doacao : this.doacoesFeitas) {
            doacaoDTOS.add(new DoacaoDTO(doacao.getQuantia(), doacao.getDataDeDoacaoString(), doacao.getCampanhaDTO()));
        }
        return doacaoDTOS;
    }


    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }
    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCampanhasCriadas(Campanha campanha) {
        this.campanhasCriadas.add(campanha);
    }
    public void setComentario(Comentario novoComentario) {
        this.comentariosFeitos.add(novoComentario);
    }
    public void setLikesEmCampanhas(Likes likesEmCampanhas) {
        this.likesEmCampanhas.add(likesEmCampanhas);
    }
    public void setNovaDoacao(Doacao novaDoacao) {
        this.doacoesFeitas.add(novaDoacao);
    }
}