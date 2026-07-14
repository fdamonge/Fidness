package controlador;

import excepciones.DatosInvalidosException;
import excepciones.LoginException;
import modelo.Administrador;
import modelo.Persona;
import modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que gestiona los usuarios del sistema.
 * Demuestra: Colecciones (ArrayList), Serialización, Excepciones, Polimorfismo.
 */
public class GestorUsuarios {
    
    private List<Persona> usuarios;
    private static final String ARCHIVO_DATOS = "datos/usuarios.dat";

    public GestorUsuarios() {
        usuarios = new ArrayList<>();
        cargarDatos();
        
        // Si no hay usuarios, crear admin por defecto
        if (usuarios.isEmpty()) {
            usuarios.add(new Administrador("admin", "admin123"));
            usuarios.add(new Usuario("cliente", "cliente123"));
            guardarDatos();
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Demuestra: Excepciones personalizadas, validaciones.
     */
    public void registrar(String username, String password) throws DatosInvalidosException {
        if (username == null || username.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre de usuario no puede estar vacío.");
        }
        if (password == null || password.length() < 4) {
            throw new DatosInvalidosException("La contraseña debe tener al menos 4 caracteres.");
        }
        
        // Verificar si el usuario ya existe
        for (Persona p : usuarios) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                throw new DatosInvalidosException("El usuario '" + username + "' ya existe.");
            }
        }
        
        usuarios.add(new Usuario(username, password));
        guardarDatos();
    }

    /**
     * Autentica un usuario por sus credenciales.
     * Demuestra: Polimorfismo (retorna Persona, puede ser Usuario o Administrador).
     */
    public Persona autenticar(String username, String password) throws LoginException {
        for (Persona p : usuarios) {
            if (p.getUsername().equalsIgnoreCase(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        throw new LoginException("Usuario o contraseña incorrectos.");
    }

    /**
     * Serializa la lista de usuarios a archivo.
     * Demuestra: Serialización con ObjectOutputStream.
     */
    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                usuarios = (List<Persona>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar usuarios: " + e.getMessage());
                usuarios = new ArrayList<>();
            }
        }
    }

    public void guardarDatos() {
        try {
            File directorio = new File("datos");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
                oos.writeObject(usuarios);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}
