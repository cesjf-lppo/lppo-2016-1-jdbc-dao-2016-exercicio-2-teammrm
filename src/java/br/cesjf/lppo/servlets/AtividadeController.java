package br.cesjf.lppo.servlets;

import br.cesjf.lppo.Atividade;
import br.cesjf.lppo.AtividadeDAO;
import br.cesjf.lppo.AtividadeDAOPrep;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author igor
 */
@WebServlet(name = "EstabelecimentoController",
        urlPatterns = {"/listar.html", "/novo.html", "/excluir.html", "/editar.html"})
public class AtividadeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().contains("listar.html")) {
            List<Atividade> lista = new ArrayList<>();
            try {
                AtividadeDAOPrep dao = new AtividadeDAOPrep();
                lista = dao.listaTodos();
            } catch (Exception ex) {
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
                lista = new ArrayList<Atividade>();
                request.setAttribute("erro", "Problema ao listar atividades!");
            }

            request.setAttribute("atividades", lista);
            request.getRequestDispatcher("/WEB-INF/views/atividade/listar.jsp").forward(request, response);
        } else if (request.getRequestURI().contains("novo.html")) {
            request.getRequestDispatcher("/WEB-INF/views/atividade/novo.jsp").forward(request, response);

        } else if (request.getRequestURI().contains("excluir.html")) {
            Long id = Long.parseLong(request.getParameter("id"));
            try {
                AtividadeDAOPrep dao = new AtividadeDAOPrep();
                dao.excluirPorId(id);
            } catch (Exception ex) {
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("listar.html");
        } else if (request.getRequestURI().contains("editar.html")) {
            Long id = Long.parseLong(request.getParameter("id"));
            
            try {
                AtividadeDAOPrep dao = new AtividadeDAOPrep();
                Atividade estab = dao.buscaPorId(id);
                if (estab != null) {
                    request.setAttribute("estabelecimento", estab);
                    request.getRequestDispatcher("/WEB-INF/views/atividade/editar.jsp").forward(request, response);
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("listar.html");

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains("novo.html")) {
            Atividade nova = new Atividade();
            nova.setFuncionario(request.getParameter("funcionario"));
            nova.setDescricao(request.getParameter("descricao"));
            nova.setTipo(request.getParameter("tipo"));
            nova.setHoras(Integer.parseInt(request.getParameter("horas")));

        try {
            AtividadeDAOPrep dao = new AtividadeDAOPrep();

                dao.criar(nova);
            } catch (Exception ex) {
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("listar.html?erro=Erro ao criar atividade!");
                return;

            }

            response.sendRedirect("listar.html");
        } else if (request.getRequestURI().contains("editar.html")) {
            Long id = Long.parseLong(request.getParameter(
                    "id"));
            try {
                AtividadeDAOPrep dao = new AtividadeDAOPrep();
                Atividade nova = dao.buscaPorId(id);
                if (nova != null) {
                    nova.setFuncionario(request.getParameter("funcionario"));
                    nova.setDescricao(request.getParameter("descricao"));
                    nova.setTipo(request.getParameter("tipo"));
                    nova.setHoras(Integer.parseInt(request.getParameter("horas")));

                    dao.salvar(nova);
                    response.sendRedirect("listar.html");
                }
            } catch (Exception ex) {
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("editar.html?id=" + id);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
