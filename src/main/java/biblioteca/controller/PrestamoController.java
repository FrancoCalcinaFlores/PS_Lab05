package biblioteca.controller;

import biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public PrestamoController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public String verPrestamos(Model model) {
        model.addAttribute("prestamosActivos", bibliotecaService.listarPrestamosActivos());
        model.addAttribute("libros", bibliotecaService.listarTodosLosLibros());
        model.addAttribute("usuarios", bibliotecaService.listarTodosLosUsuarios());
        return "prestamos";
    }

    @PostMapping("/registrar")
    public String registrarPrestamo(@RequestParam("libroCodigo") String libroCodigo,
                                    @RequestParam("usuarioCodigo") String usuarioCodigo,
                                    RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.registrarPrestamo(libroCodigo, usuarioCodigo);
            redirectAttributes.addFlashAttribute("successMessage", "Préstamo registrado exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("prevLibro", libroCodigo);
            redirectAttributes.addFlashAttribute("prevUsuario", usuarioCodigo);
        }
        return "redirect:/prestamos";
    }

    @PostMapping("/devolver/{id}")
    public String registrarDevolucion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.registrarDevolucion(id);
            redirectAttributes.addFlashAttribute("successMessage", "Devolución registrada exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/devolver-desde-prestamos/{id}")
    public String registrarDevolucionDesdePrestamos(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.registrarDevolucion(id);
            redirectAttributes.addFlashAttribute("successMessage", "Devolución registrada exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/prestamos";
    }
}
