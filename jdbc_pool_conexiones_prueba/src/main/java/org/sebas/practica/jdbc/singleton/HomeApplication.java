package org.sebas.practica.jdbc.singleton;

import org.sebas.practica.jdbc.singleton.model.User;
import org.sebas.practica.jdbc.singleton.repository.ModelRepository;
import org.sebas.practica.jdbc.singleton.repository.UserRepositoryImpl;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author jhonc
 * @version 1.0
 * @since 26/02/2022
 */
public class HomeApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int opcionIndice = 0;
        ModelRepository<User> repo = new UserRepositoryImpl();
        String message;

        do {
            message = "ID | USERNAME | PASSWORD | EMAIL\n";
            Map<String, Integer> operaciones = new HashMap<>();
            operaciones.put("Agregar", 1);
            operaciones.put("Eliminar", 2);
            operaciones.put("Listar", 3);
            operaciones.put("Actualizar", 4);
            operaciones.put("Salir", 5);

            Object[] opArreglo = operaciones.keySet().toArray();

            Object opcion = JOptionPane.showInputDialog(null,
                    "Seleccione un Operación",
                    "Mantenedor de Usuarios",
                    JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
            } else {
                opcionIndice = operaciones.get(opcion.toString());

                switch (opcionIndice) {
                    case 1: {
                        String username = "", password = "", email = "";
                        try {
                            username = JOptionPane.showInputDialog("Ingrese el usuario: ");
                            if (!(username.trim().isBlank())) {
                                password = JOptionPane.showInputDialog("Ingrese la contraseña: ");
                            } else {
                                messageMisPlacedFields();
                                break;
                            }
                            if (!(password.trim().isBlank())) {
                                email = JOptionPane.showInputDialog("Ingrese el email: ");
                            } else {
                                messageMisPlacedFields();
                                break;
                            }
                            if (!(email.trim().isBlank())) {
                                repo.save(new User(username, password, email));
                                JOptionPane.showMessageDialog(null, "¡Usuario creado con éxito!", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                messageMisPlacedFields();
                                break;
                            }

                        } catch (NumberFormatException e) {
                            messageError();
                        }
                        break;
                    }
                    case 2: {
                        long indiceEliminar = 0L;
                        try {
                            indiceEliminar = Long.parseLong(JOptionPane.showInputDialog("Ingrese el id del usuario: "));
                            if (indiceEliminar > 0) {
                                if (repo.findById(indiceEliminar) != null) {
                                    repo.delete(indiceEliminar);
                                    JOptionPane.showMessageDialog(null, "¡Usuario eliminado con éxito!", "Felicidades"
                                            , JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "¡No existe el usuario!", "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                                }

                            }
                        } catch (NumberFormatException e) {
                            messageError();
                        }

                        break;
                    }
                    case 3: {
                        for (User user : repo.getAll()) {
                            message += user;
                        }
                        JOptionPane.showMessageDialog(null, message, "Listado de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    case 4: {
                        User user = new User();
                        long indexToSearch = 0L;
                        String newUsername = "", newPassword = "", newEmail = "";

                        try {
                            indexToSearch = Long.parseLong(JOptionPane.showInputDialog(null, "Ingrese el id del usuario a actualizar: "));

                            user = repo.findById(indexToSearch);

                            if (user == null) {
                                JOptionPane.showMessageDialog(null, "¡No existe el usuario!", "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            newUsername = JOptionPane.showInputDialog("Ingrese el nuevo usuario: ");
                            if (!(newUsername.trim().isBlank())) {
                                newPassword = JOptionPane.showInputDialog("Ingrese la nueva contraseña: ");
                            } else {
                                messageMisPlacedFields();
                                break;
                            }
                            if (!(newPassword.trim().isBlank())) {
                                newEmail = JOptionPane.showInputDialog("Ingrese el nuevo email: ");
                            } else {
                                messageMisPlacedFields();
                                break;
                            }
                            if (!(newEmail.trim().isBlank())) {
                                user.setUsername(newUsername);
                                user.setPassword(newPassword);
                                user.setEmail(newEmail);
                                repo.save(user);
                                JOptionPane.showMessageDialog(null, "¡Usuario actualizado con éxito!", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                messageMisPlacedFields();
                                break;
                            }

                        } catch (NumberFormatException e) {
                            messageError();
                        }
                        break;
                    }
                    case 5: {
                        JOptionPane.showMessageDialog(null, "Sesión Finalizada", "Salir", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }

            }

        } while (opcionIndice != 5);


    }

    private static void messageMisPlacedFields() {
        JOptionPane.showMessageDialog(null, "¡Los campos no puede estar vacios!", "Mensaje de Error"
                , JOptionPane.ERROR_MESSAGE);
    }

    private static void messageError() {
        JOptionPane.showMessageDialog(null, "¡Valor invalido!", "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
    }

}


