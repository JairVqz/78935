package mx.uv.t4is.Saludos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.saludos.BuscarSaludosResponse;
import https.t4is_uv_mx.saludos.EliminarSaludoRequest;
import https.t4is_uv_mx.saludos.EliminarSaludoResponse;
import https.t4is_uv_mx.saludos.ModificarSaludoRequest;
import https.t4is_uv_mx.saludos.ModificarSaludoResponse;
import https.t4is_uv_mx.saludos.SaludarRequest;
import https.t4is_uv_mx.saludos.SaludarResponse;

@Endpoint
public class SaludosEndPoint {
    int contador = 1;
    List<Saludo> listaSaludos = new ArrayList<>();


    @PayloadRoot(localPart = "SaludarRequest" ,namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public SaludarResponse Saludar(@RequestPayload SaludarRequest peticion) {
        SaludarResponse respuesta = new SaludarResponse();
        respuesta.setRespuesta("Hola "+peticion.getNombre());
        Saludo saludo = new Saludo();
        saludo.setNombre(peticion.getNombre());
        saludo.setId(contador);
        listaSaludos.add(saludo);
        contador++;
        return respuesta;  
    }

    @PayloadRoot(localPart = "BuscarSaludosRequest" ,namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public BuscarSaludosResponse buscarSaludos(){
        BuscarSaludosResponse respuesta = new BuscarSaludosResponse();
        for (Saludo saludo : listaSaludos) {
            BuscarSaludosResponse.Saludos saludosBuscar = new BuscarSaludosResponse.Saludos();
            saludosBuscar.setId(saludo.getId());
            saludosBuscar.setNombre(saludo.getNombre());
            respuesta.getSaludos().add(saludosBuscar);
        }
        return respuesta;
    }

    @PayloadRoot(localPart = "ModificarSaludoRequest" ,namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public ModificarSaludoResponse modificarSaludo(@RequestPayload ModificarSaludoRequest peticion){       
        ModificarSaludoResponse respuesta = new ModificarSaludoResponse(); 
        Saludo saludo = new Saludo();
        saludo.setId(peticion.getId());
        saludo.setNombre(peticion.getNombre());
        listaSaludos.set(peticion.getId()-1,saludo);            
        respuesta.setRespuesta("Saludo modificado");        
        return respuesta;
    }

    @PayloadRoot(localPart = "EliminarSaludoRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public EliminarSaludoResponse eliminarSaludo(@RequestPayload EliminarSaludoRequest peticion){
        EliminarSaludoResponse respuesta = new EliminarSaludoResponse();
        BuscarSaludosResponse.Saludos saludo = new BuscarSaludosResponse.Saludos();
        contador=1;
        saludo.setId(peticion.getId());
        listaSaludos.remove(saludo.getId()-1);
        respuesta.setRespuesta("Saludo eliminado");
        //reconstrucción de la lista de los saludos, para que los Id's queden en orden secuencial
        for(int j = 0; j<listaSaludos.size();j++){
            listaSaludos.get(j).setId(j+1);
            contador++;
        }
        return respuesta;
    }

    /*EJEMPLO ELIMINAR CLASE
    @PayloadRoot(localPart = "EliminarSaludoRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public EliminarSaludoResponse eliminarSaludo(@RequestPayload EliminarSaludoRequest peticion){
        EliminarSaludoResponse respuesta = new EliminarSaludoResponse();
        
        for (Saludo saludo : listaSaludos) {
            if(saludo.getId()==peticion.getId()){
                listaSaludos.remove(saludo);
                break;
            }
        }
        respuesta.setRespuesta("Saludo eliminado");
        return respuesta;
    }*/

}
