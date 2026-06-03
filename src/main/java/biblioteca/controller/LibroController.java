package biblioteca.controller;

import biblioteca.model.Libro;
import biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public LibroController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public String listarLibros(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("libros", bibliotecaService.buscarLibros(query));
        model.addAttribute("query", query);
        if (!model.containsAttribute("nuevoLibro")) {
            model.addAttribute("nuevoLibro", new Libro());
        }
        return "libros";
    }

    @PostMapping("/registrar")
    public String registrarLibro(@ModelAttribute("nuevoLibro") Libro libro, RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.registrarLibro(libro);
            redirectAttributes.addFlashAttribute("successMessage", "Libro registrado exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("nuevoLibro", libro);
        }
        return "redirect:/libros";
    }

    @PostMapping("/eliminar/{codigo}")
    public String eliminarLibro(@PathVariable("codigo") String codigo, RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.eliminarLibro(codigo);
            redirectAttributes.addFlashAttribute("successMessage", "Libro eliminado exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/libros";
    }
}
