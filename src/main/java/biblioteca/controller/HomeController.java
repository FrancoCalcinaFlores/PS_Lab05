package biblioteca.controller;

import biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public HomeController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("prestamosActivos", bibliotecaService.listarPrestamosActivos());
        model.addAttribute("totalLibrosDisponibles", bibliotecaService.totalLibrosDisponibles());
        model.addAttribute("totalLibrosCat", bibliotecaService.listarTodosLosLibros().size());
        model.addAttribute("totalUsuarios", bibliotecaService.listarTodosLosUsuarios().size());
        return "index";
    }
}
