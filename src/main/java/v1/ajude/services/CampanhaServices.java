package v1.ajude.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import v1.ajude.daos.CampanhaRepository;
import v1.ajude.daos.ComentarioRepository;
import v1.ajude.models.Campanha;
import v1.ajude.models.Comentario;
import v1.ajude.models.Resposta;
import v1.ajude.models.Usuario;

import java.util.Optional;

@Service
public class CampanhaServices {

    @Autowired
    private CampanhaRepository<Campanha, Integer> campanhasDAO;

    @Autowired
    private ComentarioRepository<Comentario, Integer> comentariosDAO;

    @Autowired
    private UsuarioServices usuarioServices;

    public CampanhaServices(CampanhaRepository<Campanha, Integer> campanhasDAO) {
        this.campanhasDAO = campanhasDAO;
    }

    public Campanha criarCampanha(String email, Campanha campanha) {
        Campanha campanhaSalva = recuperaCampanha(campanha);

        if (campanhaSalva == null) {
            Campanha campanhaConstruct = new Campanha(campanha.getNomeCurto(), campanha.getDescricao(),
                    campanha.getDeadLine(), campanha.getURL(), campanha.getMeta(), usuarioServices.getUsuario(email).get());
            return campanhasDAO.save(campanhaConstruct);
        }
        return null;
    }

    public Optional<Campanha> getCampanha(Long id) {
        Campanha campanhaSalva = recuperaCampanha(id);

        if (campanhaSalva != null) {
            // Atualiza Status Campanha, sem encerrar
            campanhaSalva.setStatus(false);
            campanhasDAO.save(campanhaSalva);
            return campanhasDAO.findById(id);
        }
        return null;
    }

    public Campanha setStatus(Campanha campanha) {
        Campanha campanhaSalva = recuperaCampanha(campanha);

        if (campanhaSalva != null) {
            // Encerra Campanha
            campanhaSalva.setStatus(true);
            campanhasDAO.save(campanhaSalva);
            return campanhaSalva;
        }
        return null;
    }

    public Comentario addComentario(Campanha campanha, Comentario comentario, String email) {
        Campanha campanhaSalva = recuperaCampanha(campanha);
        Usuario usuarioSalvo = recuperaUsuario(email);

        if (campanhaSalva != null && usuarioSalvo != null) {
            Comentario novoComentario = new Comentario(campanhaSalva, usuarioSalvo, comentario.getTextoComentario());
            comentariosDAO.save(novoComentario);
            return novoComentario;
        }
        return null;
    }

    /*
    public Comentario addResposta(Campanha campanha, int idComentario, Resposta resposta, String email) {
        Campanha campanhaSalva = recuperaCampanha(campanha);
        Usuario usuarioSalvo = recuperaUsuario(email);
        Comentario comentarioSalvo = recuperaComentario(campanhaSalva);

        if (campanhaSalva != null && usuarioSalvo != null) {
            Resposta novaResposta = new Resposta()
            Comentario comentarioComNovaResposta = campanhaSalva.addResposta(idComentario, usuarioSalvo, resposta);
            return comentarioComNovaResposta;
        }
        return null;
    }
    */

    private Campanha recuperaCampanha(Campanha campanha) {
        Optional<Campanha> campanhaSalva = this.campanhasDAO.findById(campanha.getId());
        if (campanhaSalva.isPresent()) {
            return campanhaSalva.get();
        }
        return null;
    }

    private Campanha recuperaCampanha(Long id) {
        Optional<Campanha> campanhaSalva =  this.campanhasDAO.findById(id);
        if (campanhaSalva.isPresent()) {
            return campanhaSalva.get();
        }
        return null;
    }

    private Usuario recuperaUsuario(String email) {
        Optional<Usuario> usuarioSalvo =  usuarioServices.getUsuario(email);
        if (usuarioSalvo.isPresent()) {
            return usuarioSalvo.get();
        }
        return null;
    }

}
