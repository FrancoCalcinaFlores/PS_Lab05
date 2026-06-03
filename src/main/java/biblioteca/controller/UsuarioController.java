package biblioteca.controller;

import biblioteca.model.Usuario;
import biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final BibliotecaService bibliotecaService;

    @Autowired
    public UsuarioController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public String listarUsuarios(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("usuarios", bibliotecaService.buscarUsuarios(query));
        model.addAttribute("query", query);
        if (!model.containsAttribute("nuevoUsuario")) {
            model.addAttribute("nuevoUsuario", new Usuario());
        }
        return "usuarios";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("nuevoUsuario") Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            bibliotecaService.registrarUsuario(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario registrado exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("nuevoUsuario", usuario);
        }
        return "redirect:/usuarios";
    }
}
