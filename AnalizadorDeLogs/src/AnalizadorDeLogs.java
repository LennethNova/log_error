import java.io.*;
import java.nio.file.*;

public class AnalizadorDeLogs {
    public static void main(String[]args) {
        Path rutaLog = Paths.get("src/errores.log");
        int totalLineas = 0;
        int totalErrores = 0;
        int totalWarnings = 0;

        // try reading file
        try (BufferedReader lector = Files.newBufferedReader(rutaLog)){
            String linea;
            while ((linea = lector.readLine()) != null) {
                totalLineas++;
                if (linea.contains("ERROR")) totalErrores++;
                if (linea.contains("WARNING")) totalWarnings++;
            }

            // SUmmary
            System.out.println("\nSummary of log analysis: ");
            System.out.println("Lines read: " + totalLineas);
            System.out.println("Errors Found: " + totalErrores);
            System.out.println("Warnings Found: " + totalWarnings);

            double porcentaje = ((double) (totalErrores + totalWarnings) / totalLineas) * 100;
            System.out.printf("Relevant percentage of lines: %.2f%%\n", porcentaje);

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            registrarError(e.getMessage());
        }
    }

    // Save errors in external file
    private static void registrarError(String mensaje) {
        Path rutaError = Paths.get("src/Failure_registry.txt");
        try {
            Files.writeString(rutaError, "Reading error: " + mensaje + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("Couldn't register the failure: " + ex.getMessage());
        }
    }
}