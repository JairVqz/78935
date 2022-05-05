package mx.uv.SaludarRest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludarControlador {
  
    List<Saludo> lista = new ArrayList<>();
    private int contador = 0;   

    @RequestMapping("/")
    public String home(){
        return "Bienvenido";
    }

    //Saludar
    @GetMapping("/saludar/{nombre}")
    public String agregar(@PathVariable(("nombre") )String nombre){

        Saludo saludo = new Saludo();
        saludo.setNombre(nombre);
        saludo.setId(contador);
        lista.add(saludo);
        contador++;
        return "Hola " + nombre;
    }

    //Listar Saludos
    @GetMapping("/listaSaludos")
    public List<Saludo> lista(){
        return lista;
    } 

}
